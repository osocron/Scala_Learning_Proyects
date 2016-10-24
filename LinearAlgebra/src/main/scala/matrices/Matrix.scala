package matrices

/**
  *
  * This class provides useful methods to compute basic matrix operations such as scalar addition, multiplication,
  * matrix vector multiplication, inversion, transposition, etc.
  *
  * @param data   The actual data of the matrix
  */
case class Matrix (data: Seq[Double]*) {

  /**
    * The rowSize and columnSize members of the matrix
    */
  val rowSize = data.size
  val columnSize = if (data.nonEmpty) data.head.size else 0

  /**
    * Require that every row has the same number of columns
    */
  require(data.forall(r => r.size == columnSize), "All rows must have the same number of columns")

  /**
    * Total number of elements in the matrix
    */
  val numElems = rowSize * columnSize


  /**
    * Returns the element at the given position.
    * The positions start at index 1 instead of 0
    *
    * @param r  The row position
    * @param c  The column position
    * @return   The element at the given position
    */
  def get(r: Int, c: Int): Double = {

    require(
      r <= rowSize    &&
      r > 0           &&
      c <= columnSize &&
      c > 0,
      "The row and the column arguments must be between bounds of the Matrix"
    )

    data(r - 1)(c - 1)

  }

  /**
    * Returns the sum of two Matrices with the requirement they have
    * to be of the same dimensions.
    *
    * @param that The matrix to be applied as the function argument.
    * @return     The sum of the two Matrices
    */
  def +(that: Matrix): Matrix = this.merge(that, (x, y) => x + y)

  /**
    * Returns the rest of two matrices of the same dimensions
    *
    * @param that The matrix to be substracted
    * @return     The rest of the two matrices
    */
  def -(that: Matrix): Matrix = this.merge(that, (x, y) => x - y)

  /**
    * Merges two matrices and returns the result after applying the given function to each element.
    * Both matrices must be of the same dimension.
    *
    * @param that   The matrix to be merged
    * @param f      The function to be applied to each element
    * @return       The result of merging two matrices and applying the function to each element
    */
  def merge(that: Matrix, f: (Double, Double) => Double) = {

    require(
      rowSize    == that.rowSize    &&
      columnSize == that.columnSize,
      "Matrices must be of the same dimensions."
    )

    val dataZipped = data.zip(that.data)
    val innerListsZipped = dataZipped.map(_.zipped.map(f))

    Matrix(innerListsZipped: _*)

  }

  /**
    * The scalar multiplication function.
    *
    * @param n  The real number used to compute the scalar multiplication of the matrix
    * @return   The result of multiplying n to each element of the matrix
    */
  def *(n: Double): Matrix = Matrix(data.map(_.map(_ * n)): _*)


  /**
    * The scalar division of the matrix
    *
    * @param n  The real number used to compute the scalar division of the matrix
    * @return   The result of dividing each element by n
    */
  def /(n: Double): Matrix = this * (1 / n)


  /**
    * The matrix multiplication of two matrices. The result of a Matrix of (m rows, n columns)
    * multiplied by another Matrix of (n1 rows, m1 columns) is a Matrix of (m rows, m1 columns).
    * The multiplication can only be computed if n == n1.
    *
    * @param that The second argument to the matrix multiplication
    * @return     The result of multiplying two matrices.
    */
  def *(that: Matrix): Matrix = {

    require(columnSize == that.rowSize,
      "The number of columns of matrix one should " +
        "be the same as the number of rows of matrix two")

    val result =
      for (r <- data) yield
        (for (r2 <- that.data.transpose)
          yield r zip r2 map Function.tupled(_ * _)) map (_.sum)

    Matrix(result: _*)

  }


  override def toString: String = (for (r <- data) yield r.mkString("|", "| ", "|")).mkString("\n")


}


