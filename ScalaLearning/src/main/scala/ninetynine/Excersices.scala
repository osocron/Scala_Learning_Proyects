package ninetynine

/**
  * Created by osocron on 7/30/16.
  */
class Excersices {

  //23689

  val lista = 1 :: 2 :: 3 :: 4 :: 6 :: 8 :: Nil
  val head = lista.head
  val tail = lista.tail
  val longitud = lista.length

  def regresaUltimo(lista: List[Int]): Int = lista match {
    case Nil => throw new IllegalArgumentException
    case n :: Nil => n
    case h :: t => regresaUltimo(t)
  }

}
