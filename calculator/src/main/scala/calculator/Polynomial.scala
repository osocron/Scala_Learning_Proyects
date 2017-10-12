package calculator

object Polynomial {
  def computeDelta(a: Signal[Double], b: Signal[Double],
                   c: Signal[Double]): Signal[Double] = {
    Signal {
      val aD = a()
      val bD = b()
      val cD = c()
      (bD * bD) - (4 * (aD * cD))
    }
  }

  def computeSolutions(a: Signal[Double], b: Signal[Double],
                       c: Signal[Double], delta: Signal[Double]): Signal[Set[Double]] = {
    Signal {
      val aD = a()
      val bD = b()
      val cD = c()
      val del = delta()
      if (del < 0) Set()
      else {
        val positive = (-bD + math.sqrt(del)) / (2 * aD)
        val negative = (-bD - math.sqrt(del)) / (2 * aD)
        Set(positive, negative)
      }
    }
  }
}
