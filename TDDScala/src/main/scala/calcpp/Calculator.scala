package calcpp

/**
  * Created by osocron on 3/03/16.
  * This class is being developed by designing test cases first and then writing the necessary code to pass the tests
  */
object Calculator {
  def divide(operands: Int*) = operands.foldRight(1)((num,acc) => num / acc)
  def multiply(operands: Int*) = operands.product
  def rest(operands: Int*) = operands.foldLeft(operands.head * 2)((acc,num) => acc - num)
  def add(operands: Int*) = operands.sum
  def restTwoOperands(a: Int, b: Int) = a - b
  def addTwoOperands(a: Int, b: Int) = a + b
}
