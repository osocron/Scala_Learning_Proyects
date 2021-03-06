package scalaDesign.reactive.frp

/**
  * Created by osocron on 7/4/16.
  */
class Var[T] (expr: => T) extends Signal[T](expr) {
  override def update(expr: => T): Unit = super.update(expr)
}

object Var {
  def apply[T](expr: => T) = new Var(expr)
}
