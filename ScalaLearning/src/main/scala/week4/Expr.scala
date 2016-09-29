package week4

/**
  * Created by osocron on 5/02/16.
  */
trait Expr {
  def isNumber: Boolean
  def isSum: Boolean
  def numValue: Int
  def lefOp: Expr
  def rightOp: Expr
}

class Number(n: Int) extends Expr{
  override def isNumber: Boolean = true
  override def isSum: Boolean = false
  override def numValue: Int = n
  override def rightOp: Expr = throw new Error("Number.rightOp")
  override def lefOp: Expr = throw new Error("Number.leftOp")
}

class Sum (e1: Expr, e2: Expr) extends Expr {
  override def isNumber: Boolean = false
  override def isSum: Boolean = true
  override def numValue: Int = throw new Error("Sum.numValue")
  override def rightOp: Expr = e1
  override def lefOp: Expr = e2
}

object execute extends App {
  def eval(e: Expr): Int = {
    if (e.isNumber) e.numValue
    else if (e.isSum) eval(e.lefOp) + eval(e.rightOp)
    else throw new Error("Unknown expression" + e)
  }
  println(eval(new Sum(new Number(1),new Number(3))))
}
