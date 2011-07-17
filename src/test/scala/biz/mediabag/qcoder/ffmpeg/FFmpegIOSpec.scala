package biz.mediabag.qcoder.ffmpeg

import org.scalatest.FunSuite
import java.io._
import FFmpegUtils._
import avformat._
import avcodec._
import avutil._
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith

@RunWith(classOf[JUnitRunner])
class FFmpegIOSuite extends FunSuite {
//  test("Container factory loads file and returns correct streams") {
//    val x = new FFmpegDecodingContainerFactory
//    val c = x.loadFrom(new File("src/test/data/short.avi"))
//    println(c.bitRate)
//    println(c.duration)
//    println(c.formatName)
//    println(c.longFormatName)
//    println(c.startTime)
//    println(c.streams)
//  }
//
//  test("Container factory create new container for format 3gp") {
//    val x = new FFmpegEncodingContainerFactory
//    val c = x.writeTo("3gp", "mpeg4", "mpeg4")
//    println(c.bitRate)
//    println(c.duration)
//    println(c.formatName)
//    println(c.longFormatName)
//    println(c.startTime)
//    println(c.streams)
//  }
  
  test("Container factory runs decode loop") {
    val x = new FFmpegDecodingContainerFactory
    val c = x.loadFrom(new File("src/test/data/preview.mp4"))
    c.runDecodeLoop
  }

}