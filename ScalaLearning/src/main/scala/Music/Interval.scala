package Music

/**
  * Created by osocron on 7/19/16.
  */

trait Line

case object FirstLine extends Line
case object SecondLine extends Line
case object ThirdLine extends Line
case object FourthLine extends Line
case object FifthLine extends Line
case object LedgerLine extends Line

trait Space

case object FirstSpace extends Space
case object SecondSpace extends Space
case object ThirdSpace extends Space
case object FourthSpace extends Space
case object LedgerSpace extends Space

trait Clef

case object TrebleClef extends Clef
case object BassClef extends Clef

trait Measure
trait TimeSignature

trait Staff

case class ModernStaff(lines: List[Line], spaces: List[Space], clef: Clef) extends Staff
case class GrandStaff(lines: List[Line], spaces: List[Space]) extends Staff


trait Interval
trait Quality

case object Perfect extends Quality
case object Major extends Quality
case object Minor extends Quality

case class First(quality: Quality) extends Interval
case class Second(quality: Quality) extends Interval
case class Third(quality: Quality) extends Interval
case class Fourth(quality: Quality) extends Interval
case class Fifth(quality: Quality) extends Interval
case class Sixth(quality: Quality) extends Interval
case class Seventh(quality: Quality) extends Interval
case class Eighth(quality: Quality) extends Interval

trait Step
trait Scale

case object WholeStep extends Step
case object HalfStep extends Step


trait Note
trait Accidental
trait Duration

case object Sharp extends Accidental
case object Flat extends Accidental
case object None extends Accidental

case object WholeD extends Duration
case object HalfD extends Duration
case object QuarterD extends Duration
case object EighthD extends Duration
case object SixteenthD extends Duration

case class C(accidental: Accidental, duration: Duration) extends Note
case class D(accidental: Accidental, duration: Duration) extends Note
case class E(accidental: Accidental, duration: Duration) extends Note
case class F(accidental: Accidental, duration: Duration) extends Note
case class G(accidental: Accidental, duration: Duration) extends Note
case class A(accidental: Accidental, duration: Duration) extends Note
case class B(accidental: Accidental, duration: Duration) extends Note