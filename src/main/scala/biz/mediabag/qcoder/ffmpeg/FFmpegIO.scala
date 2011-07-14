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

class FFmpegEncodingContainerFactory extends EncodingContainerFactory[FFmpegEncodingStream[_]] with Logging {

  def writeTo(format: String, videoCodec: String, audioCodec: String): FFmpegOutputContainer = {
    new FFmpegOutputContainer(format, videoCodec, audioCodec)
  }
}

class FFmpegDecodingContainerFactory extends DecodingContainerFactory[FFmpegDecodingStream[_]] with Logging {

  private def probeFormat(input: InputChannelRewinder) = {
    val buffer = ByteBuffer.allocateDirect(4096)
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
      debug("Probe successful: %s, %s".format(inputFmt.name,
        inputFmt.long_name));
      inputFmt.flags |= AVFMT_NOFILE

      input.rewind
      val bioBuffer = ByteBuffer.allocateDirect(4096 + AvcodecLibrary.FF_INPUT_BUFFER_PADDING_SIZE)
      trace("FormatLibrary.av_alloc_put_byte")
      val bioContext = FFmpegCallObject { FormatLibrary.av_alloc_put_byte(bioBuffer, 4096, AvformatLibrary.URL_RDONLY, null, new ReadFunc(input), null, new SeekFunc(input)) };
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
      new FFmpegInputContainer(formatCtx)
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
    override def apply(opaque: Pointer, offset:Long, whence:Int): Long = {
      trace("Seek offset=" + offset + " whence="+whence)
      return -1
    }
  }
}

abstract class FFmpegContainer(val formatCtx: AVFormatContext, output: Boolean) {
  val AV_NOPTS_VALUE = new BigInteger("8000000000000000", 16)
  def formatName: String = if (output) { formatCtx.oformat.name } else { formatCtx.iformat.name }
  def longFormatName: String = if (output) { formatCtx.oformat.long_name } else { formatCtx.iformat.long_name }
  def startTime: Option[Long] = if (AV_NOPTS_VALUE != formatCtx.start_time) { Some(formatCtx.start_time) } else { None }
  def duration: Option[Long] = if (formatCtx.duration != 0) { Some(formatCtx.duration) } else { None }
  def bitRate: Option[Long] = if (formatCtx.bit_rate != 0) { Some(formatCtx.bit_rate) } else { None }
}

class FFmpegInputContainer(formatCtx: AVFormatContext) extends FFmpegContainer(formatCtx, false) with DecodingContainer[FFmpegDecodingStream[_]] with Logging {
  private var streamsList: List[FFmpegDecodingStream[_]] = Nil
  override def streams = streamsList

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
      case AVMEDIA_TYPE_VIDEO => new FFmpegDecodingVideoStream(avStream, streamIdx, { fr => println("Recieved video frame") })
      case AVMEDIA_TYPE_AUDIO => new FFmpegDecodingAudioStream(avStream, streamIdx, { fr => println("Recieved audio frame") })
    }
    streamsList = stream :: streamsList
  }
  
  trace("Created " + streamsList.size + " streams")

  override def runDecodeLoop = {
    var openStreams = streamsList
    val frameFinished = ByteBuffer.allocateDirect(4)
    while (openStreams.size > 0) {
      val pkt = new AVPacket
      val pFrame = CodecLibrary.avcodec_alloc_frame
      val ret = FormatLibrary.av_read_frame(formatCtx, pkt)
      val stream = streams(pkt.stream_index)
      if (ret < 0) {
        openStreams = openStreams.filterNot(_ eq stream)
        stream.EOFReached
      }
      val bytesRead = stream match {
        case str: FFmpegDecodingVideoStream => {
          val ib = frameFinished.asIntBuffer
          CodecLibrary.avcodec_get_frame_defaults(pFrame)
          CodecLibrary.avcodec_decode_video2(stream.avStream.codec, pFrame, ib, pkt)
          ib.flip
          if (ib.limit > 0 && ib.get > 0) {
            str << new FFmpegVideoFrame(pFrame)
          }
        }
        case str: FFmpegDecodingAudioStream =>
      }
      CodecLibrary.av_free_packet(pkt)
      frameFinished.flip
    }
  }

}

class FFmpegOutputContainer(format: String, videoCodecStr: String, audioCodecStr: String)
  extends FFmpegContainer(FormatLibrary.avformat_alloc_context(), true) with Container[FFmpegEncodingStream[_]] with Logging {
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



