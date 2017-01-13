package algebras

import java.time.LocalDate

import scala.concurrent.Future

/**
  * Created by osocron on 24/12/16.
  */
trait OrderService {

  type Response[A] = Future[A]

  def placeOrder[O  <: NormalOrder,
                 C  <: OrderClient,
                 OS <: OrderStatus](order: O, custumer: C): Response[OS]

  def placeExpressOrder[C   <: OrderClient,
                        ExO <: ExpressOrder,
                        OS  <: OrderStatus](expressOrder: ExO, customer: C): Response[OS]

  def checkStatus[O  <: Order,
                  OS <: OrderStatus](order: O): Response[OS]
}

sealed trait Order {
  def orderId: Long
  def orderDescription: String
  def items: List[Item]
}

sealed case class NormalOrder(orderId: Long,
                              orderDescription: String,
                              items: List[Item]) extends Order

sealed case class ExpressOrder(orderId: Long,
                               orderDescription: String,
                               expressTime: LocalDate,
                               items: List[Item]) extends Order

sealed trait Item
sealed trait OrderClient

sealed trait OrderStatus
sealed case class Sent(dateSent: LocalDate, expectedDeliveryDate: LocalDate, o: Order) extends OrderStatus
sealed case class Delayed(delay: LocalDate) extends OrderStatus
sealed case class Canceled(cancelDate: LocalDate) extends OrderStatus
object NotSent extends OrderStatus