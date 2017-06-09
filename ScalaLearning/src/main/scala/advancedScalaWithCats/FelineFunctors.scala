package advancedScalaWithCats

/**
  * Created by osocron on 8/06/17.
  */
object FelineFunctors extends App {

  import cats.Functor
  import cats.Show
  import cats.instances.int._
  import cats.instances.function._
  import cats.instances.list._
  import cats.instances.option._
  import cats.syntax.show._
  import cats.syntax.functor._

  val func1 = (x: Int) => x.toDouble
  val func2 = (y: Double) => y * 2
  val func3 = func1.map(func2)

  println(func3(11))

  val list1 = List(1, 2, 3)
  val list2 = Functor[List].map(list1)(_ * 2)

  val func = (x: Int) => x + 1
  val lifted = Functor[Option].lift(func)
  val result = lifted(Option(1))

  println(result.show)

  sealed trait Tree[+A]
  final case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]
  final case class Leaf[A](value: A) extends Tree[A]

  implicit val treeFunctor = new Functor[Tree] {
    override def map[A, B](fa: Tree[A])(f: (A) => B): Tree[B] = fa match {
      case Leaf(value) => Leaf(f(value))
      case Branch(left, right) => Branch(map(left)(f), map(right)(f))
    }
  }

  def branch[A](left: Tree[A], right: Tree[A]): Tree[A] = Branch(left, right)
  def leaf[A](value: A): Tree[A] = Leaf(value)

  leaf(100).map(_ * 2)
  branch(leaf(10), leaf(20)).map(_ * 2)

  trait Printable[A] { self =>

    def format(value: A): String

    def contramap[B](func: B => A): Printable[B] =
      (value: B) => self.format(func(value))

  }

  def format[A](value: A)(implicit p: Printable[A]): String = p.format(value)

  implicit val stringPrintable: Printable[String] =
    (value: String) => "\"" + value + "\""

  implicit val booleanPrintable: Printable[Boolean] =
    (value: Boolean) => if (value) "yes" else "no"

  println(format("Hello"))

  println(format(true))

  final case class Box[A](value: A)

  implicit def boxPrintableFormString[A](implicit p: Printable[A]): Printable[Box[A]] =
    p.contramap(_.value)

  //implicit def boxPrintableFormBoolean[A](implicit p: Printable[A]): Printable[Box[A]] =
    //booleanPrintable.contramap(b => b.value != null)

  println(format(Box("Hello")))

  trait Codec[A] { self =>
    def encode(value: A): String
    def decode(value: String): Option[A]

    def imap[B](dec: A => B, enc: B => A): Codec[B] = new Codec[B] {
      override def encode(value: B): String = self.encode(enc(value))
      override def decode(value: String): Option[B] = self.decode(value).map(dec(_))
    }
  }

  def encode[A](value: A)(implicit c: Codec[A]): String = c.encode(value)

  def decode[A](value: String)(implicit c: Codec[A]): Option[A] = c.decode(value)

  implicit val intCodec =
    new Codec[Int] {
      def encode(value: Int): String =
        value.toString
      def decode(value: String): Option[Int] =
        scala.util.Try(value.toInt).toOption
    }

  implicit val stringCode = new Codec[String] {
    override def encode(value: String): String = value
    override def decode(value: String): Option[String] = Some(value)
  }

  implicit def boxCodec[A](implicit c: Codec[A]): Codec[Box[A]] = c.imap(Box(_), _.value)

  println(format(encode(Box(123))))

  println(decode[Box[Int]]("123"))

}