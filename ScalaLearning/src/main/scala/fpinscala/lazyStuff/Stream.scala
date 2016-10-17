package fpinscala.lazyStuff

/**
  * Created by osocron on 9/14/16.
  */
trait Stream[+A] {

  import Stream._

  def toList: List[A] = this match {
    case Empty => Nil
    case Cons(h, t) => h() :: t().toList
  }

  /*
    Create a new Stream[A] from this, but ignore the n first elements. This can be achieved by recursively calling
    drop on the invoked tail of a cons cell. Note that the implementation is also tail recursive.
  */
  @annotation.tailrec
  final def drop(n: Int): Stream[A] = this match {
    case Cons(_, t) if n > 0 => t().drop(n - 1)
    case _ => this
  }

  def take(n: Int): Stream[A] = this match {
    case Empty => empty
    case Cons(h, t) =>
      if (n == 0) empty
      else cons(h(), t().take(n - 1))
  }

  def takeByUnfold(n: Int): Stream[A] = unfold(this) {
    case Cons(h, t) => Some(h(), t().take(n - 1))
    case Empty => None
  }

  def takeWhile(f: A => Boolean): Stream[A] = this match {
    case Empty => empty
    case Cons(h, t) =>
      if (f(h())) cons(h(), t().takeWhile(f))
      else empty
  }

  def takeWhileByUnfold(f: A => Boolean): Stream[A] = unfold(this) {
    case Cons(h, t) => if (f(h())) Some(h(), t().takeWhile(f)) else None
    case _ => None
  }

  def zipWith[B, C](s2: Stream[B])(f: (A, B) => C): Stream[C] = unfold((this, s2)) {
    case (Cons(h1, t1), Cons(h2, t2)) => Some((f(h1(), h2()), (t1(), t2())))
    case _ => None
  }

  def zip[B](s2: Stream[B]): Stream[(A, B)] = zipWith(s2)((_,_))

  def zipAll[B](s2: Stream[B]): Stream[(Option[A],Option[B])] =
    zipWithAll(s2)((_,_))

  def zipWithAll[B, C](s2: Stream[B])(f: (Option[A], Option[B]) => C): Stream[C] =
    Stream.unfold((this, s2)) {
      case (Empty, Empty) => None
      case (Cons(h, t), Empty) => Some(f(Some(h()), Option.empty[B]) -> (t(), empty[B]))
      case (Empty, Cons(h, t)) => Some(f(Option.empty[A], Some(h())) -> (empty[A] -> t()))
      case (Cons(h1, t1), Cons(h2, t2)) => Some(f(Some(h1()), Some(h2())) -> (t1() -> t2()))
    }

  def foldRight[B](z: => B)(f: (A, => B) => B): B =
    this match {
      case Cons(h, t) => f(h(), t().foldRight(z)(f))
      case Empty => z
    }

  def exists(p: A => Boolean): Boolean =
    foldRight(false)((a, b) => p(a) || b)

  def forAll(p: A => Boolean): Boolean =
    foldRight(true)((a, b) => p(a) && b)

  def takeWhile_1(f: A => Boolean): Stream[A] =
    foldRight(empty[A])((a, b) => if (f(a)) cons(a, b) else empty)

  def map[B](f: A => B): Stream[B] =
    foldRight(empty[B])((a,b) => cons[B](f(a), b))

  def mapByUnfold[B](f: A => B): Stream[B] = unfold(this) {
    case Cons(h, t) => Some(f(h()), t())
    case _ => None
  }

  def filter(p: A => Boolean): Stream[A] =
    foldRight(empty[A])((a, b) => if (p(a)) cons(a, b) else b)

  def append[B>:A](s: => Stream[B]): Stream[B] =
    foldRight(s)((h, t) => cons(h, t))

  def flatMap[B](f: A => Stream[B]): Stream[B] =
    foldRight(empty[B])((h, t) => f(h) append t)

  def tails: Stream[Stream[A]] = unfold(this) {
    case s => Some((s, s drop 1))
    case _ => None
  }

  def hasSubSequence[B](s: Stream[B]): Boolean = tails.exists(x => startsWith(x,s))

}

case object Empty extends Stream[Nothing]
case class Cons[+A](h: () => A, t: () => Stream[A]) extends Stream[A]

object Stream {

  def fib: Stream[Int] = {
    def fibo(n: Int): Int = if (n == 0 | n == 1) n else fibo(n - 1) + fibo(n - 2)
    def infiniteFib(n: Int): Stream[Int] = cons(fibo(n), infiniteFib(n + 1))
    infiniteFib(1)
  }

  def fibsByUnfold = {
    def fibo(n: Int): Int = if (n == 0 | n == 1) n else fibo(n - 1) + fibo(n - 2)
    unfold(1)(s => Some(fibo(s), s + 1))
  }

  /**
    * Produces a Stream using the concept of corecursion or productive recursion.
    *
    * Corecursion is different from recursion in the fact that recursive functions
    * consume data while corecursive ones generate data.
    *
    * @param z   The initial state
    * @param f   The function to generate the next state and value
    * @tparam A  The type of the values in the Stream
    * @tparam S  The type of the initial state
    * @return    An infinite Stream
    */
  def unfold[A, S](z: S)(f: S => Option[(A, S)]): Stream[A] = f(z) match {
    case Some((a, s)) => cons(a, unfold(s)(f))
    case None => empty
  }

  def from(i: Int): Stream[Int] = cons(i, from(i + 1))

  def fromByUnfold(i: Int) = unfold(i)(s => Some(s, s + 1))

  def constant[A](i: A): Stream[A] = cons(i, constant(i))

  def constantByUnfold[A](i: A): Stream[A] = unfold(i)(s => Some(s, s))

  def empty[A]: Stream[A] = Empty

  def startsWith[A](s: Stream[A], s2: Stream[A]): Boolean =
    s.zip(s2).forAll { case (a1, a2) => a1 == a2 }

  def cons[A](hd: => A, tl: => Stream[A]): Stream[A] = {
    lazy val head = hd
    lazy val tail = tl
    Cons(() => head, () => tail)
  }

  def apply[A](as: A*): Stream[A] =
    if (as.isEmpty) empty
    else cons(as.head, apply(as.tail: _*))

}

object StreamTest extends App {

  val infiniteOnes: Stream[Int] = Stream.cons(1, infiniteOnes)

  infiniteOnes.take(5).toList.foreach(println)

}