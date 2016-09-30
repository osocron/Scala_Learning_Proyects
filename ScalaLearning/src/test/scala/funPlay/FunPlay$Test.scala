package funPlay

import org.scalatest.FunSuite

/**
  * Created by osocron on 9/28/16.
  */
class FunPlay$Test extends FunSuite {

  implicit val stringN = 5

  val wString = StringN("12345")

  test("application should return true") {
    assert(FunPlay.application("12345"))
  }

  test("wString should be a StringN") {
    wString match {
      case StringN(s) => assert(s == "12345")
      case InvalidS => assert(wString.isInstanceOf[InvalidS.type])
    }
  }



}
