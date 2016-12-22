package fpdesign.week3

import scala.util.DynamicVariable

/**
  * Created by osocron on 21/12/16.
  */
class Signal[T](expr: => T) {
  import Signal._

  private var myExpr: () => T = _
  private var myValue: T = _
  private var observers: Set[Signal[_]] = Set()
  update(expr)

  protected def update(expr: T): Unit = {
    myExpr = () => expr
    computeValue()
  }

  protected def computeValue(): Unit = {
    val newValue = caller.withValue(this)(myExpr())
    if (myValue != newValue) {
      myValue = newValue
      val obs = observers
      observers = Set()
      obs.foreach(_.computeValue())
    }
  }

  def apply(): T = {
    observers += caller.value
    assert(!caller.value.observers.contains(this), "cyclic signal definition")
    myValue
  }

}

object NoSignal extends Signal[Nothing](???) {
  override def computeValue(): Unit = ()
}

object Signal {
  private val caller = new DynamicVariable[Signal[_]](NoSignal)
  def apply[T](expr: => T): Signal[T] = new Signal(expr)
}
