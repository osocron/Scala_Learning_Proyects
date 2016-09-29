trait Plus[A] {
  def plus(a1: A, a2: A): A
}

def plus[A: Plus](a1: A, a2: A): A = implicitly[Plus[A]].plus(a1, a2)

trait Monoid[A] {
  def mappend(a1: A, a2: A): A
  def mzero: A
}

object Monoid {
  implicit val IntMonoid: Monoid[Int] = new Monoid[Int] {
    def mappend(a: Int, b: Int): Int = a + b
    def mzero: Int = 0
  }
  implicit val StringMonoid: Monoid[String] = new Monoid[String] {
    def mappend(a: String, b: String): String = a + b
    def mzero: String = ""
  }
}

trait FoldLeft[F[_]] {
  def foldLeft[A, B](xs: F[A], b: B, f: (B, A) => B): B
}

object FoldLeft {
  implicit val FoldLeftList: FoldLeft[List] = new FoldLeft[List] {
    override def foldLeft[A, B](xs: List[A], b: B, f: (B, A) => B): B = xs.foldLeft(b)(f)
  }
}

def sum[M[_]: FoldLeft, A: Monoid](xs: M[A]): A = {
  val m = implicitly[Monoid[A]]
  val f1 = implicitly[FoldLeft[M]]
  f1.foldLeft(xs, m.mzero, m.mappend)
}

sum(List(1,2,3,4,5,6))

sum(List("Hello", " ", "world!"))

// Scala's way of handling errors

class Coffee
class Beans

object CoffeeService {

  val price = 3

  def purchaseCoffee(money: Int): Option[Coffee] =
    for {
      beans <- buyBeans(money)
      coffee <- brewCoffee(beans)
    } yield coffee


  def buyBeans(money: Int): Option[Beans] =
    if (money < price) None else Some(new Beans)

  def brewCoffee(beans: Beans): Option[Coffee] =
    if (Math.random < 0.25) None else Some(new Coffee)


}

import java.time.LocalDate

sealed abstract class RoastLevel(val value: Int)
object RoastLevel {
  case object VeryLight extends RoastLevel(1)
  case object Light     extends RoastLevel(2)
  case object Medium    extends RoastLevel(3)
  case object Dark      extends RoastLevel(4)
  case object Burnt     extends RoastLevel(5)
}

trait Roast {
  def level: RoastLevel
  def date: LocalDate
  def isEven: Boolean
}
case class UnevaluatedRoast(level: RoastLevel, date: LocalDate, isEven: Boolean) extends Roast
case class ApprovedRoast(level: RoastLevel, date: LocalDate, isEven: Boolean) extends Roast

case class RoastProblem(reason: String)

object RoastEvaluation {
  def evaluateRoastLevel(roastLevel: RoastLevel): Option[RoastProblem] = {
    if (roastLevel.value > 2)
      None
    else
      Some(RoastProblem(s"roast too light, at a ${roastLevel.value}"))
  }

  def evaluateFreshness(roastDate: LocalDate): Option[RoastProblem] = {
    if (roastDate.isAfter(LocalDate.now.minusDays(3)))
      None
    else
      Some(RoastProblem(s"not fresh, roast date ${roastDate} is more than 3 days old"))
  }

  def evaluateEvenness(roastIsEven: Boolean): Option[RoastProblem] = {
    if (roastIsEven)
      None
    else
      Some(RoastProblem("roast is not evenly distributed"))
  }

  def evaluateRoast(roast: Roast): Either[List[RoastProblem], ApprovedRoast] = {
    val problems = List(
      evaluateRoastLevel(roast.level),
      evaluateFreshness(roast.date),
      evaluateEvenness(roast.isEven)).flatten

    if (problems.isEmpty)
      Right(ApprovedRoast(roast.level, roast.date, roast.isEven))
    else
      Left(problems)
  }
}



