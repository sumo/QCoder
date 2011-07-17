package biz.mediabag.qcoder.ffmpeg

import scala.collection.mutable.Queue
import biz.mediabag.qcoder.domain._
import biz.mediabag.qcoder._
import java.nio._
import java.nio.channels._
import avformat._
import avcodec._
import avutil._
import com.sun.jna._
import com.ochafik.lang.jnaerator.runtime._
import grizzled.slf4j.Logger
import FFmpegUtils._
import AvformatLibrary._
import grizzled.slf4j.Logging
import java.math.BigInteger
import AvcodecLibrary.AVMediaType._
import com.sun.jna.ptr.IntByReference
import java.io.IOException

class FFmpegContext(val icr: InputChannelRewinder) {
  val readFunc = new ReadFunc(icr)
  val seekFunc = new SeekFunc(icr)
}

class FFmpegEncodingContainerFactory extends EncodingContainerFactory[FFmpegEncodingStream[_]] with Logging {

  def writeTo(format: String, videoCodec: String, audioCodec: String): FFmpegOutputContainer = {
    new FFmpegOutputContainer(format, videoCodec, audioCodec)
  }
}

class FFmpegDecodingContainerFactory extends DecodingContainerFactory[FFmpegDecodingStream[_]] with Logging {

  private val SIZE = 4096

  private def probeFormat(input: InputChannelRewinder) = {
    val buffer = ByteBuffer.allocateDirect(SIZE)
    val size = input.read(buffer)
    buffer.rewind

    val memory = new Memory(size)
    val bytes: Array[Byte] = new Array(size)
    buffer.get(bytes)
    memory.write(0, bytes, 0, size)

    val probeData = new AVProbeData(null, memory, size)

    FormatLibrary.av_probe_input_format(probeData, 1)
  }

  def loadFrom(chan: ReadableByteChannel): FFmpegInputContainer = {
    val input = new InputChannelRewinder(chan)
    val inputFmt = probeFormat(input)

    if (inputFmt == null) {
      error("Probe failed");
      null
    } else {
      debug("Probe successful: %s, %s".format(inputFmt.name.getString(0),
        inputFmt.long_name.getString(0)));
      inputFmt.flags |= AVFMT_NOFILE

      val ffmpegContext = new FFmpegContext(input)
      input.rewind
      val bioBuffer = ByteBuffer.allocateDirect(SIZE + AvcodecLibrary.FF_INPUT_BUFFER_PADDING_SIZE)
      trace("FormatLibrary.av_alloc_put_byte")

      val bioContext = FFmpegCallObject { FormatLibrary.av_alloc_put_byte(bioBuffer, SIZE, AvformatLibrary.URL_RDONLY, null, ffmpegContext.readFunc, null, ffmpegContext.seekFunc) }
      bioContext.is_streamed = 1
      trace("/FormatLibrary.av_alloc_put_byte")

      val formatCtxArray: Array[AVFormatContext.ByReference] = new Array(1)
      trace("FormatLibrary.av_open_input_stream")
      FFmpegCall {
        FormatLibrary.av_open_input_stream(formatCtxArray, bioContext, "", inputFmt, null)
      }
      trace("/FormatLibrary.av_open_input_stream")

      trace("FormatLibrary.av_find_stream_info")
      val formatCtx = formatCtxArray(0)
      FFmpegCall { FormatLibrary.av_find_stream_info(formatCtx) }
      trace("/FormatLibrary.av_find_stream_info")
      trace("FormatLibrary.dump_format")
      FormatLibrary.dump_format(formatCtx, 0, "stream", 0)
      trace("/FormatLibrary.dump_format")

      new FFmpegInputContainer(formatCtx, ffmpegContext)
    }
  }

}

class ReadFunc(icr: InputChannelRewinder) extends av_alloc_put_byte_arg1_read_packet_callback with Logging {
  override def apply(opaque: Pointer, buf: Pointer, bufSize: Int): Int = {
    val buffer = buf.getByteBuffer(0, bufSize)
    try {
      val size = icr.read(buffer)
      trace("Read " + size + "/" + bufSize + " bytes")
      size
    } catch {
      case e: Exception => {
        error("Failed to read", e)
        -1
      }
    }
  }
}

class SeekFunc(icr: InputChannelRewinder) extends av_alloc_put_byte_arg3_seek_callback with Logging {
  override def apply(opaque: Pointer, offset: Long, whence: Int): Long = {
    trace("Seek offset=" + offset + " whence=" + whence)
    whence match {
      case AvformatLibrary.AVSEEK_SIZE => return icr.size
      case SEEK_SET => if (offset < 0) -1 else icr.seek(offset.intValue)
      case SEEK_CUR => try {
        icr.seek(icr.position + offset.intValue)
      } catch {
        case e: IOException => {
          error("Seek from current to " + offset + " failed", e)
          -1
        }
      }
      case _ => -1
    }
  }
}

