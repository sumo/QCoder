package biz.mediabag.qcoder
import java.io.File
import java.io.RandomAccessFile
import java.nio.channels.FileChannel
import java.nio.channels.ReadableByteChannel
import java.nio.ByteBuffer
import grizzled.slf4j.Logging

class InputChannelRewinder(chan: ReadableByteChannel, memSize:Int = 10 * 1024 * 1024) extends Logging {

  private val file = File.createTempFile("qcoder", ".tmp")
  file.deleteOnExit
  private val mappedBuffer = new RandomAccessFile(file, "rw").getChannel.map(FileChannel.MapMode.READ_WRITE, 0, memSize)
  private var eof = false
  private var remaining = 0
  private var remainingForRewind = 0
  checkEOFAndReload

  def read(buffer: ByteBuffer) = {
    if (!checkEOFAndReload) {
      val limit = if (buffer.remaining >= remaining) {
        remaining
      } else {
        buffer.remaining
      }
      for (i <- 0 until limit) {
        val b = mappedBuffer.get
        buffer.put(b)
        remaining -= 1
      }
      limit
    } else {
      -1
    }
  }

  private def checkEOFAndReload: Boolean = {
    if (eof) {
      true
    } else if (remaining == 0) {
      if (mappedBuffer.remaining == 0) {
        debug("Reusing mapped buffer, rewind will not rewind to start of original channel")
        mappedBuffer.rewind
      }
      mappedBuffer.mark
      remaining = chan.read(mappedBuffer)
      remainingForRewind = remaining
      val x = remaining
      mappedBuffer.reset
      debug("Cached " + remaining + " bytes")
      eof = remaining == -1
      eof
    } else {
      false
    }

  }

  def rewind = {
    mappedBuffer.rewind
    remaining = remainingForRewind
  }

}