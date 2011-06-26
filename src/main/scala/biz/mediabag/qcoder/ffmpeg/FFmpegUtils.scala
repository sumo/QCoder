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
  lazy val codecs = buildCodecList(null)
  lazy val iFormats = buildFormatList(null.asInstanceOf[AVInputFormat])
  lazy val oFormats = buildFormatList(null.asInstanceOf[AVOutputFormat])
  
  log.info("AvcodecLibrary: " + CodecLibrary.avcodec_configuration)
  log.info("Codecs are " + codecs)
  log.info("Input formats are " + iFormats)
  log.info("Output formats are " + oFormats)
  
  val UtilLibrary = AvutilLibrary.INSTANCE

  implicit def convertToMemory(str: String): Memory = {
    assert(str.length > 0, "String length needs to be greater than 0")
    val mem = new Memory(str.length)
    mem.write(0, str.getBytes(), 0, str.length());
    mem
  }
  
  def buildCodecList(codec:AVCodec):List[String] = {
    val ac = CodecLibrary.av_codec_next(codec)
    if (ac == null) {
      Nil
    } else {
      ac.name :: buildCodecList(ac)
    }
  }
  
  def buildFormatList(format:AVInputFormat):List[String] = {
    val ac = FormatLibrary.av_iformat_next(format)
    if (ac == null) {
      Nil
    } else {
      ac.name :: buildFormatList(ac)
    }
  }
  
  def buildFormatList(format:AVOutputFormat):List[String] = {
    val ac = FormatLibrary.av_oformat_next(format)
    if (ac == null) {
      Nil
    } else {
      ac.name :: buildFormatList(ac)
    }
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


