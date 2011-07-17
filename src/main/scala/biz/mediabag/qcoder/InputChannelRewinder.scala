package biz.mediabag.qcoder
import java.io.File
import java.io.RandomAccessFile
import java.nio.channels.FileChannel
import java.nio.channels.ReadableByteChannel
import java.nio.ByteBuffer
import grizzled.slf4j.Logging
import java.io.IOException

class InputChannelRewinder(chan: ReadableByteChannel, memSize: Int = 10 * 1024 * 1024) extends Logging {

  private val file = File.createTempFile("qcoder", ".tmp")
  file.deleteOnExit
  private val fileBuffer = new RandomAccessFile(file, "rw")
  private var mappedBuffer = fileBuffer.getChannel.map(FileChannel.MapMode.READ_WRITE, 0, memSize)
  private var mappedSize = memSize
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
        // increase the size of the buffer
        mappedBuffer = fileBuffer.getChannel.map(FileChannel.MapMode.READ_WRITE, 0, mappedSize + memSize)
        mappedBuffer.position(mappedSize)
        mappedSize += memSize
        debug("Memory buffer size increase to " + mappedSize)
      }
      mappedBuffer.mark
      remaining = chan.read(mappedBuffer)
      val eof = if (remaining > 0) {
        remainingForRewind += remaining
        mappedBuffer.reset
        debug("Cached " + remaining + " bytes")
        false
      } else {
        true
      }
      eof
    } else {
      false
    }

  }

  def rewind = {
    mappedBuffer.rewind
    remaining = remainingForRewind
  }

  def seek(position: Int) = {
    if (position > remainingForRewind) {
      throw new IOException("Position " + position + " is beyond the number of remaining bytes of " + remainingForRewind)
    }
    mappedBuffer.position(position)
    remaining = remainingForRewind - position
    position
  }
  
  def position = mappedBuffer.position
  
  def size = remainingForRewind
  
  def close = fileBuffer.close

}