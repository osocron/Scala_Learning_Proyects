package chalange

import java.io.File
import java.nio.charset.CodingErrorAction

import com.github.tototoshi.csv.CSVReader
import org.saddle.Frame
import org.saddle.io.{CsvFile, CsvParser}

//import org.apache.spark.{SparkConf, SparkContext}

import breeze.linalg._

import scala.collection.immutable.ListMap
import scala.collection.parallel.mutable.ParArray
import scala.io.Codec
import scala.util.Try

/**
  * Created by osocron on 9/15/16.
  */
object Application extends App {

  //val conf = new SparkConf().setAppName("Data_Carretera").setMaster("local")
  //val sc = new SparkContext(conf)

  implicit val codec = Codec("ISO-8859-1")
  codec.onMalformedInput(CodingErrorAction.REPLACE)
  codec.onUnmappableCharacter(CodingErrorAction.REPLACE)

  //val input = sc.textFile(getClass.getResource("/data.csv").toString)
  //val words = input.flatMap(line => line.split(" "))
  //words.foreach(println)

  def readCSV(): ParArray[Array[String]] = {
    val stream = getClass.getResourceAsStream("/data.csv")
    scala.io.Source.fromInputStream(stream)
      .getLines()
      .map(_.split(",").map(_.trim))
      .toArray.par
      .drop(1)
  }

  def saddleRead(): Frame[Int, String, String] = {
    val file = CsvFile(getClass.getResource("/data.csv").getFile, encoding = "ISO-8859-1")
    CsvParser.parse(List(3, 4, 5, 25))(file).withColIndex(0)
  }

  def totoshiReader(): List[List[String]] = {
    val reader = CSVReader.open(getClass.getResource("/data.csv").getFile, "ISO-8859-1")
    reader.all().drop(1)
  }

  def breezeReader() = {
    csvread(new File(getClass.getResource("/data.csv").getFile))
  } //Only for numeric data :(

  def customReader() = {
    val file = utilCopy.CsvFile(getClass.getResource("/data.csv").getFile, encoding = "ISO-8859-1")
    utilCopy.CsvParser.parse(List(3, 4, 5, 25))(file).transpose.drop(1)
  }

  //Agrupar los datos por dia, sentido y hora
  val agrupados = customReader().groupBy(arr => (arr(0), arr(1), arr(2)))
  //Transformar los datos para mostrar el flujo de autos por cada grupo
  val porLongitud = agrupados.map(t => (t._1, t._2.length))
  //Ordenar los datos de mayor a menor flujo de autos
  val ordPorFlujo = ListMap(porLongitud.toSeq.seq.sortWith(_._2 > _._2): _*)
  //Obtener el resultado con mayor flujo
  val ((dia, sentido, hora), flujo) = ordPorFlujo.head
  println(s"Dia: $dia, Hora: $hora, Sentido: $sentido, Flujo: $flujo autos por hora")

  //Transformar los datos para mostrar la suma del tonelaje de cada grupo
  val porTonelaje = agrupados.map { case (tuple, array) =>
    val toneladas = array.map(array => array(3))
    val result = toneladas.foldLeft(BigDecimal(0.0))((acc, s) =>
      acc + Try(BigDecimal(s)).getOrElse(BigDecimal(0.0))
    )
    (tuple, result)
  }
  //Ordenar de mayor a menor tonelaje
  val ordenadoPorTonelaje = ListMap(porTonelaje.toSeq.seq.sortWith(_._2 > _._2): _*)
  //Obtener el resultado con mayor tonelaje
  val ((dia2, sentido2, hora2), tonelaje) = ordenadoPorTonelaje.head
  println(s"Dia: $dia2, Hora: $hora2, Sentido: $sentido2, Tonelaje: $tonelaje toneladas")

}
