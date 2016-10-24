package topten

import scalaz._
import Scalaz._
import scala.collection.mutable
import scala.util.matching.Regex

/**
  * Created by osocron on 9/12/16.
  */
object TopTen extends App {

  def minSizeSubArraySum(xs: List[Int], s: Int): Int = {

    def loop(step: Int): Int =
      if (xs.sliding(step).toList.exists(_.sum >= s)) step
      else loop(step + 1)

    (xs.sum >= s) ? loop(1) | 0

  }

  println(minSizeSubArraySum(List(1,2,3,4,5), 10))

  def rotateArray(xs: List[Int], k: Int): List[Int] =
    xs.splitAt(xs.length - k) match {case (x, y) => List(y, x).flatten}

  println(rotateArray(List(1,2,3,4,5,6,7), 3))

  def reverseWords(s: String): String = s.split(' ').reverse.mkString(" ")

  println(reverseWords("The sky is blue"))

  val ops = "+-/*"

  def evalOp(s: String, n: String, m: String): String = {
    val a = n.toInt
    val b = m.toInt
    s match {
      case "+" => String.valueOf(a + b)
      case "-" => String.valueOf(a - b)
      case "*" => String.valueOf(a * b)
      case "/" => String.valueOf(a / b)
    }
  }

  def evalReversePolishNotation(input: List[String]): Double =
    input.foldLeft(List[String]())((acc, s) => {
      if (ops.contains(s))
        evalOp(s, acc.tail.head, acc.head) :: acc.drop(2)
      else s :: acc}).head.toInt

  println(evalReversePolishNotation(List("4", "13", "5", "/", "+")))

}

