package chalange

import java.nio.charset.CodingErrorAction

import scala.collection.parallel.mutable.ParArray
import scala.io.Codec
import scala.util.Try

object AppDataChalange extends App {

  implicit val codec = Codec("ISO-8859-1")
  codec.onMalformedInput(CodingErrorAction.REPLACE)
  codec.onUnmappableCharacter(CodingErrorAction.REPLACE)

  //Lee el archivo csv y lo almacena en un arreglo de dos dimensiones
  def readCSV(): ParArray[Array[String]] = {
    val stream = getClass.getResourceAsStream("/prueba.csv")
    scala.io.Source.fromInputStream(stream)
      .getLines()
      .map(_.split(",").map(_.trim))
      .toArray.par
      .drop(1)
  }

  //Agrupar los datos por dia, sentido y hora
  val agrupados = readCSV().groupBy(arr => (arr(3), arr(4), arr(5)))
  //Transformar cada grupo para que se muestre el flujo de autos
  val conFlujo = agrupados.map{ case (llave, datos) => (llave, datos.length)}
  //Ordenar los grupos de mayor flujo a menor flujo
  val ordenados = conFlujo.toList.sortWith((t1, t2) => t1._2 > t2._2)
  //Obtener el primer elemento del arreglo e imprimirlo en pantalla
  val ((dia, sentido, hora), flujo) = ordenados.head
  println(s"Dia: $dia, Sentido: $sentido, Hora: $hora, Flujo: $flujo")

  //Transformar los grupos para mostrar la suma del tonelaje de cada grupo
  val conTonelaje = agrupados.map {
    case (llave, datos) =>
      (llave, datos.map(inner => {
        (for {
          maybeTon <- Try(inner(25))
          maybeNum <- Try(BigDecimal(maybeTon))
        } yield maybeNum).getOrElse(
          (for {
            str <- Try(inner(26))
            num <- Try(BigDecimal(str))
          } yield num).getOrElse(BigDecimal(0.0)))
      }).sum)
  }
  //Ordenar los grupos de mayor tonelaje a menor tonelaje
  val tonOrdenado = conTonelaje.toList.sortWith((t1, t2) => t1._2 > t2._2)
  //Obtener el primer elemento del arreglo e imprimirlo en pantalla
  val ((d, s, h), ton) = tonOrdenado.head
  println(s"Dia: $d, Sentido: $s, Hora: $h, Tonelaje: $ton")

}
