package calcpp

import org.scalatest.FlatSpec
/**
  * Created by osocron on 3/03/16.
  * This is a simple example of test driven development, works like a charm!
  */
class CalcppTests extends FlatSpec{

  "A Calculator" should "add two numbers" in {
    assertResult(3) {
      Calculator.addTwoOperands(1, 2)
    }
  }

  it should "rest two numbers" in {
    assertResult(1) {
      Calculator.restTwoOperands(3, 2)
    }
  }

  it should "sum three numbers" in {
    assertResult(6) {
      Calculator.add(1, 2, 3)
    }
  }

  it should "sum n numbers" in {
    assertResult(21) {
      Calculator.add(1, 2, 3, 4, 5, 6)
    }
  }

  it should "rest three numbers" in {
    assertResult(-4) {
      Calculator.rest(1, 2, 3)
    }
  }

  it should "rest n numbers" in {
    assertResult(-34) {
      Calculator.rest(1, 2, 3, 4, 5, 6, 7, 8)
    }
  }

  it should "multiplly two numbers" in {
    assertResult(20) {
      Calculator.multiply(4, 5)
    }
  }

  it should "multiply n numbers" in {
    assertResult(384) {
      Calculator.multiply(2, 4, 6, 8)
    }
  }

  it should "divide two numbers" in {
    assertResult(4) {
      Calculator.divide(8, 2)
    }
  }

  it should "throw an error when divided by zero" in {
    intercept[java.lang.ArithmeticException]{
      Calculator.divide(8,0)
    }
  }

}
