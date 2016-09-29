package chapter3

/**
  * Created by osocron on 3/17/16.
  * The decorator design pattern proposes to attach additional responsibilities to an object
  * dynamically. Decorators provide a flexible alternative to subclassing for extending
  * functionality.
  * The OO principle learned was:
  *   -Classes should be open for extension but closed for modification.
  */
abstract class Beverage {
  def getDescription:String
  def cost():Double
}

abstract class CondimentDecorator(beverage: Beverage) extends Beverage {
  def getDescription:String
}

abstract class SizeDecorator(beverage: Beverage) extends Beverage {
  def getDescription: String
}

class Espresso extends Beverage {
  override def getDescription: String = "Espresso"
  override def cost(): Double = 1.99
}

class HouseBlend extends Beverage {
  override def getDescription: String = "House Blend"
  override def cost(): Double = .89
}

class Mocha(beverage: Beverage) extends CondimentDecorator(beverage) {
  override def getDescription: String = beverage.getDescription + ", Mocha"
  override def cost(): Double = beverage.cost() + .20
}

class Small(beverage: Beverage) extends SizeDecorator(beverage) {
  override def getDescription: String = beverage.getDescription + ", Small"
  override def cost(): Double = beverage.cost() + .10
}

