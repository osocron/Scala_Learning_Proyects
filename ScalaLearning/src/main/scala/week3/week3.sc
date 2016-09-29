

val x = new Rational(1,2)
x.numer
x.denom
val y = new Rational(2,3)
x + y
-x
x - y
x < y
x max y
val z = new Rational(14, 6)
-z
case class Rational(x: Int, y: Int){

  require( y != 0, "Denominator cannot be equal to zero!")

  def this(x: Int) = this(x, 1)

  private def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)

  def numer = x
  def denom = y

  def < (that: Rational) = numer * that.denom < that.numer * denom

  def max(that: Rational) = if (this < that) that else this

  def + (that: Rational) =
    new Rational(
      numer * that.denom + that.numer * denom,
      denom * that.denom
    )

  def unary_- = new Rational(-numer, denom)

  def - (that: Rational) = this + -that

  override def toString = {
    val g = gcd(numer, denom)
    numer / g + "/" + denom / g
  }
}