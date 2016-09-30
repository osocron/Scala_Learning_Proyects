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

  /**
    * Good ol' map that applies a function to each element of the tree.
    *
    * @param t  The tree
    * @param f  The function
    * @tparam A The type parameter of the original tree.
    * @tparam B The type parameter for the resulting tree.
    * @return   A tree of type B after applying a function to each
    *           element of the tree.
    */
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
      * @return   The deepest pit.
      */
    def loop(t: Tree[A], d: Int): Int = t match {
      case Leaf(v) => d
      case Branch(l, r) => {
        val left  = loop(l, d + 1)
        val right = loop(r, d + 1)
        if (left > right) left else right
      }
    }

    loop(t, 0)

  }

  /**
    * Folds over the tree creating mighty deforestation.
    *
    * @param t  The tree to be destroyed.
    * @param z  The accumulator.
    * @param f  The function of destruction.
    * @tparam A The type parameter of the tree.
    * @tparam B The type parameter of the resulting destruction.
    * @return   A barren land with only one value left.
    */
  def fold[A, B](t: Tree[A], z: B)(f: (A, B) => B): B = t match {
    case Leaf(v) => f(v, z)
    case Branch(l, r) => fold(l,fold(r, z)(f))(f)
  }

}