abstract class FFmpegContainer(val formatCtx: AVFormatContext, ffmpegContext: FFmpegContext, output: Boolean) {
  val AV_NOPTS_VALUE = new BigInteger("8000000000000000", 16)
  val formatName: String = if (output) { formatCtx.oformat.name } else { formatCtx.iformat.name.getString(0) }
  val longFormatName: String = if (output) { formatCtx.oformat.long_name } else { formatCtx.iformat.long_name.getString(0) }
  val startTime: Option[Long] = if (AV_NOPTS_VALUE != formatCtx.start_time) { Some(formatCtx.start_time) } else { None }
  val duration: Option[Long] = if (formatCtx.duration != 0) { Some(formatCtx.duration) } else { None }
  val bitRate: Option[Long] = if (formatCtx.bit_rate != 0) { Some(formatCtx.bit_rate) } else { None }
}

class FFmpegInputContainer(formatCtx: AVFormatContext, ffmpegContext: FFmpegContext) extends FFmpegContainer(formatCtx, ffmpegContext, false)
  with DecodingContainer[FFmpegDecodingStream[_]] with Logging {
  val icr = ffmpegContext.icr
  private var streamsList: List[FFmpegDecodingStream[_]] = Nil
  override def streams = streamsList

  trace("Found " + formatCtx.nb_streams + " streams")
  for (streamIdx <- 0 until formatCtx.nb_streams) {
    val streamPointer = formatCtx.streams(streamIdx)
    if (streamPointer == null) {
      throw new QCoderException("Stream " + streamIdx + " was not valid")
    }
    val avStream = new AVStream
    avStream.use(streamPointer)
    val codecCtx: AVCodecContext = avStream.codec
    val codec: AVCodec = codecCtx.codec
    val stream = codecCtx.codec_type match {
      case AVMEDIA_TYPE_VIDEO => new FFmpegDecodingVideoStream(avStream, streamIdx, { fr => println("Received video frame with pts=" + fr.pts) })
      case AVMEDIA_TYPE_AUDIO => new FFmpegDecodingAudioStream(avStream, streamIdx, { fr => println("Received audio frame") })
    }
    streamsList = stream :: streamsList
  }

  trace("Created " + streamsList.size + " streams")

  override def runDecodeLoop = {
    var openStreams = streamsList
    val frameFinished = ByteBuffer.allocateDirect(4)
    val pFrame = new AVFrame
    var eof = false

    while (!eof) {
      val pkt = new AVPacket
      try {
        trace("FormatLibrary.av_read_frame")
        val ret = FFmpegCall {
          FormatLibrary.av_read_frame(formatCtx, pkt)
        }
        trace("/FormatLibrary.av_read_frame")
        if (ret == EAGAIN) {
          trace("Stream " + pkt.stream_index + " EAGAIN")
        } else {
          val stream = streams(pkt.stream_index)
          val bytesRead = stream match {
            case str: FFmpegDecodingVideoStream => {
              val ib = frameFinished.asIntBuffer
              CodecLibrary.avcodec_get_frame_defaults(pFrame)
              CodecLibrary.avcodec_decode_video2(stream.avStream.codec, pFrame, ib, pkt)
              frameFinished.rewind
              if (frameFinished.getInt > 0) {
                str << new FFmpegVideoFrame(pFrame)
              }
            }
            case str: FFmpegDecodingAudioStream =>
          }
        }

      } catch {
        case e: QCoderException => {
          trace(e)
          eof = true
          trace("End of streams encountered")
        }
      } finally {
        CodecLibrary.av_free_packet(pkt)
        frameFinished.rewind
      }

    }

    icr.close
  }
}

class FFmpegOutputContainer(format: String, videoCodecStr: String, audioCodecStr: String)
  extends FFmpegContainer(FormatLibrary.avformat_alloc_context(), new FFmpegContext(null), true) with Container[FFmpegEncodingStream[_]] with Logging {
  private var videoCodec: AVCodec = _
  private var audioCodec: AVCodec = _
  val oformat = FormatLibrary.av_guess_format(format, null, null)
  if (oformat == null) {
    throw new QCoderException(format + " is an unknown format")
  }
  formatCtx.oformat = oformat.byReference
  debug("Created output format " + oformat.name + " with default video codec " + oformat.video_codec
    + " and default audio codec " + oformat.audio_codec)
  videoCodec = CodecLibrary.avcodec_find_encoder_by_name(videoCodecStr)
  if (videoCodec == null) {
    throw new QCoderException(videoCodecStr + " is an unknown video codec")
  }
  audioCodec = CodecLibrary.avcodec_find_encoder_by_name(audioCodecStr)
  if (audioCodec == null) {
    throw new QCoderException(audioCodecStr + " is an unknown audio codec")
  }
  oformat.audio_codec = audioCodec.id
  oformat.video_codec = videoCodec.id

  override def streams = Nil
}



