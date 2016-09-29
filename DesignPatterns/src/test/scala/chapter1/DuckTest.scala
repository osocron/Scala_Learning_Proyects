package chapter1

import org.scalatest.FunSuite

/**
  * Created by osocron on 16/03/16.
  */
class DuckTest extends FunSuite {

  test("Mallard Duck should fly") {
    val myMallard = new MallardDuck
    assert(myMallard.performFly() === "I fly with wings")
  }

  test("Mallard Duck should quack") {
    val myMallard = new MallardDuck
    assert(myMallard.performQuack() === "I quack")
  }

  test("Bath Duck should not fly") {
    val myBathDuck = new BathDuck
    assert(myBathDuck.performFly() === "I can't fly")
  }

  test("Runtime changes on Model Duck's flying behavior should work properly") {
    val myModelDuck = new ModelDuck
    assert(myModelDuck.performFly() === "I can't fly")
    myModelDuck.flyBehavior_=(new FlyRocketPowered)
    assert(myModelDuck.performFly() === "I fly with me rockets")
  }

}
