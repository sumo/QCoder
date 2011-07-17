package biz.mediabag.qcoder.ffmpeg

import grizzled.slf4j.Logging
import biz.mediabag.qcoder._
import java.io._
import java.nio._
import avformat._
import avcodec._
import avutil._
import com.sun.jna._
import grizzled.slf4j.Logger
import FFmpegUtils._
import biz.mediabag.qcoder.domain._
import scala.collection.immutable.Queue

abstract class FFmpegStream(val avStream: AVStream) extends Logging {

  val codecCtx: AVCodecContext = avStream.codec
  private val codec = CodecLibrary.avcodec_find_decoder(avStream.codec.codec_id)
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

case class FFmpegAudioFrame(samples: ShortBuffer) extends AudioFrame {
  override def sample = -1
}
case class FFmpegVideoFrame(ffmpegFrame: AVFrame) extends VideoFrame {
  val pts = ffmpegFrame.pts
  override def rgbBytes: Array[Byte] = Nil.toArray
}