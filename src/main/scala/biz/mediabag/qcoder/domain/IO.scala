package biz.mediabag.qcoder.domain
                                     
import java.io._

abstract class Stream[T <: Frame] {
  def frames: Seq[T] 
}

abstract class Container {
  def streams: Map[String, Stream[_]]
}

abstract class ContainerFactory {
  def loadFrom(file:File) : Container = {
    val fis = new FileInputStream(file)
    loadFile(fis)
  }
  def loadFrom(is:InputStream) : Container  
}
