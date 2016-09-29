import org.scalatest.FunSuite

/**
  * Created by osocron on 4/11/16.
  */
class FunctionCheckerTest extends FunSuite {

  test("Function isFunction should return true for a List of ordered values of Ints " +
    "that conform to the function law"){
    val orderedPairs = List((-2,4),(3,9),(4,16),(5,25))
    assert(FunctionChecker.isFuntcion(orderedPairs))
  }

  test("Function isFunction should return false given a List " +
    "of ordered values of Ints that do not conform to the function law"){
    val orderedPairs = List((-2,4),(3,9),(-2,16),(3,25))
    assert(!FunctionChecker.isFuntcion(orderedPairs))
  }

  test("Should give true given that Set = (2,3)(2,3)(2,3)") {
    val orderedPairs = List((2,3),(2,3),(2,3))
    assert(FunctionChecker.isFuntcion(orderedPairs))
  }

  test("allEqual should be true for (2,2,2,2,2)"){
    assert(FunctionChecker.allEqual(List(2,2,2,2,2)))
  }

  test("allEqual should be false for (4,16)"){
    assert(!FunctionChecker.allEqual(List(2,4)))
  }

  test("Find Duplicates should return List((2,3),(2,3))"){
    assert(FunctionChecker.findDuplicates(List((2,3),(2,3))).equals(List((2,3),(2,3))))
  }

  test("Should be able to create expression 2x") {
    val expr = new AlgebraicExpression(2,"x","+")
    assert(expr.constant == 2 && expr.variable.equals("x") && expr.operand.equals("+"))
  }

}
