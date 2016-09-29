package chapter3

import java.io.{InputStream, FilterInputStream}

/**
  * Created by osocron on 3/18/16.
  */
class LowerCaseInputStream(in: InputStream) extends FilterInputStream(in){

  override def read(): Int = {
    val c = super.read()
    if (c == -1) c else Character.toLowerCase(c)
  }

  override def read(b: Array[Byte], off: Int, len: Int): Int = {
    val res = super.read(b, off, len)
    for(i <- 0 to (off+res)){
      b(i) = Character.toLowerCase(b(i)).toByte
    }
    res
  }

}
