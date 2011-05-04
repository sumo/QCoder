package biz.mediabag.qcoder.ffmpeg
import biz.mediabag.qcoder._
import java.io._
import java.nio._
import avformat._
import avcodec._
import avutil._
import com.sun.jna._
import com.ochafik.lang.jnaerator.runtime._
import grizzled.slf4j.Logger
import FFmpegUtils._
import AvutilLibrary.AVMediaType._
import biz.mediabag.qcoder.domain._

object FFmpegStream {
  def apply(avStream: AVStream): FFmpegStream = {
    val codecCtx: AVCodecContext = avStream.codec
    val codec: AVCodec = codecCtx.codec
    codecCtx.codec_type match {
      case AVMEDIA_TYPE_VIDEO => new FFmpegVideoStream(avStream)
      case AVMEDIA_TYPE_AUDIO => new FFmpegAudioStream(avStream)
    }
  }
}

abstract class FFmpegStream(avStream: AVStream) extends Stream[Frame] {
  val codecCtx: AVCodecContext = avStream.codec
}

class FFmpegVideoStream(avStream: AVStream) extends FFmpegStream(avStream) {
  def frames: Seq[Frame] = null
  override def toString = {
    val dec = codecCtx.codec
    var ret = "Video:[codecName="
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

class FFmpegAudioStream(avStream: AVStream) extends FFmpegStream(avStream) {
  def frames: Seq[Frame] = null
}

class AudioFrame extends domain.AudioFrame {

}

class VideoFrame extends domain.VideoFrame {

}
