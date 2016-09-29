package week4

import java.util

import sun.invoke.empty.Empty

/**
  * Created by osocron on 4/02/16.
  */
object excercises extends App {

  abstract class IntSet {
    def incl(x: Int): IntSet
    def contains(x: Int): Boolean
    def union(other: IntSet): IntSet
  }

  object Empty extends IntSet {

    override def incl(x: Int): IntSet = new NonEmpty(x, Empty, Empty)

    override def contains(x: Int): Boolean = false

    override def toString = "."

    override def union(other: IntSet): IntSet = other
  }

  class NonEmpty(elem: Int, left: IntSet, right: IntSet) extends IntSet {

    override def incl(x: Int): IntSet =
      if (x < elem) new NonEmpty(elem, left incl x, right )
      else if (x > elem) new NonEmpty(elem, left , right incl x)
      else this

    override def contains(x: Int): Boolean =
      if (x < elem) left contains x
      else if (x > elem) right contains x
      else true

    override def toString = "{" + left + elem + right + "}"

    override def union(other: IntSet): IntSet = {
      ((left union right) union other) incl elem
    }

  }

  def nth[T](n: Int, list: List[T]): T = n match{
    case n: Int if list.isEmpty => throw new IndexOutOfBoundsException
    case 0 => list.head
    case _ => nth(n - 1, list.tail)
  }

  //val list = new Cons(1, new Cons(2, new Cons(3, new Nil)))

   //println(nth(2, list))

}
