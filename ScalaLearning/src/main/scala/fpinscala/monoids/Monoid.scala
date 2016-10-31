package fpinscala.monoids

/**
  * Created by osocron on 10/17/16.
  */
trait Monoid[A] {
  def op(a1: A, a2: A): A
  def zero: A
}

object Monoid {

  val stringMonoid = new Monoid[String] {
    override def op(a1: String, a2: String): String = a1 + a2
    override def zero: String = ""
  }

  def listMonoid[A] = new Monoid[List[A]] {
    override def op(a1: List[A], a2: List[A]): List[A] = a1 ++ a2
    override def zero: List[A] = Nil
  }

  val intAddition: Monoid[Int] = new Monoid[Int] {
    override def op(a1: Int, a2: Int): Int = a1 + a2
    override def zero: Int = 0
  }

  val intMultiplication: Monoid[Int] = new Monoid[Int] {
    override def op(a1: Int, a2: Int): Int = a1 * a2
    override def zero: Int = 1
  }

  val booleanOr: Monoid[Boolean] = new Monoid[Boolean] {
    override def op(a1: Boolean, a2: Boolean): Boolean = a1 || a2
    override def zero: Boolean = false
  }

  val booleanAnd: Monoid[Boolean] = new Monoid[Boolean] {
    override def op(a1: Boolean, a2: Boolean): Boolean = a1 && a2
    override def zero: Boolean = true
  }

  def optionMonoid[A]: Monoid[Option[A]] = new Monoid[Option[A]] {
    override def op(a1: Option[A], a2: Option[A]): Option[A] = a1 orElse a2
    override def zero: Option[A] = None
  }

  // We can get the dual of any monoid just by flipping the `op`.
  def dual[A](m: Monoid[A]): Monoid[A] = new Monoid[A] {
    def op(x: A, y: A): A = m.op(y, x)
    val zero = m.zero
  }

  /**
    * A function having the same argument and return type is an endofunction.
    * This is the monoid for endofunctions.
    * */
  def endoMonoid[A]: Monoid[A => A] = new Monoid[A => A] {
    override def op(a1: A => A, a2: A => A): A => A = a1.compose(a2)
    override def zero: A => A = a => a
  }

  def trimMonoid: Monoid[String] = new Monoid[String] {
    override def op(a1: String, a2: String): String = a1.trim + " " + a2.trim
    override def zero: String = ""
  }

  /**
    * Function that folds a list with a monoid.
    *
    * @param as A list
    * @param m  The monoid implementation of type A
    * @tparam A The type of elements in the list
    * @return   A single A
    */
  def concatenate[A](as: List[A], m: Monoid[A]): A = as.fold(m.zero)(m.op)

  /**
    * If a category does not have a Monoid instance we can always morph
    * using a functor to a category that does have a Monoid instance.
    *
    * @param as A list of category A
    * @param m  A monoid instance of category B
    * @param f  A functor from A to B
    * @tparam A Category A
    * @tparam B Category B
    * @return   The concatenation of category B
    */
  def foldMap[A,B](as: List[A], m: Monoid[B])(f: A => B): B =
    as.map(f).fold(m.zero)(m.op)

  def fold[A, B](xs: List[A])(z: B)(f: (A, B) => B): B =
    foldMap(xs, endoMonoid[B])(a => b => f(a, b))(z)

  def foldLeft[A, B](as: List[A])(z: B)(f: (B, A) => B): B =
    foldMap(as, dual(endoMonoid[B]))(a => b => f(b, a))(z)

}
