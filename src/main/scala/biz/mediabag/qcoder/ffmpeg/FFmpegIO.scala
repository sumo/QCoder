package biz.mediabag.qcoder.ffmpeg

import biz.mediabag.qcoder.domain._
import java.io._;
import avformat._
                                              
object FFmpegContainerFactory extends ContainerFactory {
                 
  AvformatLibrary.INSTANCE.av_register_all
  def loadFrom(is:InputStream) : FFmpegContainer = {
    
  }
}   

class FFmpegContainer extends Container {
  
} 

class FFmpegStream extends Stream {
  
}
