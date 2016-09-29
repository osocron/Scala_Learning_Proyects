package chapter2

import java.util.{Observer, Observable}

/**
  * Created by osocron on 16/03/16.
  * The observer pattern defines a one-to-many dependency between objects so that when changes
  * state, all its dependants are notified and updated automatically.
  * In this example, the java.util.Observer and Observable classes are used to implement the
  * observer design pattern.
  * The OO principle learned in this lesson is:
  *   -Strive for loosely coupled designs between objects that interact.
  */

class WeatherData extends Observable {
  //State
  private var _temperature:Float = 0.0.toFloat
  private var _humidity = 0.0.toFloat
  private var _pressure = 0.0.toFloat

  //Methods specific to Weather Data
  def measurementsChanged() = {
    setChanged()
    notifyObservers()
  }

  def setMeasurements(temperature: Float, humidity: Float, pressure: Float): Unit = {
    _temperature = temperature
    _humidity = humidity
    _pressure = pressure
    measurementsChanged()
  }

  def temperature = _temperature
  def humidity = _humidity
  def pressure = _pressure

}


trait DisplayableElement {
  def display():Unit
}

class CurrentConditions(observable: Observable) extends Observer with DisplayableElement {

  var _temperature = 0.0.toFloat
  var _humidity = 0.0.toFloat

  observable.addObserver(this)

  override def update(o: Observable, arg: scala.Any): Unit = {
    o match {
      case data: WeatherData =>
        _temperature = data.temperature
        _humidity = data.humidity
        display()
      case _ => sys.error("This is not a weather data class!")
    }
  }

  override def display(): Unit = println("Current conditions: " + _temperature + "F degrees and "
    + _humidity + "% humidity")

}



