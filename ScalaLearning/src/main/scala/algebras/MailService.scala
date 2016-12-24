package algebras

/**
  * Created by osocron on 24/12/16.
  */
sealed trait MailService {
  def sendMail(r: Recipient, d: Destination, md: MailData): Result[Status]
}

sealed trait Correspondant
sealed case class Recipient(address: MailAddress) extends Correspondant
sealed case class Destination(address: MailAddress) extends Correspondant

sealed case class MailData(message: String)
sealed class MailAddress

sealed trait Result[+A]
sealed case class Successful[A](a: A) extends Result[A]
sealed class Failed extends Result[Nothing]

sealed trait Status
object Sent extends Status
object NotSent extends Status