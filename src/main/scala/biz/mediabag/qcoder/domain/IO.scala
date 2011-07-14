package biz.mediabag.qcoder.domain

import java.nio._
import java.nio.channels._
import java.io._

trait Stream

trait DecodingStream[T <: Frame] extends Stream {
}

trait EncodingStream[T <: Frame] extends Stream {
}

trait Container[T <: Stream] {
  def formatName: String
  def longFormatName: String
  def startTime: Option[Long]
  def duration: Option[Long]
  def bitRate: Option[Long]
  def streams: List[T]
}

trait DecodingContainer[T <: Stream] extends Container[T] {
  def runDecodeLoop:Unit
}

trait DecodingContainerFactory[T <: DecodingStream[_]] {
  def loadFrom(file: File): DecodingContainer[T] = {
    val fis = new FileInputStream(file)
    loadFrom(fis.getChannel)
  }
  def loadFrom(is: ReadableByteChannel): DecodingContainer[T]
}

trait EncodingContainerFactory[T <: EncodingStream[_]] {
  def writeTo(format: String, videoCodec: String, audioCodec: String): Container[T]
}
