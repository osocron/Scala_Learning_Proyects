/**
  * Created by osocron on 4/21/16.
  *
  * This is a very simple implementation of the travelling salesman problem.
  * It has zero optimization, zero performance tuning and no method of
  * finding the best solution the fastest way. It's just brute force.
  *
  */

case class Town(rLength: List[Int], name: String)

case class SalesMan(towns: List[Town]) {

  def neighborDistance(t1: Town, t2: Town) = {
    val posTown1 = towns.indexOf(t1)
    val posTown2 = towns.indexOf(t2)
    if (posTown1 < posTown2) t2.rLength(posTown1)
    else t1.rLength(posTown2)
  }

  def calculateDistance(comb: List[Town]): Int = comb match {
    case Nil => 0
    case h :: Nil => neighborDistance(h, towns.head)
    case h :: t => neighborDistance(h,t.head) + calculateDistance(t)
  }

  def generateCombinations(nodes: List[Town]) = {
    nodes.permutations.toList
  }

  def findMinLengthComb(comb: List[List[Town]]) =
    comb(comb.map(calculateDistance(_)).indexOf(comb.map(calculateDistance(_)).min))

  def solve = findMinLengthComb(generateCombinations(towns))

}


object App {
  def main(args: Array[String]) {
    val towns = List(
      Town(List(2,7,7,1,2,8,8), "home"),
      Town(List(2,5,4,3,11,9,4), "Tennessee"),
      Town(List(7,5,7,5,2,6,11), "Chicago"),
      Town(List(7,4,7,4,9,9,6), "Miami"),
      Town(List(1,3,5,4,12,11,8), "Salt Lake"),
      Town(List(2,11,2,9,12,6,1), "Seattle"),
      Town(List(8,9,6,9,11,6,3), "New York"),
      Town(List(8,4,11,6,8,1,3), "Kansas")
    )
    val salesMan = SalesMan(towns)
    val result = salesMan.solve
    println("The shortest route is: ")
    result.foreach( t => println(t.name) )
    println("With a traveling length of: " + salesMan.calculateDistance(result))
  }
}

