import fpdesign.week2.BankAccount

val acct = new BankAccount

acct deposit 50
acct deposit 20
acct withdraw 50

def repeat(command: => Unit): Unit = new {
  def until(condition: => Boolean): Unit = {
    command
    if (condition) ()
    else until(condition)
  }
}

