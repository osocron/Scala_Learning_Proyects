package fpinscala.fibo

import scalaz._
import Scalaz._

/**
  * Created by osocron on 8/24/16.
  */
object Fibonacci extends App {

  def fibo(n: Int): Int = {

    def fib(x: Int, y: Int): Int = x + y

    def loop(x: Int, y: Int, c: Int, acc: Int): Int = {
      if (c <= 0) acc
      else loop(y, fib(x, y), c - 1, fib(x, y))
    }

    loop(0, 1, n - 1, 1)

  }

  println(fibo(10))

  /**
    *This functions are supposed to be used quite commonly
    * They weren't hard to implement and are extremely useful.
    */

  def partial1[A, B, C](a: A, f: (A, B) => C): B => C = b => f(a, b)

  def curry[A,B,C](f: (A, B) => C): A => (B => C) = a => b => f(a, b)

  def uncurry[A,B,C](f: A => B => C): (A, B) => C = (a, b) => f(a)(b)

  def compose[A,B,C](f: B => C, g: A => B): A => C = a => a |> g |> f


  /*
   * Implementation of the partially applied function.
   */

  def f(i: Int, s: String): Boolean = ("0".toInt == i) ? true | false

  val pApp = partial1(2, f)

  pApp("0")

}
