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
  val SEEK_SET = 0
  val SEEK_CUR = 1
  val SEEK_END = 2
  val EAGAIN = 11
  
  val FormatLibrary = AvformatLibrary.INSTANCE
  FormatLibrary.av_register_all
  info("AvformatLibrary " + AvformatLibrary.JNA_LIBRARY_NAME + ": " + FormatLibrary.avformat_configuration)
  val CodecLibrary = AvcodecLibrary.INSTANCE
  CodecLibrary.avcodec_init
  CodecLibrary.avcodec_register_all
  lazy val codecs = buildCodecList(null)
  lazy val iFormats = buildFormatList(null.asInstanceOf[AVInputFormat])
  lazy val oFormats = buildFormatList(null.asInstanceOf[AVOutputFormat])

  info("AvcodecLibrary: " + CodecLibrary.avcodec_configuration)
  info("Codecs are " + codecs)
  info("Input formats are " + iFormats)
  info("Output formats are " + oFormats)

  val UtilLibrary = AvutilLibrary.INSTANCE
  UtilLibrary.av_log_set_level(AvutilLibrary.AV_LOG_DEBUG)
  implicit def convertToMemory(str: String): Memory = {
    val length = if (str.length ==0) 1 else str.length
    val bytes:Array[Byte] = if (str.length ==0) Array(0) else str.getBytes
    val mem = new Memory(length)
    mem.write(0, bytes, 0, length);
    mem
  }

  def buildCodecList(codec: AVCodec): List[String] = {
    val ac = CodecLibrary.av_codec_next(codec)
    if (ac == null) {
      Nil
    } else {
      ac.name.getString(0) :: buildCodecList(ac)
    }
  }

  def buildFormatList(format: AVInputFormat): List[String] = {
    val ac = FormatLibrary.av_iformat_next(format)
    if (ac == null) {
      Nil
    } else {
      ac.name.getString(0) :: buildFormatList(ac)
    }
  }

  def buildFormatList(format: AVOutputFormat): List[String] = {
    val ac = FormatLibrary.av_oformat_next(format)
    if (ac == null) {
      Nil
    } else {
      ac.name :: buildFormatList(ac)
    }
  }

  def convertToString(pointer: Pointer): String = {
    pointer.getString(0)
  }

  implicit def wrap(buf: ByteBuffer) = {
    new BufferWrapper(buf)
  }

  implicit def wrap(mem: Memory) = {
    new MemoryZeroWrapper(mem)
  }

  class MemoryZeroWrapper(mem: Memory) {
    def zero(offset: Int, len: Int) = {
      val zero = List.fill(len)(0.asInstanceOf[Byte])
      mem.write(offset, zero.toArray, 0, len)
    }
  }

  class BufferWrapper(buf: ByteBuffer) {
    def zero = {
      for (i <- 0 until buf.capacity) {
        buf.put(0.asInstanceOf[Byte])
      }
      buf.rewind
    }

    def toCharString = {
      buf.rewind
      val bytearr: Array[Byte] = new Array(buf.remaining())
      buf.get(bytearr);
      new String(bytearr);
    }

    def toBytesString = {
      buf.rewind
      val bytearr: Array[Byte] = new Array(buf.remaining())
      buf.get(bytearr);
      val sb = new StringBuilder
      bytearr.foreach { b =>
        sb.append(b.intValue.toString).append(" ")
      }
      sb.toString
    }

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
        trace("Call result= " + code)
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


