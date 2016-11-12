package fpinscala.monads

import org.scalatest.FunSuite

/**
  * Created by osocron on 4/11/16.
  */
class Functor$Test extends FunSuite {

  import Functor._

  test("list functor works") {
    val mapped = listFunctor.map(List(1,2,3,4,5))(_ * 2)
    assert(mapped == List(2,4,6,8,10))
  }

}
