package chapter5

/**
  * Created by osocron on 3/22/16.
  * This chapter's focus is on Singleton classes. That is, classes that can only be instantiated once
  * during the entire life of the application. The implementations of this pattern is trivial in Scala
  * since it has the companion Object concept which is actually a Singleton object.
  */
object ChocolateBoiler {
  var isEmpty = true
  var isBoiled = false
  def fill() = if (isEmpty) isEmpty = false; isBoiled = false
  def drain() = if (!isEmpty && isBoiled) isEmpty = true
  def boil() = if (!isEmpty && !isBoiled) isBoiled = true
}

