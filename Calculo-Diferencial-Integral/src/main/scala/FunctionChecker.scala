/**
  * Created by osocron on 4/11/16.
  */
object FunctionChecker {

  def isFuntcion(xs: List[(Int,Int)]):Boolean =
    if (findDuplicates(xs).nonEmpty) allEqual(findDuplicates(xs).map(_._2)) else true


  def findDuplicates(xs: List[(Int,Int)]): List[(Int,Int)] = xs match {
    case Nil => Nil
    case h :: t :: Nil => if (t._1 == h._1) List(h,t) else List(t)
    case h :: t => if (t.map(_._1).contains(h._1)) h :: findDuplicates(t) else findDuplicates(t)
  }

  def allEqual(ints: List[Int]):Boolean = ints.forall((elem) => elem == ints.head)

}

class FunctionSolver {


}

case class AlgebraicExpression(c: Int, v: String, o: String) {
  val constant = c
  val variable = v
  val operand = o
}
