package algebras

import java.time.LocalDate

import cats.Foldable
import cats.kernel.Monoid

/**
  * Created by osocron on 11/01/17.
  */
sealed trait TransactionType
case object DR extends TransactionType
case object CR extends TransactionType

sealed trait Currency
case object USD extends Currency
case object JPY extends Currency
case object AUD extends Currency
case object INR extends Currency

case class Money(m: Map[Currency, BigDecimal]) {
  def toBaseCurrency: BigDecimal = ???
}

case class Transaction(txid: String,
                       accountNo: String,
                       date: LocalDate,
                       amount: Money,
                       txnType: TransactionType,
                       status: Boolean)

case class Balance(b: Money)

trait Analytics[Transaction, Balance, Money] {
  def maxDebitOnDay(txns: List[Transaction])(implicit m: Monoid[Money]): Money
  def sumBalances(bs: List[Balance])(implicit m: Monoid[Money]): Money
}

object Analytics extends Analytics[Transaction, Balance, Money] {

  final val zeroMoney: Money = Money(Monoid[Map[Currency, BigDecimal]].empty)

  implicit def MoneyAdditionMonoid = new Monoid[Money] {
    val m = implicitly[Monoid[Map[Currency, BigDecimal]]]
    override def empty: Money = zeroMoney
    override def combine(x: Money, y: Money): Money = Money(m.combine(x.m, y.m))
  }

  implicit def MoneyCompMonoid = new Monoid[Money] {
    override def empty: Money = zeroMoney
    override def combine(x: Money, y: Money): Money = if (x.toBaseCurrency > y.toBaseCurrency) x else y
  }

  def mapReduce[F[_], A, B](as: F[A])(f: A => B)
                           (implicit fd: Foldable[F], m: Monoid[B]): B = fd.foldMap(as)(f)

  def maxDebitOnDay(txns: List[Transaction])(implicit m: Monoid[Money]): Money = {
    mapReduce(txns.filter(_.txnType == DR))(valueOf)
  }

  def sumBalances(balances: List[Balance])(implicit m: Monoid[Money]): Money = {
    mapReduce(balances)(creditBalance)
  }

  private def valueOf(txn: Transaction): Money = ???

  private def creditBalance(b: Balance): Money = ???
}
