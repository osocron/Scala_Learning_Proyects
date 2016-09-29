package perceptron

import scala.io.StdIn

/**
  * Created by osocron on 9/03/16.
  * Implementación de un perceptrón en Scala
  */
object app {
  
  def main(args: Array[String]) {

    println("Escribe el peso uno")
    val w0 = StdIn.readLine().toDouble
    println("Escribe el peso dos")
    val w1 = StdIn.readLine().toDouble
    println("Escribe el peso tres")
    val w2 = StdIn.readLine().toDouble
    println("Escribe el factor de aprendizaje")
    val fApr = StdIn.readLine().toDouble
    val vectores = List(List(1.0,0.0,0.0),List(1.0,0.0,1.0),List(1.0,1.0,0.0),List(1.0,1.0,1.0))

    println("Pesos correctos: \n")
    perceptron.generarPesos(List(w0,w1,w2),vectores,List(0,0,0,1),fApr).foreach((peso) => println(peso+"\n"))
  }

}

object perceptron {

  /*Para poder generar los pesos correctos se necesitan modificar los pesos
  * originales hasta que todos los errores sean igual a 0. Esta es una función
  * recursiva que sólo terminara hasta que no existan más cambios entre los pesos
  * y los pesosCorregidos ya que la ausencia de cambios en los pesos indica que se ha
  * logrado que todos los errores sean igual a 0*/
  def generarPesos(pesos: List[Double], entradas: List[List[Double]], salidas: List[Double], factorApr: Double): List[Double] = {
    val pesosCorregidos: List[Double] = train(pesos: List[Double],entradas: List[List[Double]],salidas: List[Double],factorApr: Double)
    if(pesos == pesosCorregidos) pesos else generarPesos(pesosCorregidos,entradas,salidas,factorApr)
  }

  /*Esta función se encarga de iterar sobre cada uno de los elementos de entrada,
  * los cuales son a su vez vectores con valores de X0 hasta X2. En cada iteración
  * se pregunta si el error es igual a 0, si no lo es se corrigen los pesos*/
  def train(pesos: List[Double], entradas: List[List[Double]], salidas: List[Double], factorApr: Double): List[Double] = {
    var pesosCorregidos: List[Double] = pesos
    for(elemEntrada <- entradas){
      val error = calcularError(salidas(entradas.indexOf(elemEntrada)), y0(neti(elemEntrada,pesosCorregidos)))
      if(!error.equals(0.0)) pesosCorregidos = modificarPesos(pesos,factorApr,error,elemEntrada)
    }
    pesosCorregidos
  }

  /*Neti se encarga de obtener el valor de (x0*w0)+(x1*w1)+(x2*w2) x siendo
  * las entradas y w los pesos, neti se utiliza para saber si y0 es igual a 1 o igual a 0,
  * y0 es a su vez necesaria para calcular el error*/
  def neti(entradas: List[Double], pesos: List[Double]): Double = pesos match{
    case Nil => 0
    case h::t => (h*entradas.head) + neti(entradas.tail, pesos.tail)
  }

  /*y0 es usada a partir del resultado de neti,
  * sólo regresa 1 en caso de que el resultado de neti
  * sea igual o mayor a 0 y regresa 0 si es menor a 0*/
  def y0(input: Double) = if (input >= 0) 1 else 0

  /*Se calcula el error a partir de y0 y la salida esperada donde yd es la salida esperada*/
  def calcularError(yd: Double, y0: Double): Double = yd - y0

  /*Se itera sobre cada peso para calcular la modificación que le corresponde. La modificación se calcula en la función
  * modificarPeso*/
  def modificarPesos(pesos: List[Double], factApr: Double, error: Double, entradas: List[Double]): List[Double] = pesos match{
    case Nil => Nil
    case h::t => List(modificarPeso(pesos.head,factApr,error,entradas.head)) ++ modificarPesos(pesos.tail,factApr,error,entradas.tail)
  }

  /*Se encarga de calcular la modificación al peso dado con la formula peso = Wi + (factorDeAprendizaje * error * Xi)*/
  def modificarPeso(peso: Double, factApr: Double, error: Double, entrada: Double) = peso + (factApr*error*entrada)

}

