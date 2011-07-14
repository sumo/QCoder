package biz.mediabag.qcoder

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import java.nio.channels.Channels
import java.io.ByteArrayInputStream
import java.nio.ByteBuffer
import java.io.FileInputStream
import org.scalatest.matchers.ShouldMatchers

@RunWith(classOf[JUnitRunner])
class InputChannelRewinderSuite extends FunSuite with ShouldMatchers {

  test("Small buffer reads correctly") {
    val chan = Channels.newChannel(new ByteArrayInputStream("1234567890987654321".getBytes))
    val subject = new InputChannelRewinder(chan, 5)
    val buf = ByteBuffer.allocateDirect(5)
    subject.read(buf)
    assert(byteBufferToString(buf) == "12345", "Expected 12345 but was " + byteBufferToString(buf))
    buf.rewind
    subject.read(buf)
    assert(byteBufferToString(buf) == "67890")
  }

  test("Bytes after rewind are correct") {
    val chan = Channels.newChannel(new ByteArrayInputStream("1234567890987654321".getBytes))
    val subject = new InputChannelRewinder(chan, 20)
    val buf = ByteBuffer.allocateDirect(5)
    subject.read(buf)
    assert(byteBufferToString(buf) == "12345", "Expected 12345 but was " + byteBufferToString(buf))
    buf.rewind
    subject.read(buf)
    assert(byteBufferToString(buf) == "67890")
    subject.rewind
    buf.rewind
    subject.read(buf)
    assert(byteBufferToString(buf) == "12345")
  }

  val buf = ByteBuffer.allocateDirect(4096)
  val buf2 = ByteBuffer.allocateDirect(4096)
  val array1: Array[Byte] = new Array(4096)
  val array2: Array[Byte] = new Array(4096)

  test("Reads are match control") {
    val chan = new FileInputStream("src/test/data/preview.mp4").getChannel
    val subject = new InputChannelRewinder(chan)
    subject.read(buf)
    buf.rewind
    subject.rewind
    val controlChan = new FileInputStream("src/test/data/preview.mp4").getChannel
    var end = 0
    while (end != -1) {
      buf.rewind
      buf2.rewind
      end = controlChan.read(buf2)
      subject.read(buf)
      buf.rewind
      buf2.rewind
      buf.get(array1)
      buf2.get(array2)
      array1 should equal(array2)
    }
  }

  test("Test on short.avi") {
    buf.rewind
    buf2.rewind
    val chan = new FileInputStream("src/test/data/short.avi").getChannel
    val subject = new InputChannelRewinder(chan)
    assert(subject.read(buf) == 4096, "First read did not read 4096 bytes")
    subject.rewind
    assert(subject.read(buf2) == 4096, "Second read did not read 4096 bytes")
    buf.rewind
    buf2.rewind
    buf.get(array1)
    buf2.get(array2)
    array1 should equal(array2)

  }

  test("Sequential reads on short.avi") {
    buf.rewind
    buf2.rewind

    val chan = new FileInputStream("src/test/data/short.avi").getChannel
    val subject = new InputChannelRewinder(chan)
    assert(subject.read(buf) == 4096, "Buf 1 first read did not read 4096 bytes")
    assert(subject.read(buf2) == 4096, "Buf 2 first read did not read 4096 bytes")
    buf.rewind
    buf2.rewind
    buf.get(array1)
    buf2.get(array2)
    array1 should not equal (array2)
  }

  test("Sequential reads with rewind on short.avi") {
    buf.rewind
    buf2.rewind

    val chan = new FileInputStream("src/test/data/short.avi").getChannel
    val subject = new InputChannelRewinder(chan)

    assert(subject.read(buf) == 4096, "Buf 1 first read did not read 4096 bytes")
    buf.rewind
    assert(subject.read(buf) == 4096, "Buf 1 second read did not read 4096 bytes")
    buf.rewind
    assert(subject.read(buf) == 4096, "Buf 1 third did not read 4096 bytes")
    buf.rewind
    assert(subject.read(buf) == 4096, "Buf 1 forth did not read 4096 bytes")
    buf.rewind
    buf.get(array1)

    subject.rewind

    assert(subject.read(buf2) == 4096, "Buf 2 first read did not read 4096 bytes")
    buf2.rewind
    assert(subject.read(buf2) == 4096, "Buf 2 second read did not read 4096 bytes")
    buf2.rewind
    assert(subject.read(buf2) == 4096, "Buf 2 third read did not read 4096 bytes")
    buf2.rewind
    assert(subject.read(buf2) == 4096, "Buf 2 forth read did not read 4096 bytes")
    buf2.rewind
    buf2.get(array2)

    array2 should equal(array1)
  }

  def byteBufferToString(buf: ByteBuffer) = {
    buf.rewind
    val bytearr: Array[Byte] = new Array(buf.remaining())
    buf.get(bytearr);
    new String(bytearr);
  }
}