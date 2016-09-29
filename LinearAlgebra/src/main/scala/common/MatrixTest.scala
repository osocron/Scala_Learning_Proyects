package common

import matrices._
/**
  * Created by ventas on 06/08/2016.
  */
object MatrixTest extends App {

  val testMatrix = Matrix(
    List(  2,  4,  6, 21,  4),
    List(  3,  5, 24,  7, 23),
    List( 12,  7,  3, 88,  1),
    List(  0, 23,  5,  2, 34),
    List(  1, 76,  2,  0,  0)
  )

  val m1 = Matrix(
    List(4),
    List(6),
    List(7)
  )

  val m2 = Matrix(
    List(2),
    List(1),
    List(0)
  )

  val m3 = m1 / 2
  val m4 = m2 * 3
  val res = m3 - m4

  val m5 = Matrix(
    List(1, 3),
    List(4, 0),
    List(2, 1)
  )

  val m6 = Matrix(
    List(1),
    List(5)
  )

  val mMult = m5 * m6

  val m7 = Matrix(
    List(1, 3, 2),
    List(4, 0, 1)
  )

  val m8 = Matrix(
    List(1, 3),
    List(0, 1),
    List(5, 2)
  )

  println(m7 * m8)

}
