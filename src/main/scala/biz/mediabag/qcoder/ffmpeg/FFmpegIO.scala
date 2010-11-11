package biz.mediabag.qcoder.ffmpeg

import package biz.mediabag.qcoder.domain
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
