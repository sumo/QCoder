package biz.mediabag.qcoder.ffmpeg

import biz.mediabag.qcoder.domain._
import biz.mediabag.qcoder._
import java.nio._
import java.nio.channels._
import avformat._
import avcodec._
import avutil._
import com.sun.jna._
import com.ochafik.lang.jnaerator.runtime._
import FFmpegUtils._
import AvformatLibrary._
import grizzled.slf4j.Logging
import java.math.BigInteger
import AvcodecLibrary.AVMediaType._
import com.sun.jna.ptr.IntByReference
import java.io.IOException
import com.sun.jna.ptr.ShortByReference

class FFmpegInputContext(private val icr: InputChannelRewinder, val inputFmt: AVInputFormat) extends Logging {
  val SIZE = 4096
  private val readFunc = new ReadFunc(icr)
  private val seekFunc = new SeekFunc(icr)

  private val bioBuffer = ByteBuffer.allocateDirect(SIZE + AvcodecLibrary.FF_INPUT_BUFFER_PADDING_SIZE)
  trace("FormatLibrary.av_alloc_put_byte")
  icr.rewind
  val bioContext = FFmpegCallObject { FormatLibrary.av_alloc_put_byte(bioBuffer, SIZE, AvformatLibrary.URL_RDONLY, null, readFunc, null, seekFunc) }
  bioContext.is_streamed = 1
  trace("/FormatLibrary.av_alloc_put_byte")

  private val formatCtxArray: Array[AVFormatContext.ByReference] = new Array(1)
  trace("FormatLibrary.av_open_input_stream")
  FFmpegCall {
    FormatLibrary.av_open_input_stream(formatCtxArray, bioContext, "", inputFmt, null)
  }
  trace("/FormatLibrary.av_open_input_stream")

  val formatCtx = formatCtxArray(0)
  
  trace("FormatLibrary.av_find_stream_info")
  FFmpegCall { FormatLibrary.av_find_stream_info(formatCtx) }
  trace("/FormatLibrary.av_find_stream_info")

  trace("FormatLibrary.dump_format")
  FormatLibrary.dump_format(formatCtx, 0, "stream", 0)
  trace("/FormatLibrary.dump_format")

