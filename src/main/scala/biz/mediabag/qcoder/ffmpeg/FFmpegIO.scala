package biz.mediabag.qcoder.ffmpeg

import biz.mediabag.qcoder.domain._
import java.io._
import avformat._ 
import avcodec._   
import com.sun.jna._
                                              
object FFmpegContainerFactory extends ContainerFactory {
                 
  val FormatLibrary = AvformatLibrary.INSTANCE
  FormatLibrary.av_register_all  
  val CodecLibrary = AvcodecLibrary.INSTANCE
  CodecLibrary.avcodec_register_all
  
  def loadFrom(is:InputStream) : FFmpegContainer = {
    val peakStream = new PeakStream(is)
    val memory =  new Memory(2261)
    memory.write(0, peakStream.peek(0, 2260), 0, 2260)
    val probeData = new AVProbeData(null, memory, 2261)
    val inputFmt = FormatLibrary.av_probe_input_format(probeData, 1)
    val formatCtxArray = List(new AVFormatContext.ByReference).toArray
    val bioContext = new ByteIOContext
    bioContext.is_streamed = 1
    FormatLibrary.av_open_input_stream(formatCtxArray, bioContext, null, inputFmt, null)
    
  }
}   

class FFmpegContainer extends Container {
  
} 

class FFmpegStream extends Stream {
  
}

class PeakStream(val is:InputStream) {
  val pushBack = new PushbackInputStream(is)
  
  def peek(offset:Int, length:Int):Array[Byte]  = {
    val ret = new Array[Byte](length) 
    pushBack.read(ret, offset, length)
    pushBack.unread(ret, offset, length)
    ret
  }
}