package utilCopy

import java.io.{BufferedReader, InputStreamReader, RandomAccessFile}
import java.nio.channels.FileChannel

import it.unimi.dsi.io.ByteBufferInputStream
import org.saddle.UTF8

/**
  * Created by osocron on 9/20/16.
  */
class CsvFile(path: String, encoding: String = UTF8) extends CsvSource {

  private val file = new RandomAccessFile(path, "r")
  private val chan = file.getChannel

  private val stream = ByteBufferInputStream.map(chan, FileChannel.MapMode.READ_ONLY)
  private val reader = new BufferedReader(new InputStreamReader(stream, encoding))

  def readLine = {
    val line = reader.readLine()
    if (line == null) file.close()
    line
  }

  override def toString = "CsvFile(%s, encoding: %s)".format(path, encoding)
}

object CsvFile {
  def apply(path: String, encoding: String = UTF8) = new CsvFile(path, encoding)
}
