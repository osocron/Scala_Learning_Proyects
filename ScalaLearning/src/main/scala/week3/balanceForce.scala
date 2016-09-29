package week3

/**
  * Created by osocron on 2/1/16.
  */
object balanceForce extends App{

  def balanceTheForce(string: String): Boolean = {

    val chars = string.toList

    def findOpenElement(chars: List[Char], matches: Int): Boolean = chars match {
      case chars: List[Char] if chars.isEmpty => returnValue(matches)
      case chars: List[Char] if chars.head == '(' => findCloseElement(chars.tail, matches + 1)
      case chars: List[Char] if chars.head == ')' => findOpenElement(chars.tail, matches - 1)
      case _  => findOpenElement(chars.tail, matches)
    }

    def findCloseElement(chars: List[Char], matches: Int): Boolean = chars match {
      case chars: List[Char] if chars.isEmpty => false
      case chars: List[Char] if chars.head == ')' => findOpenElement(chars.tail, matches - 1)
      case chars: List[Char] if chars.head == '(' => findCloseElement(chars.tail, matches + 1)
      case _ => findCloseElement(chars.tail, matches)
    }

    def returnValue(n: Int): Boolean = if (n == 0) true else false

    findOpenElement(chars, 0)

  }

  println(balanceTheForce("hola ()((()))"))

}
