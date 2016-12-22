package quickcheck

import common._

import org.scalacheck._
import Arbitrary._
import Gen._
import Prop._

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  lazy val genHeap: Gen[H] = for {
    i <- arbitrary[Int]
    h <- oneOf[H](genHeap, empty)
  } yield insert(i, h)

  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)

  property("gen1") = forAll { (h: H) =>
    val m = if (isEmpty(h)) 0 else findMin(h)
    findMin(insert(m, h)) == m
  }

  property("The smallest should remain the same") = forAll { (h: H, a1: A, a2: A) =>
    val min = if (a1 <= a2) a1 else a2
    findMin(insert(a2, insert(a1, h))) == min
  }

  property("Insert to empty and then deleting") = forAll { (a: A) =>
    isEmpty(deleteMin(insert(a, empty)))
  }

  property("Sorted heap") = forAll { (h: H) =>
    isSorted(getElements(h))
  }

  property("Minimum of meld") = forAll { (h1: H, h2: H) =>
    val min = if (findMin(h1) <= findMin(h2)) findMin(h1) else findMin(h2)
    findMin(meld(h1, h2)) == min
  }

  def isSorted(la: List[A]): Boolean = la.sorted == la

  def getElements(h: H): List[A] = {
    if (isEmpty(h)) Nil
    else findMin(h) :: getElements(deleteMin(h))
  }

}
