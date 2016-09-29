import org.scalatest.FunSuite

/**
  * Created by osocron on 4/22/16.
  */
class SalesManTest extends FunSuite {

  val towns = List(Town(List(1,2),"Town1"),Town(List(1,4),"Town2"),Town(List(2,4),"Town3"))
  val salesman = SalesMan(towns)

  test("testCalculateDistance") {
    assert(salesman.calculateDistance(towns) == 7)
  }

  test("testGenerateCombinations") {
    assert(salesman.generateCombinations(towns).length == 6)
  }

}
