package week3

/**
  * Created by osocron on 2/1/16.
  */
object changeCounter extends App{

  def countChange(money: Int, coins: List[Int]): Int = {
    if (money == 0) 1
    else if (coins.isEmpty || money < 0) 0
    else countChange(money - coins.head, coins) + countChange(money, coins.tail)
  }

  println(countChange(7, List(2,1)))

  def sum(f: Int => Int)(a: Int, b: Int): Int = {
    def loop(a: Int, acc: Int): Int = {
      if (a > b) acc
      else loop(a + 1, f(a) + acc)
    }
    loop(a, 0)
  }

  println(sum((x: Int) => x * x)( 5, 10))

  def product(f: Int => Int)(a: Int, b: Int): Int = {
    def loop(a: Int, acc: Int): Int = {
      if (a > b) acc
      else loop(a + 1, f(a) * acc)
    }
    loop(a, 1)
  }

  println(product((x: Int) => x * x)(1,5))

  def factorial (x: Int) = product(x => x)(1,x)

  println(factorial(8))

  def generalized(f: Int => Int)(g: (Int,Int) => Int)(a: Int, b: Int)(n: Int): Int = {
    def loop(a: Int, acc: Int): Int = {
      if (a > b) acc
      else loop(a + 1, g(f(a),acc)  )
    }
    loop(a, n)
  }

  println(generalized(x => x)((y,z) => y*z)(1,3)(1))

}




  /*If I want to see the ways I can give 10 pesos from 1, 2, 3, 5 coins this is what I would do. First take the
  * biggest coin, then add them until I get 10 which would be 5 + 5. Then I would start using 5 and 3, that would be
  * 5 + 3 + 3 but that is 11, the rule would be not to exceed the amount in which case instead of 3 I would use the next
  * smaller number, in this case being 2 then it follows that the next combination would be 5 + 3 + 2. I stop using
  * 5 + 3 + n because the coin 3 cannot be used any less times so I go to the 2 coin and use it with 5. That would yield
  *  5 + 2 + 2 + 1, then following the logic of reducing the coin until only one coin of its kind is used the following
  *  combinations would be 5 + 2 + 1 + 1 + 1, 5+1+1+1+1+1 since 1 is my last kind of coin I proceed to reduce 5 to 3,
  *  then it follows the next combinations. 3+3+3+1, 3+3+2+2, 3+3+2+1+1, 3+3+1+1+1+1,3+2+2+2+1, 3+2+2+1+1+1,
  *  3+2+1+1+1+1+1, 3+1+1+1+1+1+1+1, 2+2+2+2+2, 2+2+2+2+1+1, 2+2+2+1+1+1+1, 2+2+1+1+1+1+1+1, 2+1+1+1+1+1+1+1+1,
  *  1+1+1+1+1+1+1+1+1+1. Now to implement the recursive madness.
  *
  *  As delimiters I have:
  *  -Not to exceed the value
  *  -Start using the biggest type of coin without braking first rule
  *  -When a coin is replaced, use the following bigger valued coin*/


