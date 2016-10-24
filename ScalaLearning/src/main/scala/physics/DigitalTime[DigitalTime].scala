package physics

/**
  * Created by osocron on 7/17/16.
  */

trait DigitalTime {
  type Second = Int
  type Minute = Int
  type Hour = Int
  def isEmpty: Boolean
  def seconds: Second
  def minutes: Minute
  def hours: Hour
  def addSec(seconds: Second): DigitalTime
  def addMin(minutes: Minute): DigitalTime
  def addHours(hours: Hour): DigitalTime
  def subSec(seconds: Second): DigitalTime
  def subMin(minutes: Minute): DigitalTime
  def subHours(hours: Hour): DigitalTime
  def totalSec: Second
  def +(t: DigitalTime): DigitalTime
  def -(t: DigitalTime): DigitalTime
  def normalize: DigitalTime = {
    type IntFun = Int => Int
    def normLoop(t: DigitalTime, unit: Int, minusFun: List[IntFun], overFun: List[IntFun]): DigitalTime = {
      if (unit < 60 && unit >= 0) t
      else if (unit < 0)
        normLoop(SomeDigitalTime(minusFun.head(t.hours), minusFun(1)(t.minutes), minusFun(2)(t.seconds)), unit + 60, minusFun, overFun)
      else
        normLoop(SomeDigitalTime(overFun.head(t.hours), overFun(1)(t.minutes), overFun(2)(t.seconds)), unit - 60, minusFun, overFun)
    }
    val normSec = normLoop(this, seconds, List(x => x, x => x - 1, x => x + 60), List(x => x, x => x + 1, x => x - 60))
    normLoop(normSec, normSec.minutes, List(x => x - 1, x => x + 60, x => x), List(x => x + 1, x => x - 60, x => x))
  }
}

case object NoDigitalTime extends DigitalTime {
  override def seconds: Second = 0
  override def minutes: Minute = 0
  override def hours: Hour = 0
  override def isEmpty: Boolean = true
  override def addSec(seconds: Second): DigitalTime = SomeDigitalTime(0, 0, seconds).normalize
  override def addMin(minutes: Minute): DigitalTime = SomeDigitalTime(0, minutes, 0).normalize
  override def addHours(hours: Hour): DigitalTime = SomeDigitalTime(hours, 0, 0).normalize
  override def subSec(seconds: Second): DigitalTime = throw new IllegalArgumentException
  override def subMin(minutes: Minute): DigitalTime = throw new IllegalArgumentException
  override def subHours(hours: Hour): DigitalTime = throw new IllegalArgumentException
  override def totalSec: Second = 0
  override def +(t: DigitalTime): DigitalTime = t
  override def -(t: DigitalTime): DigitalTime = throw new IllegalArgumentException
}

case class SomeDigitalTime(h: Int, m: Int, s: Int) extends DigitalTime {
  require(h <= 24)
  override def seconds: Second = s
  override def minutes: Minute = m
  override def hours: Hour = h
  override def isEmpty: Boolean = false
  override def addSec(seconds: Second): DigitalTime = SomeDigitalTime(h, m, s + seconds).normalize
  override def addMin(minutes: Minute): DigitalTime = SomeDigitalTime(h, m + minutes, s).normalize
  override def addHours(hours: Hour): DigitalTime = SomeDigitalTime(h + hours, m, s).normalize
  override def subSec(seconds: Second): DigitalTime = {
    require(seconds <= totalSec)
    SomeDigitalTime(h, m, s - seconds).normalize
  }
  override def subMin(minutes: Minute): DigitalTime = {
    require(SomeDigitalTime(0, minutes, 0).totalSec <= totalSec)
    SomeDigitalTime(h, m - minutes, s).normalize
  }
  override def subHours(hours: Hour): DigitalTime = {
    require(SomeDigitalTime(hours, 0, 0).totalSec <= totalSec)
    SomeDigitalTime(h - hours, m, s).normalize
  }
  override def totalSec: Second = s + (m * 60) + (h * 60 * 60)
  override def +(t: DigitalTime): DigitalTime = SomeDigitalTime(h + t.hours, m + t.minutes, s + t.seconds).normalize
  override def -(t: DigitalTime): DigitalTime = SomeDigitalTime(h - t.hours, m - t.minutes, s - t.seconds).normalize
}
