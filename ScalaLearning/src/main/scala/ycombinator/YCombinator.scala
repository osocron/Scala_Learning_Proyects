package ycombinator

/**
  * Created by osocron on 10/10/16.
  */
object YCombinator extends App {


  /**
    * This is the original recursive factorial function
    *
    * @param n A positive integer number
    * @return  The factorial of n
    */
  def factorial(n: Int): Int = if (n == 0) 1 else factorial(n - 1) * n

  /**
    * If we abstract over the factorial function so that the call to factorial
    * is replaced to a call to a function f that is taken as a parameter then
    * we are beginning to create an anonymous recursive function.
    *
    * @param factorial The factorial function as a parameter
    * @return          The result of computing the recursive function factorial.
    */
  def semi_factorial(factorial: Int => Int): Int => Int =
    n => if (n == 0) 1 else factorial(n - 1) * n

  /**
    * This is the tricky part, if we abstract the semi_factorial function and
    * pass it as a parameter to this function we get the following.
    *
    * @param semi_factorial The semi_factorial function taken as a parameter
    * @return               A function from Int to Int
    */
  def semi_abstract(semi_factorial: (Int => Int) => (Int => Int)): Int => Int =
    semi_factorial(semi_abstract(semi_factorial))

  /**
    * If we write semi_factorial in a type generic way we get the following
    *
    * @param f   A function that takes a function as a paramter and returns a function
    * @tparam A  The first type parameter of the function
    * @tparam B  The second type parameter of the function
    * @return    A function from A to B
    */
  def fix[A, B](f: (A => B) => (A => B)): (A => B) = (a: A) => f(fix(f))(a)

  /**
    * Factorial can be rewritten in terms of the fix function in an
    * anonymous way. This is beautiful...
    */
  val fact = fix[Int, Int](f => a => if (a <= 0) 1 else f(a - 1) * a)

  println(factorial(10))

  println(semi_factorial(factorial)(10))

  println(fact(10))

}
