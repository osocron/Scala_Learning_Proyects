package ejercicios

/**
  * Created by osocron on 7/18/16.
  */
object Ejercicios extends App {

  val miLista = List(1,2,3,4,5)

  def addList(a: Int, xs: List[Int]) = xs :+ a

  val nueva = addList(10, miLista)

  println(nueva)

  println(miLista)

  val miLista2 = addList(10, miLista)

  def addRango(top: Int, list: List[Int]): List[Int] = {
    def loopInt(init: Int, xs: List[Int]): List[Int] = {
      if (init > top) xs
      else loopInt(init + 1, xs :+ init)
    }
    loopInt(1, list)
  }

  def addRango2(top: Int, xs: List[Int]) = xs ++ (1 to top).toList

  println(addRango(10, miLista))

  println(addRango2(10, List(11,12,13)))

}
