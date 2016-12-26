package algebras

import java.time.LocalDate

import scala.concurrent.Future

/**
  * Created by osocron on 24/12/16.
  */
trait OrderService {

  type Response[A] = Future[A]

  def placeOrder[I  <: Item,
                 O  <: NormalOrder[I],
                 C  <: Customer,
                 OS <: OrderStatus](order: O, custumer: C): Response[OS]

  def placeExpressOrder[C   <: Customer,
                        I   <: Item,
                        ExO <: ExpressOrder[I],
                        OS  <: OrderStatus](expressOrder: ExO, customer: C): Response[OS]

  def checkStatus[I  <: Item,
                  O  <: Order[I],
                  OS <: OrderStatus](order: O): Response[OS]
}

sealed trait Order[I <: Item] {
  def orderId: Long
  def orderDescripction: String
  def items: List[I]
}

sealed case class NormalOrder[I <: Item](orderId: Long,
                                         orderDescripction: String,
                                         items: List[I]) extends Order[I]

sealed case class ExpressOrder[I <: Item](orderId: Long,
                                          orderDescripction: String,
                                          expressTime: LocalDate,
                                          items: List[I]) extends Order[I]

sealed trait Item
sealed trait Customer

trait OrderStatus
sealed case class Sent[I <: Item](dateSent: LocalDate, expectedDeliveryDate: LocalDate, o: Order[I]) extends OrderStatus
sealed case class Delayed(delay: LocalDate) extends OrderStatus
sealed case class Canceled(cancelDate: LocalDate) extends OrderStatus
object NotSent extends OrderStatus