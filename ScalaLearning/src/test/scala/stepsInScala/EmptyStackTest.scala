package stepsInScala

import org.scalatest.FunSuite

/**
  * Created by osocron on 7/14/16.
  */
class EmptyStackTest extends FunSuite {

  val empty = new EmptyStack[Int]

  test("EmptyStack should be true in isEmpty") {
    assert(empty.isEmpty)
  }

  test("EmptyStack should throw NoSuchElementException in top") {
    intercept[NoSuchElementException] {
      empty.top
    }
  }

  test("EmptyStack should return NonEmptyStack in push") {
    assert(NonEmptyStack[Int](1, new EmptyStack[Int]).top == new EmptyStack[Int].push(1).top)
  }

}
