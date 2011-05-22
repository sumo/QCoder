package biz.mediabag.qcoder.ffmpeg

import org.scalatest.FunSuite
import java.io._
import FFmpegUtils._
import avformat._
import avcodec._
import avutil._

class FFmpegIOSuite extends FunSuite {
  //  test("Container factory registers codecs without exception") {
  //    val x = FFmpegUtils
  //  }

//  test("Load file") {
//    val formatCtx = FormatLibrary.avformat_alloc_context
//    println("-")
//    FFmpegCall { FormatLibrary.av_open_input_file(formatCtx.castToReferenceArray, "src/test/data/short.avi", null, 0, null) }
//    println("--")
//    println(formatCtx.toString)
//    FFmpegCall { FormatLibrary.av_find_stream_info(formatCtx) }
//    println("---")
//    FormatLibrary.dump_format(formatCtx, 0, "", 0)
//    println("----")
//  }

  test("Container factory loads file and returns correct streams") {
    val x = new FFmpegContainerFactory
    val c = x.loadFrom(new File("src/test/data/short.avi"))
    println(c.bitRate)
    println(c.duration)
    println(c.formatName)
    println(c.longFormatName)
    println(c.startTime)
    println(c.streams)
  }

}