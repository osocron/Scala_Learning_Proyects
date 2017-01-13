package algebras

import java.time.LocalDate

import scala.util.Try

/**
  * Created by osocron on 10/01/17.
  */
trait Repository[A, IdType] {
  def query(id: IdType): Try[Option[A]]
  def store(a: A): Try[A]
}

trait AccountRepository extends Repository[Account, String] {
  def query(accountNo: String): Try[Option[Account]]
  def store(a: Account): Try[Account]
  def balance(accountNo: String): Try[Balance]
  def openedOn(date: LocalDate): Try[Seq[Account]]
}


//Then you could implement an AccountRepository with an specific technology

class AccountRepositorySlick extends AccountRepository {
  override def query(accountNo: String): Try[Option[Account]] = ???
  override def store(a: Account): Try[Account] = ???
  override def balance(accountNo: String): Try[Balance] = ???
  override def openedOn(date: LocalDate): Try[Seq[Account]] = ???
}

