package shapes

import shapeless.{:+:, ::, CNil, Coproduct, Generic, HList, HNil, Inl, Inr, Lazy}

/**
  * Created by osocron on 26/12/16.
  */

//Products
case class IceCream(name: String, numCherries: Int, inCone: Boolean)

//Coproducts with products inside
sealed trait Shape
final case class Rectangle(width: Double, height: Double) extends Shape
final case class Circle(radius: Double) extends Shape

//Recursive data types
sealed trait TreeClass[A]
case class Branch[A](left: TreeClass[A], right: TreeClass[A]) extends TreeClass[A]
case class Leaf[A](value: A) extends TreeClass[A]

//Type class derivation
trait CsvEncoder[A] {
  def encode(value: A): List[String]
}

// Custom data type:
case class Employee(name: String, number: Int, manager: Boolean)

object CsvEncoder {

  // "Summoner" method
  def apply[A](implicit enc: CsvEncoder[A]): CsvEncoder[A] = enc

  // "Constructor" method
  def instance[A](func: A => List[String]): CsvEncoder[A] = (value: A) => func(value)

  // CsvEncoder instance for the custom data type:
  implicit val employeeEncoder: CsvEncoder[Employee] =
    instance(e => List(
      e.name,
      e.number.toString,
      if (e.manager) "yes" else "no"
    ))

  //We have to first give implicit encoders for the basic types

  implicit val stringEncoder: CsvEncoder[String] =
    instance(str => List(str))

  implicit val intEncoder: CsvEncoder[Int] =
    instance(num => List(num.toString))

  implicit val booleanEncoder: CsvEncoder[Boolean] =
    instance(bool => List(if(bool) "yes" else "no"))

  implicit val hnilEncoder: CsvEncoder[HNil] =
    instance(hnil => Nil)

  //We then define an encoder for HLists

  implicit def hlistEncoder[H, T <: HList](implicit
                                           hEncoder: Lazy[CsvEncoder[H]], //Wrap in lazy so that it works on recursive types
                                           tEncoder: CsvEncoder[T]
                                          ): CsvEncoder[H :: T] =
    instance {
      case h :: t => hEncoder.value.encode(h) ++ tEncoder.encode(t)
    }

  //Generic encoder will work for any case class or tuple that have the same shape
  //As our Employee encoder

  implicit def genericEncoder[A, R](
                                     implicit
                                     gen: Generic.Aux[A, R],
                                     enc: Lazy[CsvEncoder[R]] //Also warp in lazy
                                   ): CsvEncoder[A] =
    instance(a => enc.value.encode(gen.to(a)))

  //Now lets try with coproducts, or sum types

  implicit val cnilEncoder: CsvEncoder[CNil] =
    instance(cnil => throw new Exception("Inconceivable!"))

  implicit def coproductEncoder[H, T <: Coproduct](
                                                    implicit
                                                    hEncoder: Lazy[CsvEncoder[H]], //Also wrap in lazy
                                                    tEncoder: CsvEncoder[T]
                                                  ): CsvEncoder[H :+: T] = instance {
    case Inl(h) => hEncoder.value.encode(h)
    case Inr(t) => tEncoder.encode(t)
  }

  //We also need and implicit encoder for double

  implicit val doubleEncoder: CsvEncoder[Double] =
    instance(d => List(d.toString))

}

//Coproducts or Sum types

case class Red()
case class Amber()
case class Green()


object ShapingStuff extends App {

  //Products in Shapeless

  val product: String :: Int :: Boolean :: HNil = "Sunday" :: 1 :: false :: HNil

  val first = product.head

  val second = product.tail.head

  val rest = product.tail.tail

  val genIceCream = Generic[IceCream]

  val iceCream = IceCream("Sundae", 1, inCone = false)

  val repr = genIceCream.to(iceCream)

  val iceCream2 = genIceCream.from(repr)

  //Coproducts or Sum types

  type Light = Red :+: Amber :+: Green :+: CNil

  val red: Light = Inl(Red())

  val green: Light = Inr(Inr(Inl(Green())))

  //This is how sealed Traits and case classes are derived

  val gen = Generic[Shape]

  gen.to(Rectangle(3.0, 4.0))

  gen.to(Circle(1.0))


  //Type class derivation

  def writeCsv[A](values: List[A])(implicit enc: CsvEncoder[A]): String =
    values.map(value => enc.encode(value).mkString(",")).mkString("\n")

  val employees: List[Employee] = List(
    Employee("Bill", 1, manager = true),
    Employee("Peter", 2, manager = false),
    Employee("Milton", 3, manager = false)
  )

  println(writeCsv(employees))

  //Encoders for HLists

  val reprEncoder: CsvEncoder[String :: Int :: Boolean :: HNil] = implicitly

  println(reprEncoder.encode("abc" :: 123 :: true :: HNil))

  //Working with coproducts is very similar

  val shapes: List[Shape] = List(
    Rectangle(3.0, 4.0),
    Circle(1.0)
  )

  println(writeCsv(shapes))

  //Recursive data structures work if we wrap heads in Lazy
  val tree = Branch(Branch(Leaf(1), Leaf(4)), Leaf(2))

  val treeEncoder = CsvEncoder[TreeClass[Int]]

  println(treeEncoder.encode(tree))

}
