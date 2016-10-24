package fpinscala.datastructures

/**
  * Created by osocron on 8/30/16.
  */

sealed trait List[+A]

case object Nil extends List[Nothing]

case class Cons[+A](head: A, tail: List[A]) extends List[A]

object List {

  def sum(ints: List[Int]): Int = ints match {
    case Nil => 0
    case Cons(x, xs) => x + sum(xs)
  }

  def product(ds: List[Double]): Double = ds match {
    case Nil => 1
    case Cons(0.0, _) => 0.0
    case Cons(x, xs) => x * product(xs)
  }

  def apply[A](as: A*): List[A] =
    if (as.isEmpty) Nil
    else Cons(as.head, apply(as.tail: _*))

  def tail[A](xs: List[A]): List[A] = xs match {
    case Nil => Nil
    case Cons(h, t) => t
  }

  def drop[A](xs: List[A], n: Int): List[A] =
    if (n == 0) xs else drop(tail(xs), n - 1)

  def dropWhile[A](xs: List[A])(f: A => Boolean): List[A] = xs match {
    case Nil => Nil
    case Cons(h, t) => if (f(h)) dropWhile(tail(xs))(f) else xs
  }

  def setHead[A](xs: List[A], h: A): List[A] = xs match {
    case Nil => Cons(h, Nil)
    case Cons(head, tail) => Cons(h, tail)
  }

  def init[A](l: List[A]): List[A] = l match {
    case Nil => Nil
    case Cons(h,Cons(t, Nil)) => Cons(h, Nil)
    case Cons(h, t) => Cons(h, init(t))
  }

  def foldRight[A,B](l: List[A], z: B)(f: (A, B) => B): B = l match {
    case Nil => z
    case Cons(x, xs) => f(x, foldRight(xs, z)(f))
  }

  def foreach[A](xs: List[A])(f: A => Unit): Unit = xs match {
    case Nil =>
    case Cons(h, t) => f(h); foreach(t)(f)
  }

  def length[A](l: List[A]): Int = foldRight(l, 0)((a, b) => b + 1)

  @annotation.tailrec
  def foldLeft[A,B](l: List[A], z: B)(f: (B, A) => B): B = l match {
    case Nil => z
    case Cons(h, t) => foldLeft(t, f(z, h))(f)
  }

  def sum1(xs: List[Int]): Int = foldLeft(xs, 0)((acc, x) => acc +x)

  def product1(xs: List[Double]): Double = foldLeft(xs, 1.0)((acc, x) => acc * x)

  def length1[A](l: List[A]): Int = foldLeft(l, 0)((acc, x) => acc + 1)

  /**
    * This is how reverse works with foldLeft
    *
    * List(1, 2, 3, 4, 5)
    *
    * 1- Cons(1, List(2, 3, 4, 5)) => foldLeft(List(2, 3, 4, 5), Cons(1, Nil))((acc, x) => Cons(x, acc))
    * 2- Cons(2, List(3, 4, 5)) => foldLeft(List(3, 4, 5), Cons(2, Cons(1, Nil)))((acc, x) => Cons(x, acc))
    * 3- Cons(3, List(4, 5)) => foldLeft(List(4, 5), Cons(3, Cons(2, Cons(1, Nil))))((acc, x) => Cons(x, acc))
    * 4- Cons(4, List(5)) => foldLeft(List(5), Cons(4, Cons(3, Cons(2, Cons(1, Nil)))))((acc, x) => Cons(x, acc))
    * 5- Cons(5, Nil) => foldLeft(Nil => Cons(5, Cons(4, Cons(3, Cons(2, Cons(1, Nil))))))((acc, x) => Cons(x, acc))
    * 6- Nil => Cons(5, Cons(4, Cons(3, Cons(2, Cons(1, Nil)))))
    *
    */
  def reverse[A](l: List[A]): List[A] = foldLeft(l, Nil: List[A])((acc, x) => Cons(x, acc))

  def foldLeft1[A,B](l: List[A], z: B)(f: (B, A) => B): B =
    foldRight(l, (b:B) => b)((a,g) => b => g(f(b,a)))(z)

  def append[A](xs: List[A], ys: List[A]): List[A] =
    foldRight(xs, ys)((acc, x) => Cons(acc, x))

  def flatten[A](xs: List[List[A]]): List[A] = foldLeft(xs, Nil: List[A])((acc, x) => append(acc, x))

  def map[A, B](xs: List[A])(f: A => B): List[B] = xs match {
    case Nil => Nil
    case Cons(h, t) => Cons(f(h), map(t)(f))
  }

  def dToS(xs: List[Double]): List[String] = map(xs)(x => x.toString)

  def filter[A](xs: List[A])(p: A => Boolean): List[A] = xs match {
    case Nil => Nil
    case Cons(h, t) => if (p(h)) Cons(h, filter(t)(p)) else filter(t)(p)
  }

  def flatMap[A,B](l: List[A])(f: A => List[B]): List[B] = l match {
    case Nil => Nil
    case Cons(h, t) => append(f(h), flatMap(t)(f))
  }

  def zip[A, B](xs: List[A], ys: List[B]): List[(A, B)] = xs match {
    case Nil => Nil
    case Cons(xh, xt) => ys match {
      case Nil => Nil
      case Cons(yh, yt) => Cons((xh, yh), zip(xt, yt))
    }
  }

  def listSum(xs: List[Int], ys: List[Int]): List[Int] =
    map(zip(xs, ys)){ case (x, y) => x + y }

  def zipWithFunction[A, B, C](xs: List[A], ys: List[B])(f: (A, B) => C) =
    map(zip(xs, ys)){ case (x, y) => f(x, y) }

}

object test extends App {

  import List._

  val l = List(1, 2, 3, 4, 5)
  val l2 = List(6, 7, 8, 9, 10)
  val l3 = List(List(1), List(2), List(3), List(4), List(5))

  val r1 = drop(l, 3)
  val r2 = dropWhile(l)(x => x <= 2)
  val r3 = init(l)
  val r4 = foldRight(l, Nil: List[Int])(Cons(_,_))
  val r5 = foldLeft(l, 0)((x, y) => x + y)
  val r6 = reverse(l)
  val r7 = append(l, l2)
  val r8 = flatten(l3)
  val r9 = map(l)(x => x <= 3)
  val r10 = filter(l)(x => x <= 3)
  val r11 = flatMap(l)(i => List(i, i))
  val r12 = listSum(l, l2)
  val r13 = zipWithFunction(l, l2)((x, y) => x + y)

  foreach(r13)(println)

}
