package week4

/**
  * Created by osocron on 5/02/16.
  */
abstract class Nat {
  def isZero: Boolean
  def predecessor: Nat
  def succesor: Nat = new Succ(this)
  def + (that: Nat): Nat
  def - (that: Nat): Nat
}

object Zero extends Nat {

  override def isZero: Boolean = true

  override def + (that: Nat): Nat = that

  override def - (that: Nat): Nat = if (that.isZero) this else throw new NoSuchElementException

  override def predecessor: Nat = throw new NoSuchElementException

}

class Succ (n: Nat) extends Nat {

  override def isZero: Boolean = false

  override def + (that: Nat): Nat = new Succ(n + that)

  override def - (that: Nat): Nat = if (that.isZero) this else n - that.predecessor

  override def predecessor: Nat = n

}

