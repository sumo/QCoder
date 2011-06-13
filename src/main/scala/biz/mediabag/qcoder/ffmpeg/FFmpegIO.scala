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
import grizzled.slf4j.Logger
import FFmpegUtils._
import AvformatLibrary._
import grizzled.slf4j.Logging
import java.math.BigInteger

class FFmpegContainerFactory extends ContainerFactory with Logging {

  val AV_NOPTS_VALUE = new BigInteger("8000000000000000", 16)
  val log = Logger(classOf[FFmpegContainerFactory])

  def loadFrom(is: ReadableByteChannel): FFmpegContainer = {
    val buffer = ByteBuffer.allocateDirect(2 * 2048)
    val size = is.read(buffer)
    info("Read " + size + " bytes")
    buffer.rewind

    val memory = new Memory(size)
    val bytes: Array[Byte] = new Array(size)
    buffer.get(bytes)
    memory.write(0, bytes, 0, size)
    buffer.rewind

    val probeData = new AVProbeData(null, memory, size)
    probeData.filename = "__url_prot"

    val inputFmt = FormatLibrary.av_probe_input_format(probeData, 1)
    if (inputFmt == null) {
      error("Probe failed");
      null
    } else {
      info("Probe successful: %s, %s".format(inputFmt.name.getString(0),
        inputFmt.long_name.getString(0)));
      inputFmt.flags |= AVFMT_NOFILE
      //      val bioContext = new ByteIOContext
      info("FormatLibrary.url_open_buf")
      //      FFmpegCall {
      //        //    	  FormatLibrary.url_open_buf(Array(bioContext), buffer, size, URL_RDONLY)
      //        FormatLibrary.init_put_byte(bioContext, buffer, size, AvformatLibrary.URL_RDONLY, null,
      //          new ReadFunc(is), null, null)
      //        //          null, null, null)
      //      }
      val bioContext = FFmpegCallObject { FormatLibrary.av_alloc_put_byte(buffer, size, AvformatLibrary.URL_RDONLY, null, new ReadFunc(is), null, null) };
      info("/FormatLibrary.url_open_buf")
      bioContext.is_streamed = 1
      //      info(inputFmt)
      //      info(bioContext)
      //      info(probeData)

      val formatCtxArray: Array[AVFormatContext.ByReference] = new Array(1)
      val formatParams = new AVFormatParameters
      formatParams.prealloced_context = 0

      info("FormatLibrary.av_open_input_stream")
      FFmpegCall {
        FormatLibrary.av_open_input_stream(formatCtxArray, bioContext, probeData.filename, inputFmt, null)
      }
      info("/FormatLibrary.av_open_input_stream")

      info("FormatLibrary.av_open_find_stream_info")
      info(formatCtxArray(0))
      val formatCtx = formatCtxArray(0)
      FFmpegCall { FormatLibrary.av_find_stream_info(formatCtx) }
      info("FormatLibrary.av_open_find_stream_info")
      info("FormatLibrary.dump_format")
      FormatLibrary.dump_format(formatCtx, 0, "stream", 0)
      info("/FormatLibrary.dump_format")
      new FFmpegContainer(formatCtx, false)
    }
  }

  class ReadFunc(is: ReadableByteChannel) extends av_alloc_put_byte_arg1_read_packet_callback with Logging {
    var firstWrite = true;
    override def apply(opaque: Pointer, buf: Pointer, bufSize: Int): Int = {
      if (!firstWrite) {
        val buffer = buf.getByteBuffer(0, bufSize)
        try {
          val size = is.read(buffer)
          info("Read " + size + "/" + bufSize + " bytes")
          size
        } catch {
          case e: Exception => {
            error("Failed to read", e)
            -1
          }
        }
      } else {
        firstWrite = false
        bufSize
      }
    }
  }

  class FFmpegContainer(formatCtx: AVFormatContext, output: Boolean) extends Container[FFmpegStream] {
    override def formatName: String = if (output) { formatCtx.oformat.name } else { formatCtx.iformat.name }
    override def longFormatName: String = if (output) { formatCtx.oformat.long_name } else { formatCtx.iformat.long_name }
    override def startTime: Option[Long] = if (AV_NOPTS_VALUE != formatCtx.start_time) { Some(formatCtx.start_time) } else { None }
    override def duration: Option[Long] = if (formatCtx.duration != 0) { Some(formatCtx.duration) } else { None }
    override def bitRate: Option[Long] = if (formatCtx.bit_rate != 0) { Some(formatCtx.bit_rate) } else { None }
    override def streams: List[FFmpegStream] = streamsList

    private var streamsList: List[FFmpegStream] = Nil
    extractStreams

    private def extractStreams: Unit = {
      info(formatCtx)
      val streamPtr = formatCtx.streams.getValue
      val streamPtrs = streamPtr.getPointerArray(0, formatCtx.nb_streams - 1)
      for (streamIdx <- 0 until formatCtx.nb_streams) {
        val avStream = new AVStream()
        avStream.use(streamPtrs(streamIdx))
        streamsList = FFmpegStream(avStream) :: streamsList
      }
    }
  }
}


