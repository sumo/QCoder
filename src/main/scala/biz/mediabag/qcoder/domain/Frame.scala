package biz.mediabag.qcoder.domain

object Codec extends Enumeration {
  type Codec = Value
  val Video = Value("Video")
  val Audio = Value("Audio") 
  val Subtitle = Value("Subtitle")
}

import Codec._
	
trait Frame {
	def codecType:Codec
}

trait AudioFrame extends Frame {
	override val codecType = Codec.Audio
}

trait VideoFrame extends Frame {
	override val codecType = Codec.Video
}

