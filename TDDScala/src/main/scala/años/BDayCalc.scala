package años

import java.time.LocalDateTime
import scala.io.StdIn

/**
  * Created by osocron on 4/03/16.
  */
object BDayCalc {
  def calculateYearOfBirth(age: Int) = LocalDateTime.now().getYear - age
  def askAge() = {
    println("Please tell me your age!")
    val age = StdIn.readLine().toInt
    println("You were born on the year: " + calculateYearOfBirth(age))
  }
}
