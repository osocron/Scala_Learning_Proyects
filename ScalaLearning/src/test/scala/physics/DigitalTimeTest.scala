package physics

import org.scalatest.FunSuite

/**
  * Created by osocron on 7/18/16.
  */
class DigitalTimeTest extends FunSuite {

  test("02:30:20 + 00:30:00 must equal to 03:00:20") {
    val result = SomeDigitalTime(2, 30, 20) + SomeDigitalTime(0, 30, 0)
    assert(result.hours == 3 && result.minutes == 0 && result.seconds == 20)
  }

  test("02:00:00 total seconds must be 7200") {
    assert(SomeDigitalTime(2, 0, 0).totalSec == 7200)
  }

  test("00:20:00 total seconds must be 1200") {
    assert(SomeDigitalTime(0, 20, 0).totalSec == 1200)
  }

  test("02:25:40 - 1 hour must be 01:25:40") {
    val result = SomeDigitalTime(2, 25, 40).subHours(1)
    assert(result.hours == 1 && result.minutes == 25 && result.seconds == 40)
  }

  test("02:25:40 - 02:25:30 hour must be 00:00:10") {
    val result = SomeDigitalTime(2, 25, 40) - SomeDigitalTime(2, 25, 30)
    assert(result.hours == 0 && result.minutes == 0 && result.seconds == 10)
  }

  test("02:25:40 - 35 minutes must be 01:50:40") {
    val result = SomeDigitalTime(2, 25, 40).subMin(35)
    assert(result.hours == 1 && result.minutes == 50 && result.seconds == 40)
  }

  test("02:00:00 - 300 seconds should be 01:55:00") {
    val result = SomeDigitalTime(2,0,0).subSec(300)
    assert(result.hours == 1 && result.minutes == 55 && result.seconds == 0)
  }

  test("23:00:00 + 2 hour should throw an exception") {
    intercept[IllegalArgumentException] {
      SomeDigitalTime(23,0,0).addHours(2)
    }
  }

}
