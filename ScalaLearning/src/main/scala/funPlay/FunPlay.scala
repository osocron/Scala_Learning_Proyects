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
  def map[B](f: A => B): ValidType[B] = this match {
    case LengthN(v) => LengthN(f(v))
  }
}
case object InvalidL extends ValidType[Nothing]
class LengthN[A](val v: A)
                 (implicit val n: Int,
                  val p: (A, Int) => Boolean) extends ValidType[A]
object LengthN {
  def apply[A](v: A)(implicit n: Int, p: (A, Int) => Boolean): ValidType[A] =
    if (p(v, n)) new LengthN(v)(n, p) else InvalidL
  def unapply[A](arg: LengthN[A]): Option[A] = Some(arg.v)
}
