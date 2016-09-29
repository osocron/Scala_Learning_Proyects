package alrevez

import scala.io.StdIn

/**
  * Created by osocron on 4/03/16.
  */
object BackwardString {

  def reverse(string: String) = string.reverse

  def askForString() = {
    println("Please sir, give me a string")
    val string = StdIn.readLine()
    println("Your reversed string is: "+reverse(string))
  }

}
