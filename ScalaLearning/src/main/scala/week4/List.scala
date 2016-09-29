package week4

/**
  * Created by osocron on 4/02/16.
  */
trait List[+T] {
  def isEmpty: Boolean
  def head: T
  def tail: List[T]
  def prepend [U >: T] (elem: U): List[U] = new Cons(elem,this)
}

class Cons[T](val head: T, val tail: List[T]) extends List[T] {
  override def isEmpty: Boolean = false
}

object Nil extends List[Nothing] {
  override def isEmpty: Boolean = true
  override def tail: Nothing = throw new scala.NoSuchElementException("Nil.tail")
  override def head: Nothing = throw new scala.NoSuchElementException("Nil.head")
}

object test {
  val x: List[String] = Nil
}



