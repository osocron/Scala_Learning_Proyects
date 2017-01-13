package algebras

/**
  * Created by osocron on 10/01/17.
  */
sealed trait DayOfWeek {

  val value: Int

  override def toString = value match {
    case 1 => "Monday"
    case 2 => "Tuesday"
    case 3 => "Wednesday"
    case 4 => "Thursday"
    case 5 => "Friday"
    case 6 => "Saturday"
    case 7 => "Sunday"
  }

}

object DayOfWeek {

  private def unsafeDayOfWeek(d: Int) = new DayOfWeek { override val value: Int = d }

  private val isValid: Int => Boolean = { i => i >= 1 && i <= 7 }

  def dayOfWeek(d: Int): Option[DayOfWeek] = if (isValid(d)) Some(unsafeDayOfWeek(d)) else None

}

