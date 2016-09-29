package fpinscala.lazyStuff

/**
  * Created by osocron on 9/14/16.
  */
trait Stream[+A] {

  import Stream._

  def uncons: Option[(A, Stream[A])]

  def isEmpty: Boolean = uncons.isEmpty

  def toList: List[A] = uncons match {
    case None => Nil
    case Some((h, t)) => h :: t.toList
  }

  def take(n: Int): Stream[A] = uncons match {
    case None => empty
    case Some((h, t)) =>
      if (n == 0) empty
      else cons(h, t.take(n - 1))
  }

  def takeWhile(f: A => Boolean): Stream[A] = uncons match {
    case None => empty
    case Some((h, t)) =>
      if (f(h)) cons(h, t.takeWhile(f))
      else empty
  }

  def foldRight[B](z: => B)(f: (A, => B) => B): B =
    uncons match {
      case Some((h, t)) => f(h, t.foldRight(z)(f))
      case None => z
    }

  def exists(p: A => Boolean): Boolean =
    foldRight(false)((a, b) => p(a) || b)

  def forAll(p: A => Boolean): Boolean =
    foldRight(true)((a, b) => p(a) && b)

  def takeWhile_1(f: A => Boolean): Stream[A] =
    foldRight(empty[A])((a, b) => if (f(a)) cons(a, b) else empty)

  def map[B](f: A => B): Stream[B] =
    foldRight(empty[B])((a,b) => cons[B](f(a), b))

  def filter(p: A => Boolean): Stream[A] =
    foldRight(empty[A])((a, b) => if (p(a)) cons(a, b) else b)

  def append[B>:A](s: => Stream[B]): Stream[B] =
    foldRight(s)((h, t) => cons(h, t))

  def flatMap[B](f: A => Stream[B]): Stream[B] =
    foldRight(empty[B])((h, t) => f(h) append t)

}

object Stream {

  def from(i: Int): Stream[Int] = Stream.cons(i, from(i + 1))

  def constant[A](i: A): Stream[A] = Stream.cons(i, constant(i))

  def empty[A]: Stream[A] =
    new Stream[A] { def uncons = None }

  def cons[A](hd: => A, tl: => Stream[A]): Stream[A] =
    new Stream[A] {
      lazy val uncons = Some((hd, tl))
    }

  def apply[A](as: A*): Stream[A] =
    if (as.isEmpty) empty
    else cons(as.head, apply(as.tail: _*))

}

object StreamTest extends App {

  val infiniteOnes: Stream[Int] = Stream.cons(1, infiniteOnes)

  infiniteOnes.take(5).toList.foreach(println)

}