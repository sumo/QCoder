package biz.mediabag.qcoder.domain


object Codec extends Enumeration {
  type Codec = Value
  val audio, video, subtitle = Value
}
/**
 * A decoded raw frame of audio or video
 */
trait Frame {

}

trait AudioFrame extends Frame {
  def samples: Array[Byte]
}

trait VideoFrame extends Frame {
  def rgbBytes: Array[Byte]
}

trait SubtitleFrame extends Frame {

}