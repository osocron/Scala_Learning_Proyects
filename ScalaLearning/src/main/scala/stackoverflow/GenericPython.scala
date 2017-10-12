package stackoverflow

import org.joda.time.DateTime

object GenericPython extends App {

  def genericValidator(dict: Map[String,(Any,Any=>Boolean,String)]) = {
    dict.collect { case (key, (value, predicate, message)) if !predicate(value) => (key, (value,message)) }
  }

  val inputDict: Map[String,(Any, Any=>Boolean, String)] = Map(
    "SomeInt" -> (11, { case x: Int => x < 10 }, "Input should be less than ten"),
    "SomeString" -> ("Hello", { case x: String =>  x.length > 10 }, "Input should be longer than ten characters"),
    "SomeDateTime" -> (DateTime.now().minusDays(1), { case x: DateTime => x.isAfter(DateTime.now()) }, "DateTime cannot be before now")
  )

  val result = genericValidator(inputDict)

  for ((k2, (v2, m2)) <- result)
    println(m2)

}
