package algebras

import java.time.LocalDate

/**
  * Created by osocron on 24/12/16.
  */
trait OrderService {
  def placeOrder(o: Order, c: Customer): Response[OrderStatus]
  def checkStatus(o: Order): Response[OrderStatus]
}

sealed case class Order(orderId: Long, orderDescripction: String, items: List[Item])

sealed trait Item
sealed trait Customer

sealed trait OrderStatus
sealed case class Sent(dateSent: LocalDate, expectedDeliveryDate: LocalDate, orderInfo: Order) extends OrderStatus
sealed case class Delayed(delay: LocalDate) extends OrderStatus
sealed case class Canceled(cancelDate: LocalDate) extends OrderStatus
sealed class NotSent extends OrderStatus

trait Response[+A]
sealed case class Successful[A](value: A) extends Response[A]
sealed class Failed extends Response[Nothing]