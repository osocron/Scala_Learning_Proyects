val MOD_ADLER = 65521
def toAdler32(s: String): Int = {
  val listChars = s.getBytes.toList
  def loop(list: List[Byte], values: (Int, Int)): (Int, Int) = list match {
    case Nil => values
    case h :: t =>
      val aRes = (h + values._1) % MOD_ADLER
      loop(t,(aRes,(values._2 + aRes) % MOD_ADLER))
  }
  val pair = loop(listChars,(1,0))
  pair._2 * 65536 + pair._1
}

def adler32sum(s: String): Int = {
  var a = 1
  var b = 0
  s.getBytes.foreach{char =>
    a = (char + a) % MOD_ADLER
    b = (b + a) % MOD_ADLER
  }
  // note: Int is 32 bits, which this requires
  b * 65536 + a
  // or (b << 16) + a
}

val myTest = toAdler32("osocron")
val theirTest = adler32sum("osocron")