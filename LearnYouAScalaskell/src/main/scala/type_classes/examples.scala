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

  def bmiTell(a: Double): String = a match {
    case d if d <= 18.5 => "You're underweight, you emo, you!"
    case d if d <= 25.0 => "You're supposedly normal. Pffft, I bet you're ugly!"
    case d if d <= 25.0 => "You're fat! Lose some weight, fatty!"
    case _ => "You're a whale, congratulations!"
  }



}
