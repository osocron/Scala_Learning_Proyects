package fpinscala.monoids

import org.scalatest.FunSuite

/**
  * Created by osocron on 10/17/16.
  */
class Monoid$Test extends FunSuite {

  import Monoid._

  test("testIntAddition op") {
    assert(intAddition.op(1, 2) == 3)
  }

  test("testIntAddition zero") {
    assert(intAddition.op(intAddition.zero, 1) == 1)
  }

  test("testBooleanOr op") {
    assert(booleanOr.op(true, false))
  }

  test("testBooleanOr zero") {
    assert(booleanOr.op(booleanOr.zero, true))
  }

  test("testListMonoid op") {
    assert(
      listMonoid.op(List(1), List(2)) == List(1, 2))
  }

  test("testListMonoid zero") {
    assert(
      listMonoid.op(
        listMonoid.zero, List(1)) == List(1))
  }

  test("testStringMonoid") {
    assert(
      stringMonoid.op("Hello", " world") == "Hello world"
    )
  }

  test("testBooleanAnd") {
    assert(
      booleanAnd.op(true, true)
    )
  }

  test("testIntMultiplication") {
    assert(
      intMultiplication.op(2, 4) == 8
    )
  }

}
