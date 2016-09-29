package week3

/**
  * Created by osocron on 2/2/16.
  */
object sets extends App {

  type Set = Int => Boolean

  def contains(s: Set, elem: Int): Boolean = s(elem)

  def singletonSet(elem: Int): Set = x => x == elem

  def union(s: Set, t: Set): Set = x => contains(s,x) || contains(t,x)

  def intersect(s: Set, t: Set): Set = x => contains(s,x) && contains(t,x)

  def diff(s: Set, t: Set): Set = x => contains(s,x) && !contains(t,x)

  def filter(s: Set, p: Int => Boolean): Set = x => contains(s,x) && p(x)

  def forall(s: Set, p: Int => Boolean): Boolean = {
    def iter(a: Int): Boolean = {
      if (a == 1000) s(a) && p(a)
      else if (contains(s,a)) p(a)
      else iter(a+1)
    }
    iter(-1000)
  }

  def exists(s: Set, p: Int => Boolean): Boolean =  {
    def iter(a: Int): Boolean = {
      if (a == 1000) false
      else if (contains(s, a) && contains(filter(s, p), a)) true
      else iter(a+1)
    }
    iter(-1000)
  }

  def map(s: Set, f: Int => Int): Set = { elem:Int => exists(s, {elem2:Int => f(elem2) == elem}) }

}
