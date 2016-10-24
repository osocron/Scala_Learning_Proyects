package chapter1

import concurrent.Future
import concurrent.ExecutionContext.Implicits.global

/**
  * Created by osocron on 9/9/16.
  */
object RaceExample extends App {

  var i, j = 0
  (1 to 100000).foreach(_ => Future{i = i + 1})
  (1 to 100000).foreach(_ => j = j + 1)
  Thread.sleep(1000)
  println(s"$i $j")

}
