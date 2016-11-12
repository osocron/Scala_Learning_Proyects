package type_classes

trait Show[A] {
  def show(a: A): String
}

object examples extends App {

  implicit object IntShow extends Show[Int] {
    override def show(a: Int): String = a.toString
  }

  def tell[A](xs: List[A])(implicit sh: Show[A]): String = xs match {
    case Nil => "The list is empty"
    case x :: Nil => s"The list has one element: ${sh.show(x)}"
    case x :: y :: Nil => s"The list has two elements: ${sh.show(x)} and ${sh.show(y)}"
    case x :: y :: _ => s"The list is too long, the first two elements are : ${sh.show(x)} and ${sh.show(y)}"
  }

  println(tell(List(1, 2, 3, 4, 5, 6, 7, 8)))

}
