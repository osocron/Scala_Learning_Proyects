package funPlay

import org.scalatest.FunSuite
import scalaz.std.AllInstances._

/**
  * Created by osocron on 14/11/16.
  */
class ReversedTest extends FunSuite {

  import Reversed._

  val l = List(1,2,3,4,5)

  test("A list should be reversable") {
    assert(l.reverse == reversed(l))
  }

}
