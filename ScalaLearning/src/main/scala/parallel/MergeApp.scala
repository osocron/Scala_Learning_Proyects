package parallel

/**
  * Created by osocron on 7/6/16.
  */
object MergeApp extends App {

  sealed abstract class Tree[A]
  case class Leaf[A](value: A) extends Tree[A]
  case class Node[A](left: Tree[A], right: Tree[A]) extends Tree[A]

  def reduce[A](t: Tree[A], f: (A,A) => A): A = t match {
    case Leaf(v) => v
    case Node(l, r) => f(reduce[A](l, f), reduce[A](r, f))
  }

  def map[A, B](t: Tree[A], f: (A) => B): Tree[B] = t match {
    case Leaf(v) => Leaf(f(v))
    case Node(l, r) => map[A, B](l, f); map[A, B](r, f)
  }

  def foreach[A](t: Tree[A], f: (A) => Unit): Unit = t match {
    case Leaf(v) => f(v)
    case Node(l, r) => foreach(l, f); foreach(r, f)
  }

  val tree: Tree[Int] = Node(Node(Leaf(2), Leaf(4)), Node(Leaf(6), Leaf(7)))

  println(reduce(tree, (l: Int, r: Int) => l + r))
  map(tree, (x: Int) => println(x + 10))
  foreach(tree, (x: Int) => println(x))

}
