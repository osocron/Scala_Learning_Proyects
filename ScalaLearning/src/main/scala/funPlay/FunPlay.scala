package funPlay

/**
  * Created by osocron on 9/28/16.
  */
object FunPlay {

  implicit val n: Int = 0

  type lNString = (String, Int) => Boolean

  def application(s: String)(implicit n: Int): Boolean = s.length == n

}

trait ValidType[+A] {

  def map[B](f: A => B)(implicit n: Int,
                        p: (B, Int) => Boolean): ValidType[B] = this match {
    case LengthN(v) => LengthN(f(v))
    case InvalidL => InvalidL
  }

  def flatMap[B](f: A => ValidType[B])
                (implicit n: Int,
                 p: (ValidType[B], Int) => Boolean): ValidType[B] =
    map(f) getOrElse InvalidL

  def getOrElse[B >: A](default: => B)(implicit n: Int,
                                       p: (B, Int) => Boolean): B = this match {
    case LengthN(v) => v
    case InvalidL => default
  }

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
