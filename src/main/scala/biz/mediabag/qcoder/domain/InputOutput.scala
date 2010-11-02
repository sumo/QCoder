package biz.mediabag.qcoder.domain

abstract class Stream[T <: Frame] {
  val frames: Seq[T] 
}

abstract class File {
  val streams: Map[String, Stream[_]]
}

