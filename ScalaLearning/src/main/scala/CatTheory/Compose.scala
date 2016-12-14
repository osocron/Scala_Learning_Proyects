package CatTheory

import scalaz.Monoid

/**
  * Exercise from youtube's playlist Category Theory by Bartosz Milewski.
  *
  * [[https://youtu.be/i9CU4CuHADQ]]
  *
  */
object Compose {

  def composePairs[A, B, C, D: Monoid](f: A => (B, D), g: B => (C, D)): A => (C, D) =
    a => f(a) match {
      case (b, d) => g(b) match {
        case (c, d2) => (c, implicitly[Monoid[D]].append(d, d2))
      }
    }

  def identity[A, B: Monoid](a: A): (A, B) = (a, implicitly[Monoid[B]].zero)

}

trait Product
case class Left[A](a: A) extends Product
case class Right[A](a: A) extends Product


