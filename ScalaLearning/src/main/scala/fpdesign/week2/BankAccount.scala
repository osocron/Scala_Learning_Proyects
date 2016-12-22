package fpdesign.week2

/**
  * Created by osocron on 20/12/16.
  */
class BankAccount {

  private var balance = 0

  def deposit(amount: Int): Unit = {
    if (amount > 0) balance = balance + amount
  }

  def withdraw(amount: Int): Int =
    if (amount > 0 && amount <= balance) {
      balance = balance - amount
      balance
    } else throw new Error("insufficient funds")

}
