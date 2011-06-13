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
import grizzled.slf4j.Logging

object FFmpegUtils extends Logging {
  val log = Logger("FFmpegUtils")
  val FormatLibrary = AvformatLibrary.INSTANCE
  FormatLibrary.av_register_all
  log.info("AvformatLibrary " + AvformatLibrary.JNA_LIBRARY_NAME + ": " + FormatLibrary.avformat_configuration)
  val CodecLibrary = AvcodecLibrary.INSTANCE
  CodecLibrary.avcodec_init
  CodecLibrary.avcodec_register_all
  log.info("AvcodecLibrary: " + CodecLibrary.avcodec_configuration)
  val UtilLibrary = AvutilLibrary.INSTANCE

  implicit def convertToMemory(str: String): Memory = {
    assert(str.length > 0, "String length needs to be greater than 0")
    val mem = new Memory(str.length)
    mem.write(0, str.getBytes(), 0, str.length());
    mem
  }

  implicit def convertToString(pointer: Pointer): String = {
    pointer.getString(0)
  }

  object FFmpegCall {
    def apply(call: => Int): Int = {
      val code = call
      if (code < 0) {
        val errBuf = ByteBuffer.allocate(1024)
        val errBufSize = new NativeSize(1024)
        val success = UtilLibrary.av_strerror(code, errBuf, errBufSize);
        throw new QCoderException(code, new String(errBuf.array))
      } else {
        info("Call result= " + code)
        code
      }
    }
  }

  object FFmpegCallObject {
    def apply[T](call: => T): T = {
      val code = call
      if (code == null) {
        throw new QCoderException("Call failed")
      } else {
        code
      }
    }
  }
}


