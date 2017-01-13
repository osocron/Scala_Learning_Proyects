package algebras

import java.time.LocalDate

import scala.util.{Failure, Success, Try}

/**
  * From the book Functional and Reactive Domain Modeling
  */
object ChapterThreeAccount {
  type Amount = BigDecimal
  def today: LocalDate = LocalDate.now()
}

import ChapterThreeAccount._

case class Balance(amount: Amount = 0)

sealed trait Account {
  def no: String
  def name: String
  def dateOfOpen: Option[LocalDate]
  def dateOfClose: Option[LocalDate]
  def balance: Balance
}

final case class CheckingAccount private(no: String,
                                         name: String,
                                         dateOfOpen: Option[LocalDate],
                                         dateOfClose: Option[LocalDate],
                                         balance: Balance) extends Account

final case class SavingsAccount private (no: String,
                                         name: String,
                                         rate: Amount,
                                         dateOfOpen: Option[LocalDate],
                                         dateOfClose: Option[LocalDate],
                                         balance: Balance) extends Account

final case class MoneyMarketAccount private (no: String,
                                             name: String,
                                             dateOfOpen: Option[LocalDate],
                                             dateOfClose: Option[LocalDate],
                                             balance: Balance) extends Account

object Account {

  def checkingAccount(no: String,
                      name: String,
                      dateOfOpen: Option[LocalDate],
                      dateOfClose: Option[LocalDate],
                      balance: Balance): Try[Account] = {

    closeDateCheck(dateOfOpen, dateOfClose).map {
      case (od, cd) => CheckingAccount(no, name, Some(od), cd, balance)
    }

  }

  def savingsAccount(no: String,
                     name: String,
                     rate: Amount,
                     dateOfOpen: Option[LocalDate],
                     dateOfClose: Option[LocalDate],
                     balance: Balance): Try[Account] = {

    closeDateCheck(dateOfOpen, dateOfClose) map {
      case (od, cd) => {
        if (rate >= BigDecimal(0))
          throw new Exception(s"Interest rate $rate must be > 0")
        else
          SavingsAccount(no, name, rate, Some(od), cd, balance)
      }
    }

  }

  private def closeDateCheck(openDate: Option[LocalDate],
                             closeDate: Option[LocalDate]): Try[(LocalDate, Option[LocalDate])] = {
    val od = openDate getOrElse today
    closeDate map { cd =>
      if (cd isBefore od)
        Failure(new Exception(s"Close date [$cd] cannot be earlier than open date [$od]"))
      else Success((od, Some(cd)))
    } getOrElse Success((od, closeDate))
  }

}

