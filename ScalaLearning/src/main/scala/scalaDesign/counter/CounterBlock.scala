package scalaDesign.counter

import scala.util.Random

/**
  * Created by osocron on 7/5/16.
  */
object CounterBlock extends App {

  def mCount(iter: Int): Int = {

    val randomX = new Random
    val randomY = new Random

    def counterLoop[T](limit: Int, res: Int)(b: => T)(p: T => Boolean): Int = {
      if (limit == 0) res
      else if (p(b)) counterLoop(limit - 1, res + 1)(b)(p)
      else counterLoop(limit - 1, res)(b)(p)
    }

    def sumRandomPowers: Double = {
      val x = randomX.nextDouble()
      val y = randomY.nextDouble()
      x*x + y*y
    }

    counterLoop(iter, 0)(sumRandomPowers)(d => d < 1)

  }

  def monteCarloPiSeq(iter: Int): Double = 4.0 * mCount(iter) / iter

  println(monteCarloPiSeq(20000000))

}

