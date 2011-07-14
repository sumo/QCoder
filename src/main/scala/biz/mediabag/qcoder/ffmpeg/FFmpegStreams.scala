package biz.mediabag.qcoder.ffmpeg
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

abstract class FFmpegStream(val avStream: AVStream) {

  private val codec = CodecLibrary.avcodec_find_decoder(avStream.codec.codec_id);
  if (codec == null) {
    throw new QCoderException("Decoder with id " + avStream.codec.codec_id + " not found for stream with index " + avStream.index)
  }
  FFmpegCall {
    CodecLibrary.avcodec_open(avStream.codec, codec)
  }

  val codecCtx: AVCodecContext = avStream.codec
  override def toString = {
    val dec = codecCtx.codec
    var ret = ":[codecName="
    if (dec != null) {
      ret += dec.name + ";codecLongName=" + dec.long_name
    } else {
      ret += "unknown"
    }

    ret += ";codecType=" + codecCtx.codec_type //+ ";codecTimeBase=%d/%d".format(codecCtx.time_base.num, codeCtx.time_base.den)
    ret += ";width=%d;height=%d;hasBFrames=%d".format(codecCtx.width, codecCtx.height, codecCtx.has_b_frames)
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
  override def rgbBytes: Array[Byte] = Nil.toArray
}