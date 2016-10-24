package automata

import scala.io.StdIn.readLine

/**
  * Este ejemplo de analizador lexico se define con matrices para optimizar
  * la logica de analisis
  */
object Ejemplo2 extends App {

  val num: Set[Char] = ('0' to '9').toSet

  val alpha: Set[Char] = (('a' to 'z') ++ ('A' to 'Z')).toSet ++ num

  val matrizTransicion = List(
    List(1, 2, 1, 3, 0, 4),
    List(1, 1, 1, 0, 0, 0),
    List(0, 2, 0, 0, 0, 0),
    List(0, 0, 0, 0, 0, 0),
    List(0, 0, 0, 0, 0, 4)
  )

  val matrizSalida = List(
    List(0, 0, 0, 0, 0, 0),
    List(0, 0, 0, 1, 1, 1),
    List(2, 0, 2, 2, 2, 2),
    List(3, 3, 3, 3, 3, 3),
    List(4, 4, 4, 4, 4, 0)
  )

  println("Entrada: ")

  val entrada: List[Char] = readLine().toList

  def filtro(c: Char): Int = {
    if (num.contains(c)) 1
    else if (alpha.contains(c)) 0
    else if (c == '_') 2
    else if (c == '+') 3
    else if (c == ' ' || c == '\t') 4
    else 5
  }

  def lexico(lc: List[Char]): (List[Char], Int) = {

    def loop(salida: Int, codigo: Int, estado: Int, lc: List[Char]): (List[Char], Int) = lc match {
      case Nil => (lc, matrizSalida(estado)(filtro('\n')))
      case h :: t =>
        if (salida != 0) (lc, salida)
        else {
          val nCodigo = filtro(h)
          loop(matrizSalida(estado)(nCodigo), nCodigo, matrizTransicion(estado)(nCodigo), t)
        }
    }

    loop(0, 0, 0, lc)

  }

  def eval(lc: List[Char]): List[String] = {

    def loop(lc: List[Char], ls: List[String]): List[String] = lc match {
      case Nil => ls
      case h :: t =>
        val (r, i) = lexico(lc)
        loop(r, ls :+ token(i))
    }

    def token(i: Int): String = i match {
      case 1 => "Identificador"
      case 2 => "Entero"
      case 3 => "Suma"
      case 4 => "No definido"
      case _ => "No reconocido"
    }

    loop(lc, List())

  }

  val res = eval(entrada)

  res.foreach(println)

}
