package fpinscala.datastructures

import org.scalatest.FunSuite

/**
  * Created by osocron on 9/30/16.
  */
class Tree$Test extends FunSuite {

  import Tree._

  val t = Branch(
    Branch(
      Leaf(1),
      Leaf(2)
    ),
    Branch(
      Leaf(3),
      Branch(
        Leaf(4),
        Leaf(5)
      )
    )
  )

  test("testMaximum") {
    assert(maximum(t) == 5)
  }

  test("testSize") {
    assert(size(t) == 9)
  }

  test("testFold") {
    assert(fold(t, 0)(_ + _) == 15)
  }

  test("testDepth") {
    assert(depth(t) == 3)
  }

  test("testMap") {
    assert(fold(map(t)(_ + 1), 0)(_ + _) == 20)
  }

}
