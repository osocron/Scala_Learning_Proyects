package chapter1

/**
  * Created by osocron on 16/03/16.
  * The strategy design pattern encapsulates a family of algorithms, encapsulates each one, and makes them
  * interchangeable. Strategy lets the algorithm vary independently from clients that use it.
  * During this example we make use of the following OO principles:
  *   -Encapsulate what varies
  *   -Favor composition over inheritance
  *   -Program to interfaces, not implementations
  */

/**
  *This is Duck interface which uses composition to use the Flying and Quacking
  * behaviour and to change it at runtime if so desired.
  */
abstract class Duck {
  //Pointer to the desired behaviours to be used by a Duck implementation
  var _flyBehavior: FlyBehavior
  var _quackBehavior: QuackBehavior
  //Set methods
  def flyBehavior_= (flyBehavior: FlyBehavior): Unit = _flyBehavior = flyBehavior
  def quackBehavior_= (quackBehavior: QuackBehavior): Unit = _quackBehavior = quackBehavior
  //Call to the behaviour's abstract method
  def performFly() = _flyBehavior.fly()
  def performQuack() = _quackBehavior.quack()
  //Methods used by all kinds of ducks
  def swim() = println("Swimming")
  def display():String
}

/**
  * This are the interfaces to the family of algorithms that are being encapsulated.
  * It was decided to encapsulate the fly and quack methods because they could change
  * and vary depending of each kind of Duck.
  */
trait FlyBehavior {
  def fly():String
}

trait QuackBehavior {
  def quack():String
}


/**
  * This are the implementations of the Fly and Quack interfaces
  */
class FlyWithWings extends FlyBehavior {
  override def fly(): String = "I fly with wings"
}

class FlyRocketPowered extends FlyBehavior {
  override def fly(): String = "I fly with me rockets"
}

class DoesNotFly extends FlyBehavior {
  override def fly(): String = "I can't fly"
}

class Quack extends QuackBehavior {
  override def quack(): String = "I quack"
}

class Squeak extends QuackBehavior{
  override def quack(): String = "I squeak"
}

class MuteQuack extends QuackBehavior{
  override def quack(): String = "I can't quack"
}


/**
  * All Ducks implement the Duck interface and through composition make
  * use of the different behaviours.
  */
class MallardDuck extends Duck {
  override var _flyBehavior: FlyBehavior = new FlyWithWings
  override var _quackBehavior: QuackBehavior = new Quack
  override def display(): String = "I am a Mallard Duck"
}

class RedHeadDuck extends Duck {
  override var _flyBehavior: FlyBehavior = new FlyWithWings
  override var _quackBehavior: QuackBehavior = new Quack
  override def display(): String = "I am a Red Head Duck"
}

class BathDuck extends Duck {
  override var _flyBehavior: FlyBehavior = new DoesNotFly
  override var _quackBehavior: QuackBehavior = new Squeak
  override def display(): String = "I am a cute Bath Duck"
}

class ModelDuck extends Duck {
  override var _flyBehavior: FlyBehavior = new DoesNotFly
  override var _quackBehavior: QuackBehavior = new Quack
  override def display(): String = "I am a Model Duck"
}


