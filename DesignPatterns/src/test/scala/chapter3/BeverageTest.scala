package chapter3

import org.scalatest.FunSuite

/**
  * Created by osocron on 3/17/16.
  */
class BeverageTest extends FunSuite {

  test("Mocha beverage should compute its cost after object decoration"){
    val beverage = new Espresso
    assert(beverage.getDescription + " $" + beverage.cost() === "Espresso $1.99")
    var beverage2: Beverage = new HouseBlend
    beverage2 = new Mocha(beverage2)
    beverage2 = new Mocha(beverage2)
    assert(beverage2.getDescription + " $" + beverage2.cost() === "House Blend, Mocha, Mocha $1.29")
  }

  test("House Blend should compute awesome things when coersed"){
    val beverage = new HouseBlend
    val beverage2 = new Small(beverage)
    assert(beverage2.getDescription === "House Blend, Small")
    assert(beverage2.cost() === .99)
  }

}
