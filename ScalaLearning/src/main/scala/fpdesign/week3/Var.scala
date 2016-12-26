package fpdesign.week3

/**
  * Created by osocron on 21/12/16.
  */
class Var[T](expr: => T) extends Signal[T](expr)

object Var {
  def apply[T](expr: => T): Var[T] = new Var(expr)
}