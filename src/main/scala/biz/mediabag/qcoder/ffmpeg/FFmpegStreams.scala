package biz.mediabag.qcoder.ffmpeg

import grizzled.slf4j.Logging
import biz.mediabag.qcoder._
import java.io._
import java.nio._
import avformat._
import avcodec._
import avutil._
import com.sun.jna._
import FFmpegUtils._
import biz.mediabag.qcoder.domain._
import scala.collection.immutable.Queue
import java.math.BigInteger

abstract class FFmpegStream(val avStream: AVStream) extends Logging {

  private val codecCtx: AVCodecContext = avStream.codec
  private val codec = CodecLibrary.avcodec_find_decoder(codecCtx.codec_id)
  if (codec == null) {
    throw new QCoderException("Decoder with id " + avStream.codec.codec_id + " not found for stream with index " + avStream.index)
  }
  private val codecName = new String(codec.name.getString(0))
  private val codecLongName = new String(codec.long_name.getString(0))
  private val codecType = codecCtx.codec_type
  private val codecHeight = codecCtx.height
  private val codecWidth = codecCtx.width
  private val codecHasBFrames = codecCtx.has_b_frames
  trace("CodecLibrary.avcodec_open")
  FFmpegCall {
    CodecLibrary.avcodec_open(avStream.codec, codec)
  }
  trace("/CodecLibrary.avcodec_open")
  codecCtx.debug = 1
  trace("Created stream " + toString)

  override def toString = {
    var ret = ":[codecName="
    ret += codecName + "; codecLongName=" + codecLongName
    ret += "; codecType=" + codecType
    ret += "; width=%d; height=%d; hasBFrames=%d]".format(codecWidth, codecHeight, codecHasBFrames)
    ret
  }

}

abstract case class FFmpegDecodingStream[FTYPE <: Frame](override val avStream: AVStream, val streamIdx: Int) extends FFmpegStream(avStream) with DecodingStream[FTYPE] {
  def <<(frame: FTYPE)
  def EOFReached
}

abstract class FFmpegEncodingStream[FTYPE <: Frame](avStream: AVStream, val streamIdx: Int) extends FFmpegStream(avStream) with EncodingStream[FTYPE] {
  def >>(frame: Frame)

}

class FFmpegDecodingVideoStream(avStream: AVStream, streamIdx: Int, handler: (FFmpegVideoFrame) => Unit) extends FFmpegDecodingStream[FFmpegVideoFrame](avStream, streamIdx) {
  override def <<(frame: FFmpegVideoFrame) = {
    handler(frame)
  }
  override def EOFReached = {
    println("End of stream " + toString)
  }
}

class FFmpegDecodingAudioStream(avStream: AVStream, streamIdx: Int, handler: (FFmpegAudioFrame) => Unit) extends FFmpegDecodingStream[FFmpegAudioFrame](avStream, streamIdx) {
  override def <<(frame: FFmpegAudioFrame) = {
    handler(frame)
  }
  override def EOFReached = {
    println("End of stream " + toString)
  }
}

case class FFmpegAudioFrame(val samplesPointer:Memory, size:Int, val pts:Long) extends AudioFrame {
  override val samples: Array[Byte] = {
    val bytes:Array[Byte] = new Array(size)
    samplesPointer.read(0, bytes, 0, size)
    bytes
  }
}

case class FFmpegVideoFrame(ffmpegFrame: AVFrame) extends VideoFrame {
  val pts = ffmpegFrame.pts
  val displayPictureNumber = ffmpegFrame.display_picture_number
  override def rgbBytes: Array[Byte] = Nil.toArray
}

trait FFmpegContainer {
  val formatCtx: AVFormatContext
  val output: Boolean
  val AV_NOPTS_VALUE = new BigInteger("8000000000000000", 16)
  val formatName: String = if (output) { formatCtx.oformat.name } else { formatCtx.iformat.name.getString(0) }
  val longFormatName: String = if (output) { formatCtx.oformat.long_name } else { formatCtx.iformat.long_name.getString(0) }
  val startTime: Option[Long] = if (AV_NOPTS_VALUE != formatCtx.start_time) { Some(formatCtx.start_time) } else { None }
  val duration: Option[Long] = if (formatCtx.duration != 0) { Some(formatCtx.duration) } else { None }
  val bitRate: Option[Long] = if (formatCtx.bit_rate != 0) { Some(formatCtx.bit_rate) } else { None }
}
