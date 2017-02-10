package algebras

import java.time.LocalDate

import scala.util.{Failure, Success, Try}

/**
  * Chapter 3 of the book functional domain modeling in Scala
  */
trait AccountService[Account, Amount, Balance] {

  //To inject a repository capability to the service we curry the repo or in other words,
  //use the Reader Monad which encapsulates the fact that we need an enviroment in order to
  //get a value
  def open(no: String, name: String, openDate: Option[LocalDate]): Reader[AccountRepository, Try[Account]]
  def close(account: Account, closeDate: Option[LocalDate]): Reader[AccountRepository, Try[Account]]
  def debit(account: Account, amount: Amount): Reader[AccountRepository, Try[Account]]
  def credit(account: Account, amount: Amount): Reader[AccountRepository, Try[Account]]
  def balance(account: Account): Reader[AccountRepository, Try[Balance]]

  def transfer(from: Account,
               to: Account,
               amount: Amount): Reader[AccountRepository,
                                       (Try[Account], Try[Account], Amount)] =
    for {
      a <- debit(from, amount)
      b <- credit(to, amount)
    } yield (a, b, amount)
}

trait AccountServiceImp {

  type Amount = BigDecimal

  def today: LocalDate = LocalDate.now()

  case class Balance(amount: Amount = 0)

  case class Account(no: String,
                     name: String,
                     dateOfOpen: LocalDate,
                     dateOfClose: Option[LocalDate] = None,
                     balance: Balance = Balance() )

  /*object AccountService extends AccountService[Account, Amount, Balance] {

    override def open(no: String, name: String, openDate: Option[LocalDate]): Try[Account] = {
      if (no.isEmpty || name.isEmpty)
        Failure(new Exception(s"Account no or name cannot be blank"))
      else if (openDate getOrElse today isBefore today)
        Failure(new Exception(s"Cannot open account in the past"))
      else Success(Account(no, name, openDate getOrElse today))
    }

    override def close(account: Account, closeDate: Option[LocalDate]): Try[Account] = {
      val cd = closeDate.getOrElse(today)
      if (cd isBefore account.dateOfOpen)
        Failure(new Exception(s"Close date $cd cannot be before opening date ${account.dateOfOpen}"))
      else Success(account.copy(dateOfClose = Some(cd)))
    }

    override def debit(account: Account, amount: Amount): Try[Account] = {
      if (account.balance.amount < amount)
        Failure(new Exception("Insufficient balance c5 d3 d4 e4"))
      else Success(account.copy(balance = Balance(account.balance.amount - amount)))
    }

    override def credit(account: Account, amount: Amount): Try[Account] =
      Success(account.copy(balance = Balance(account.balance.amount + amount)))

    override def balance(account: Account): Try[Balance] = Success(account.balance)

  }*/


}
