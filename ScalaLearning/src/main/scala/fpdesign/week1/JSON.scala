package fpdesign.week1

/**
  * Created by osocron on 10/16/16.
  */
abstract class JSON
case class JSeq(elems: List[JSON])            extends JSON
case class JObj(bindings: Map[String, JSON])  extends JSON
case class JNum(num: Double)                  extends JSON
case class JString(str: String)               extends JSON
case class JBool(b: Boolean)                  extends JSON
case object JNull                             extends JSON
trait JSONParser {
  def show(jSON: JSON): String = jSON match {
    case JSeq(elems) => "[" + (elems map show mkString ", ") + "]"
    case JObj(bindings) =>
      val assoc = bindings map {
        case (key, value) => "\"" + key + "\": " + show(value)
      }
      "{" + (assoc mkString ", ") + "}"
    case JNum(num) => num.toString
    case JString(str) => "\"" + str + "\""
    case JBool(b) => b.toString
    case JNull => "null"
  }
}