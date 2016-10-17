package funPlay

/**
  * Created by osocron on 9/28/16.
  */

trait ValidType[+A] {

  def map[B](f: A => B)(implicit n: Int,
                        p: (B, Int) => Boolean): ValidType[B] = this match {
    case LengthN(v) => LengthN(f(v))
    case InvalidL => InvalidL
  }

  def getOrElse[B >: A](default: => B)(implicit n: Int,
                                       p: (B, Int) => Boolean): B = this match {
    case LengthN(v) => v
    case InvalidL => default
  }

  def flatMap[B](f: A => ValidType[B])
                (implicit n: Int,
                 p: (ValidType[B], Int) => Boolean): ValidType[B] =
    map(f) getOrElse InvalidL


}

case object InvalidL extends ValidType[Nothing]

class LengthN[A](val v: A)
                 (implicit val n: Int,
                  val p: (A, Int) => Boolean) extends ValidType[A]

object LengthN {

  def apply[A](v: A)(implicit n: Int, p: (A, Int) => Boolean): ValidType[A] =
    if (p(v, n)) new LengthN(v) else InvalidL

  def unapply[A](arg: LengthN[A]): Option[A] = Some(arg.v)

}

trait ConstrainedType[+A, +B]

case object Invalid extends ConstrainedType[Nothing, Nothing]

class Constrained[A, B](val v: A)
                       (implicit val contraintValue: B,
                        val p: (A, B) => Boolean) extends ConstrainedType[A, B]

object Constrained {

  def apply[A, B](v: A)
                 (implicit constraintValue: B,
                  p: (A, B) => Boolean): ConstrainedType[A, B] =
    if (p(v, constraintValue)) new Constrained[A, B](v) else Invalid

  def unapply[A, B](arg: Constrained[A, B]): Option[A] = Some(arg.v)

}