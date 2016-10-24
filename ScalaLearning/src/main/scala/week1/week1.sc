def absolute(x: Double) = if (x > 0) x else -x

def sqrt(x: Double) = {

  def sqrtIter(guess: Double): Double =
    if (isGoodEnough(guess)) guess
    else sqrtIter(improve(guess))

  def isGoodEnough(guess: Double): Boolean =
    absolute(x - (guess * guess)) / x < 0.001

  def improve(guess: Double): Double = (guess + (x / guess)) / 2

  sqrtIter(1)

}

sqrt(4)

def gcd(a: Double, b: Double): Double =
  if (b == 0) a else gcd(b, a % b)

gcd(40, 88)