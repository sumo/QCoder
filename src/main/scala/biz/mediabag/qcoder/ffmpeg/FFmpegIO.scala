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

class FFmpegContainerFactory extends ContainerFactory {
  
  val log = Logger(classOf[FFmpegContainerFactory])

  def loadFrom(is: ReadableByteChannel): FFmpegContainer = {
    val size = 2 * 1024
    val buffer = ByteBuffer.allocate(size)
    is.read(buffer)
    buffer.flip
    val memory = new Memory(size)
    memory.write(0, buffer.array, 0, size)
    val probeData = new AVProbeData(null, memory, size)
    val inputFmt = FormatLibrary.av_probe_input_format(probeData, 1)
    if (inputFmt == null) {
      log.error("Probe failed");
      null
    } else {
      log.info("Probe successful: %s, %s".format(inputFmt.name.getString(0),
        inputFmt.long_name.getString(0)));
      
      val formatCtx = FormatLibrary.avformat_alloc_context
      val bioContext = new ByteIOContext
      log.info("FormatLibrary.url_open_buf")
      FFmpegCall {
         FormatLibrary.init_put_byte(bioContext, buffer, size, AvformatLibrary.URL_RDONLY, null,
        //new ReadFunc(is), null, new SeekFunc(is))
        null, null, null)
      }
      log.info("/FormatLibrary.url_open_buf")
      bioContext.is_streamed = 1
      log.info("FormatLibrary.av_open_input_stream")
      FFmpegCall { FormatLibrary.av_open_input_stream(formatCtx.castToReferenceArray, bioContext, "", inputFmt, null) }
      log.info("/FormatLibrary.av_open_input_stream")
      log.info("FormatLibrary.av_open_find_stream_info")
      FFmpegCall { FormatLibrary.av_find_stream_info(formatCtx) }
      log.info("FormatLibrary.av_open_find_stream_info")
      log.info("FormatLibrary.dump_format")
      FormatLibrary.dump_format(formatCtx, 0, "stream", 0)
      log.info("/FormatLibrary.dump_format")
      new FFmpegContainer(formatCtx, false)
    }
  }

}

class FFmpegContainer(formatCtx: AVFormatContext, output: Boolean) extends Container[FFmpegStream] {
  import java.math.BigInteger

  val AV_NOPTS_VALUE = new BigInteger("8000000000000000", 16)
  override def formatName: String = if (output) { formatCtx.oformat.name } else { formatCtx.iformat.name }
  override def longFormatName: String = if (output) { formatCtx.oformat.long_name } else { formatCtx.iformat.long_name }
  override def startTime: Option[Long] = if (AV_NOPTS_VALUE != formatCtx.start_time) { Some(formatCtx.start_time) } else { None }
  override def duration: Option[Long] = if (formatCtx.duration != 0) { Some(formatCtx.duration) } else { None }
  override def bitRate: Option[Long] = if (formatCtx.bit_rate != 0) { Some(formatCtx.bit_rate) } else { None }
  override def streams: List[FFmpegStream] = streamsList

  private var streamsList: List[FFmpegStream] = Nil
  extractStreams

  private def extractStreams: Unit = {
    val streamPtr = formatCtx.streams.getValue
    val streamPtrs = streamPtr.getPointerArray(0, formatCtx.nb_streams - 1)
    for (streamIdx <- 0 until formatCtx.nb_streams) {
      val avStream = new AVStream()
      avStream.use(streamPtrs(streamIdx))
      streamsList = FFmpegStream(avStream) :: streamsList
    }
  }
}


