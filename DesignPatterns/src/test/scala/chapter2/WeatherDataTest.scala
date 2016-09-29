package chapter2

import org.scalatest.FunSuite

/**
  * Created by osocron on 16/03/16.
  */
class WeatherDataTest extends FunSuite {

  test("The Weather Data object should update the displays"){
    val weatherData = new WeatherData
    val currentDisplay = new CurrentConditions(weatherData)
    weatherData.setMeasurements(80,65,30.4f)
    assert(currentDisplay._temperature === 80 && currentDisplay._humidity === 65)
    weatherData.setMeasurements(82,45,33.4f)
    assert(currentDisplay._temperature === 82 && currentDisplay._humidity === 45)
    weatherData.setMeasurements(78,68,40.4f)
    assert(currentDisplay._temperature === 78 && currentDisplay._humidity === 68)
  }

  test("The display should receive a notification from the Weather Data object"){

  }



}
