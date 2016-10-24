package fpinscala.exceptions

import scalaz._
import Scalaz._

/**
  * Exercises from chapter 4 Functional programming in Scala
  */
sealed trait Option[+A] {

  def map[B](f: A => B): Option[B] = this match {
    case None => None
    case Some(g) => Some(f(g))
  }

  def flatMap[B](f: A => Option[B]): Option[B] = map(f) getOrElse None

  def getOrElse[B >: A](default: => B): B = this match {
    case None => default
    case Some(g) => g
  }

  def orElse[B >: A](ob: => Option[B]): Option[B] = this map (Some(_)) getOrElse ob

  def filter(f: A => Boolean): Option[A] = this match {
    case Some(g) if f(g) => this
    case _ => None
  }

}

case class Some[+A](get: A) extends Option[A]
case object None extends Option[Nothing]

object Exceptions extends App {

  import java.util.regex._

  def mean_wrong(xs: Seq[Double]): Double =
    if (xs.isEmpty)
      throw new ArithmeticException("mean of empty list!")
    else xs.sum / xs.length

  def mean(xs: Seq[Double]): Option[Double] =
    if (xs.isEmpty) None else Some(xs.sum / xs.length)

  def variance(xs: Seq[Double]): Option[Double] = {
    val m = mean(xs)
    val squared = m.flatMap(m => {
      if (xs.isEmpty) None
      else Some(xs.map(d => math.pow(d - m, 2)))
    })
    squared.flatMap(s => mean(s))
  }

  def pattern(s: String): Option[Pattern] =
    try {
      Some(Pattern.compile(s))
    } catch {
      case e: PatternSyntaxException => None
    }

  /**
    * This function returns an Optional function by applying
    * map to an Option which will make it possible to lift it
    * and operate on its wrapped value.
    */
  def mkMatcher(pat: String): Option[String => Boolean] =
    pattern(pat) map (p => (s: String) => p.matcher(s).matches())

  def mkMatcher_1(pat: String): Option[String => Boolean] =
    for {
      p <- pattern(pat)
    } yield (s: String) => p.matcher(s).matches

  def doesMatch(pat: String, s: String): Option[Boolean] =
    for {
      p <- mkMatcher_1(pat)
    } yield p(s)

  def map2[A,B,C](a: Option[A], b: Option[B])(f: (A, B) => C): Option[C] =
    for {
      a1 <- a
      b1 <- b
    } yield f(a1, b1)


  def bothMatch(pat: String, pat2: String, s: String): Option[Boolean] =
    for {
      f <- mkMatcher(pat)
      g <- mkMatcher(pat2)
    } yield f(s) && g(s)

  def bothMatch2(pat: String, pat2: String, s: String): Option[Boolean] =
    map2(mkMatcher(pat), mkMatcher(pat2))((f, g) => f(s) && g(s))

  def sequence[A](a: List[Option[A]]): Option[List[A]] = a match {
    case Nil => Some(Nil)
    case h :: t => h match {
      case Some(v) => sequence(t).map(v :: _)
      case None => None
    }
  }

  def sequence_1[A](a: List[Option[A]]): Option[List[A]] =
    a.foldRight[Option[List[A]]](Some(Nil))((x,y) => map2(x,y)(_ :: _))

  def traverse[A, B](a: List[A])(f: A => Option[B]): Option[List[B]] = a match {
    case Nil => Some(Nil)
    case h :: t => map2(f(h), traverse(t)(f))(_ :: _)
  }

  def sequence_3[A](a: List[Option[A]]): Option[List[A]] = traverse(a)(x => x)

  sequence_3(List(Some(1), Some(2), Some(3), Some(4), Some(5))) match {
    case Some(list) => list.foreach(println)
    case None => println("None found on original list")
  }

}
