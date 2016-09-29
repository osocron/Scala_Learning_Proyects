package fpinscala.datastructures

/**
  * Exercise 25 from the book Functional Programming in Scala
  */
sealed trait Tree[+A]
case class Leaf[A](value: A) extends Tree[A]
case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]

object Tree {

  /**
    * Returns the size of the tree.
    *
    * @param t  The tree
    * @tparam A The type of the tree
    * @return   The number of nodes in the tree
    */
  def size[A](t: Tree[A]): Int = {
    def loop(t: Tree[A], acc: Int): Int = t match {
      case Leaf(v) => acc + 1
      case Branch(l, r) => 1 + loop(l, acc) + loop(r, acc)
    }
    loop(t, 0)
  }

  /**
    * Finds the maximum integer value in the tree
    *
    * @param t The tree
    * @return  The maximum found integer in the tree
    */
  def maximum(t: Tree[Int]): Int = {
    def loop(t: Tree[Int], acc: Int): Int = t match {
      case Leaf(v) => if (v > acc) v else acc
      case Branch(l, r) => loop(l, acc); loop(r, acc)
    }
    loop(t, 0)
  }

  /**
    * Applies the given function on each of the values on the tree.
    *
    * @param t  The tree.
    * @param f  The function to be applied.
    * @tparam A The type of the Tree.
    */
  def foreach[A](t: Tree[A])(f: A => Unit): Unit = t match {
    case Leaf(v) => f(v)
    case Branch(l, r) => foreach(l)(f); foreach(r)(f)
  }

  def map[A, B](t: Tree[A])(f: A => B): Tree[B] = t match {
    case Leaf(v) => Leaf(f(v))
    case Branch(l, r) => Branch(map(l)(f), map(r)(f))
  }

  /**
    * Returns the maximum level of depth on a given tree.
    *
    * @param t   The tree.
    * @tparam A  The type of the tree.
    * @return    The maximum level of depth.
    */
  def depth[A](t: Tree[A]): Int = {

    /**
      * Recursive loop that finds the maximum level of depth on a tree.
      *
      * @param t  The tree.
      * @param d  The depth level on each recursive call.
      * @param m  The maximum of the depth levels found.
      * @return   m, the deepest pit.
      */
    def loop(t: Tree[A], d: Int, m: Int): Int = t match {
      case Leaf(v) => if (d > m) d else m
      case Branch(l, r) => loop(l, d + 1, m); loop(l, d + 1, m)
    }

    loop(t, 0, 0)

  }

  def fold[A, B](t: Tree[A], z: B)(f: (A, B) => B): B = t match {
    case Leaf(v) => f(v, z)
    case Branch(l, r) => fold(l,fold(r, z)(f))(f)
  }

}

object test2 extends App {

  import Tree._

  val t = Branch(
    Branch(
      Leaf(1),
      Leaf(2)
    ),
    Branch(
      Leaf(3),
      Branch(
        Leaf(4),
        Leaf(5)
      )
    )
  )

  val r1 = size(t)
  val r2 = maximum(t)
  val r3 = depth(t)
  val r4 = map(t)(_.toString)
  val r5 = fold(t, 0)((x, y) => x + y)

  //foreach(r4)(println)

  println(r3)

}
