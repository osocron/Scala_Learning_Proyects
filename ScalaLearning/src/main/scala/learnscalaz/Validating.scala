package learnscalaz

import scalaz._
import Scalaz._

/**
  * Created by osocron on 9/13/16.
  */
object Validating extends App {

  def validate[F[_] : Foldable, A, B : Monoid]
  (in : F[A])
  (out : A => B): ValidationNel[Throwable, B] = {
    in foldMap (a => Validation.fromTryCatchNonFatal[B](out(a)).toValidationNel)
  }

  def toInts(maybeInts: List[String]): ValidationNel[Throwable, List[Int]] = {
    validate(maybeInts)(_.toInt :: Nil)
  }

  toInts(List("1", "2", "3", "4"))

}
