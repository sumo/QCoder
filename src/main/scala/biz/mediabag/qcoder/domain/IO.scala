package biz.mediabag.qcoder.domain
                                     
import java.nio._
import java.nio.channels._
import java.io._

abstract class Stream[T <: Frame] {
  def frames: Seq[T]
}

abstract class Container[T <: Stream[_]] {
  def formatName:String
  def longFormatName:String
  def startTime:Option[Long]
  def duration:Option[Long]
  def bitRate:Option[Long]
  def streams:List[T]
}

abstract class ContainerFactory {
  def loadFrom(file:File) : Container[_] = {
    val fis = new FileInputStream(file)
    loadFrom(fis.getChannel)
  }
  def loadFrom(is:ReadableByteChannel) : Container[_]  
}
