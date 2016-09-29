package matrices

import org.scalatest.FunSuite

/**
  * Created by ventas on 05/08/2016.
  */
class MatrixTest extends FunSuite {

  val testMatrix = Matrix(
    List(  2,  4,  6, 21,  4),
    List(  3,  5, 24,  7, 23),
    List( 12,  7,  3, 88,  1),
    List(  0, 23,  5,  2, 34),
    List(  1, 76,  2,  0,  0)
  )

  test("matrix.size should return 5 in a matrix of 5 * 1 dimension") {
    val matrix = Matrix(
      List[Double](1,2,3,4,5)
    )
    assert(matrix.numElems == 5)
  }

  test("Matrix.size should return 0 in an empty matrix"){
    assert(Matrix().numElems == 0)
  }

  test("Matrix.size should return 0 in "){
    assert(Matrix(List(), List(), List()).numElems == 0)
  }

  test("A matrix with different column sizes should throw an exception") {
    intercept[IllegalArgumentException] {
      val matrix = Matrix(
        List(1, 2, 3, 4),
        List(1, 2, 3)
      )
    }
  }

  test("testMatrix.get(1, 1) should return 2") {
    assert(testMatrix.get(1, 1) == 2)
  }

  test("The position 1,5 in the sum of two matrices should be 20") {
    val m1 = Matrix(List[Double](5,5,5,5,5))
    val m2 = Matrix(List[Double](5,5,5,5,5))
    assert((m1 + m2).get(1,5) == 10)
  }

  test("The position 1,5 in the rest of two matrices should be -3") {
    val m1 = Matrix(List[Double](5,5,5,5,5))
    val m2 = Matrix(List[Double](8,8,8,8,8))
    assert((m1 - m2).get(1,5) == -3)
  }

  test("The position 1,5 in the result of the scalar multiplication of 4 should be 20") {
    val m1 = Matrix(List[Double](5,5,5,5,5))
    assert((m1 * 4).get(1,5) == 20)
  }

  test("The position 1,5 in the result of the scalar division by 5 should be 1") {
    val m1 = Matrix(List[Double](5,5,5,5,5))
    assert((m1 / 5).get(1,5) == 1)
  }

  test("The position 1,1 in the result of the matrix multiplication of two matrices should be 14") {
    val m1 = Matrix(
      List[Double](1,   2,   1,   5),
      List[Double](0,   3,   0,   4),
      List[Double](-1, -2,   0,   0)
    )
    val m2 = Matrix(
      List[Double](1),
      List[Double](3),
      List[Double](2),
      List[Double](1)
    )
    assert((m1 * m2).get(1, 1) == 14)
  }


}
