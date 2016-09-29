package funPlay

/**
  * Created by osocron on 9/28/16.
  */
object FunPlay {

  implicit val n: Int = 0

  type lNString = (String, Int) => Boolean

  def application(s: String)(implicit n: Int): Boolean = s.length == n

}

trait ValidType[+A]
case object InvalidS extends ValidType[String]
class StringN(val s: String)(implicit val n: Int) extends ValidType[String]
object StringN {
  def apply(s: String)(implicit n: Int): ValidType[String] =
    if (s.length == n) new StringN(s)(n) else InvalidS
  def unapply(arg: StringN): Option[String] = Some(arg.s)
}
