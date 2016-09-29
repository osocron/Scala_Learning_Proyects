package scalaz

import scalaz._
import syntax.std.list._

/**
  * Created by osocron on 7/11/16.
  */
object Tutorial extends App {

  val fun = List(1,2,3,4,6,7,11,12,13) groupWhen ((x, y) => y - x == 1)

  println(fun)

}
