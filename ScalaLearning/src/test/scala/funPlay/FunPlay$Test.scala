package funPlay

import org.scalatest.FunSuite

/**
  * Created by osocron on 9/28/16.
  */
class FunPlay$Test extends FunSuite {

  implicit val stringN = 5
  implicit val pLength = (s: String, n: Int) => s.length == n
  implicit val intLength = (a: Int, b: Int) => a.toString.length == b

  val wString = LengthN("12345")

  test("application should return true") {
    assert(FunPlay.application("12345"))
  }

  test("wString should be a StringN") {
    wString match {
      case LengthN(s) => assert(s == "12345")
      case InvalidL => assert(wString.isInstanceOf[InvalidL.type])
    }
  }

  test("mapping a LengthN to an int should work") {
    assert(wString.map(_.toInt).getOrElse(5) == 12345)
  }

}
