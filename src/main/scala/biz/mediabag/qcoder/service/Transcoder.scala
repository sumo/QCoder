package biz.mediabag.qcoder.service
                                   
import biz.mediabag.qcoder.domain._

trait Encoder[T <: Frame] {
  def encode(frame : T) : T 
}                                  

trait Decoder[T <: Frame] {
  def decode(frame: T) : T
}