package CatTheory

import org.scalatest.FunSuite

/**
  * Created by osocron on 24/10/16.
  */
class Categories$Test extends FunSuite {

  import Categories._

  /**
    * Given an algebra A, B, C and D
    * where A = String
    *       B = Int
    *       C = Double
    *       D = Boolean
    *
    * And functions f, g, and h
    *
    * We can prove some properties of function composition.
    */

  type A = String
  type B = Int
  type C = Double
  type D = Boolean

  val f: A => B = a => a.size
  val g: B => C = b => b.toDouble
  val h: C => D = c => if (c <= 0) false else true
  val h_g: B => D = compose(g, h)
  val g_f: A => C = compose(f, g)

  /**
    * The identity function serves as units for function
    * composition
    */
  val idA: A => A = identity[A]
  val idB: B => B = identity[B]

  test("prove that (h compose g) compose f is equal to " +
    "h compose (g compose f)") {
    assert(compose(f, h_g)("Cats") == compose(g_f, h)("Cats"))
  }

  test("Prove that f composed idA = f = idB compose f") {
    assert(compose(idA, f)("Cats") == f("Cats"))
    assert(f("Cats") == compose(f, idB)("Cats"))
  }

}
