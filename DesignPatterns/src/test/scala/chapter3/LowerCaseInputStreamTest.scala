package chapter3

import java.io.{InputStream, FileInputStream, BufferedInputStream}

import org.scalatest.FunSuite

/**
  * Created by osocron on 3/18/16.
  */
class LowerCaseInputStreamTest extends FunSuite {
  test("LowerCaseInputStream should convert the input string to lowercase") {
    val in: InputStream = new LowerCaseInputStream(new BufferedInputStream(new FileInputStream("test.txt")))
    def loopThroughInputStream(inputStream: InputStream): Unit = {
      val c: Int = inputStream.read()
      if (c >= 0) {
        assert(c.toChar === c.toChar.toLower)
        loopThroughInputStream(inputStream)
      }
    }
    loopThroughInputStream(in)
  }
}
