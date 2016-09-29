package automata

import scala.io.StdIn.readLine

/**
  * Created by osocron on 8/29/16.
  */
object Ejemplo1 extends App {

  println("Entrada: ")

  val entrada = readLine().toList

  val num: Set[Char] = ('0' to '9').toSet

  val alpha: Set[Char] = (('a' to 'z') ++ ('A' to 'Z')).toSet ++ num + '_'

  /**
    * This function drops the chars that are contained in a given Set and
    * returns the remainder.
    *
    * @param line The list to be evaluated
    * @param s    The Set from which the the chars are going to be compared
    * @return     Thre remainder of the list
    */
  def eval(line: List[Char], s: Set[Char]): List[Char] = line.dropWhile(s.contains)

  /**
    * Evaluates a list of chars and returns a list of accurrences of identifiers,
    * integers, errors or the sum literal.
    *
    * @param line The list of chars to be evaluated
    * @return     A list of occurrences of identifiers, integers, errors or the `+`
    *             literal.
    */
  def evalLine(line: List[Char]): List[String] = {

    def loop(l: List[Char], acc: List[String]): List[String] = l match {
      case Nil => acc
      case h :: t =>
        if        (num.contains(h))     loop(eval(l,   num), acc :+ "ENT")
        else if   (alpha.contains(h))   loop(eval(l, alpha), acc :+ "ID")
        else if   (h == '+')            loop(t, acc :+ "SUM")
        else                            loop(t, acc :+ "ERR")
    }

    loop(line, List())

  }

  val res = evalLine(entrada)

  res.foreach(println)

}