  def close = icr.close
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
      whence match {
        case AvformatLibrary.AVSEEK_SIZE => return icr.size
        case SEEK_SET => {
          trace("Seek offset=" + offset + " SEEK_SET")
          if (offset < 0) -1 else icr.seek(offset.intValue)
        }
        case SEEK_CUR => try {
          trace("Seek pos=" + icr.position + ", offset=" + offset + " SEEK_CUR")
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
}

class FFmpegDecodingContainerFactory extends DecodingContainerFactory[FFmpegDecodingStream[_]] with Logging {
  private val PROBE_BUF_MIN = 2048
  private val PROBE_BUF_MAX = (1 << 20)

  private def probeFormat(input: InputChannelRewinder): AVInputFormat = {

    val buffer = ByteBuffer.allocateDirect(PROBE_BUF_MIN)

    def probeFormatWithLimit(memory: Memory, input: InputChannelRewinder, limit: Int): AVInputFormat = {
      buffer.rewind
      val size = input.read(buffer)
      buffer.rewind
      debug("Read " + size + " where limit=" + limit)

      val bytes: Array[Byte] = new Array(size)
      buffer.get(bytes, 0, size)
      memory.write(limit, bytes, 0, size)
      memory.zero(limit + size, AvformatLibrary.AVPROBE_PADDING_SIZE)

      val probeData = new AVProbeData("", memory, limit + size)
      // use av_probe_input_format2 and check for low scores (utils.c:540)
      val ifmt = FormatLibrary.av_probe_input_format(probeData, 1)
      if (ifmt != null) {
        ifmt
      } else if (limit >= PROBE_BUF_MAX) {
        null
      } else {
        val newLimit = limit + size
        probeFormatWithLimit(memory, input, newLimit)
      }
    }

    val memory = new Memory(PROBE_BUF_MAX + AvformatLibrary.AVPROBE_PADDING_SIZE)
    probeFormatWithLimit(memory, input, 0)

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

      val ffmpegContext = new FFmpegInputContext(input, inputFmt)
      new FFmpegInputContainer(ffmpegContext)
    }
  }

}

class FFmpegInputContainer(ffmpegContext: FFmpegInputContext) extends {
  override val formatCtx = ffmpegContext.formatCtx
  override val output = false
} with FFmpegContainer with DecodingContainer[FFmpegDecodingStream[_]] with Logging {

  private var streamsList: List[FFmpegDecodingStream[_]] = Nil
  override def streams = streamsList

  trace("Found " + ffmpegContext.formatCtx.nb_streams + " streams")
  for (streamIdx <- 0 until ffmpegContext.formatCtx.nb_streams) {
    val streamPointer = ffmpegContext.formatCtx.streams(streamIdx)
    if (streamPointer == null) {
      throw new QCoderException("Stream " + streamIdx + " was not valid")
    }
    val avStream = new AVStream
    avStream.use(streamPointer)
    val codecCtx: AVCodecContext = avStream.codec
    val codec: AVCodec = codecCtx.codec
    val stream = codecCtx.codec_type match {
      case AVMEDIA_TYPE_VIDEO => new FFmpegDecodingVideoStream(avStream, streamIdx, { fr => println("Received video frame with pts=" + fr.pts + " and dts=" + fr.displayPictureNumber) })
      case AVMEDIA_TYPE_AUDIO => new FFmpegDecodingAudioStream(avStream, streamIdx, { fr => println("Received audio frame with pts=" + fr.pts) })
      case _ => null
    }
    streamsList = stream :: streamsList
  }

  trace("Created " + streamsList.size + " streams")

  override def runDecodeLoop = {
    var openStreams = streamsList
    val frameFinished = ByteBuffer.allocateDirect(4)
    val pFrame = CodecLibrary.avcodec_alloc_frame
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
              ib.rewind
              CodecLibrary.avcodec_get_frame_defaults(pFrame)
              str.avStream.codec.reordered_opaque = pkt.pts;
              trace("CodecLibrary.avcodec_decode_video2")
              FFmpegCall {
                CodecLibrary.avcodec_decode_video2(str.avStream.codec, pFrame, ib, pkt)
              }
              trace("/CodecLibrary.avcodec_decode_video2")
              ib.rewind
              if (ib.get > 0) {
                str << new FFmpegVideoFrame(pFrame)
              }
            }
            case str: FFmpegDecodingAudioStream => {
              val samplesSize = if (pkt.size * 16 > AvcodecLibrary.AVCODEC_MAX_AUDIO_FRAME_SIZE) {
                pkt.size * 16
              } else {
                AvcodecLibrary.AVCODEC_MAX_AUDIO_FRAME_SIZE
              }
              val samples = new Memory(samplesSize)
              samples.align(16)
              val samplesBuffer = new ShortByReference
              samplesBuffer.setPointer(samples)

              val decodedDataSize = new IntByReference
              decodedDataSize.setValue(samplesSize)
              trace("CodecLibrary.avcodec_decode_audio3")
              val ret = FFmpegCall {
                CodecLibrary.avcodec_decode_audio3(str.avStream.codec, samplesBuffer, decodedDataSize, pkt)
              }
              trace("CodecLibrary.avcodec_decode_audio3")

              Pointer.nativeValue(pkt.data, Pointer.nativeValue(pkt.data) + ret)
              pkt.size -= ret;

              /* Some bug in mpeg audio decoder gives */
              /* decoded_data_size < 0, it seems they are overflows */
              if (decodedDataSize.getValue > 0) {
                str << new FFmpegAudioFrame(samples, samplesSize, pkt.pts)
              }

            }
            case null =>
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
      }
    }
    UtilLibrary.av_free(pFrame.getPointer)
    ffmpegContext.close
  }
}




