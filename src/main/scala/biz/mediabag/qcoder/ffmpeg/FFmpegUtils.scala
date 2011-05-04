package biz.mediabag.qcoder.ffmpeg
import avformat._
import avcodec._
import avutil._
import com.sun.jna._
import com.ochafik.lang.jnaerator.runtime._
import com.ochafik.lang.jnaerator.runtime.Structure
import grizzled.slf4j.Logger
import java.nio._
import biz.mediabag.qcoder._

object FFmpegUtils {
  val log = Logger("FFmpegUtils")
  val FormatLibrary = AvformatLibrary.INSTANCE
  FormatLibrary.av_register_all
  log.info("AvformatLibrary: " + FormatLibrary.avformat_configuration)
  val CodecLibrary = AvcodecLibrary.INSTANCE
  CodecLibrary.avcodec_init
  CodecLibrary.avcodec_register_all
  log.info("AvcodecLibrary: " + CodecLibrary.avcodec_configuration)
  
  implicit def convertToMemory(str: String): Memory = {
    assert(str.length > 0, "String length needs to be greater than 0")
    val mem = new Memory(str.length)
    mem.write(0, str.getBytes(), 0, str.length());
    mem
  }

  implicit def convertToString(pointer: Pointer): String = {
    pointer.getString(0)
  }
}

object FFmpegCall {
  val UtilLibrary = AvutilLibrary.INSTANCE
  def apply(call: => Int) = {
    val code = call
    if (code != 0) {
      val errBuf = ByteBuffer.allocate(1024)
      val errBufSize = new NativeSize(1024)
      val success = UtilLibrary.av_strerror(code, errBuf, errBufSize);
      throw new QCoderException(code, new String(errBuf.array))
    }
  }
}
