def queens(n: Int): Set[List[Int]] = {
  def placeQueens(k: Int): Set[List[Int]] = {
    if (k == 0) Set(List())
    else
      for {
        queens <- placeQueens(k-1)
        col <- 0 until n
        if isSafe(col, queens)
      } yield col :: queens
  }
  placeQueens(n)
}

def isSafe(col: Int, solutions: List[Int]): Boolean = {
  val row = solutions.length
  val queensWithRow = (row - 1 to 0 by -1) zip solutions
  queensWithRow forall {
    case (r,c) => col != c && math.abs(col - c) != row - r
  }
}

def show(queens: List[Int]) = {
  val lines =
    for (col <- queens.reverse)
      yield Vector.fill(queens.length)("* ").updated(col, "X ").mkString
   "\n" + (lines mkString "\n")
}

queens(5) map show

val countriesAndCapitals = Map("US" -> "Washington", "Mexico" -> "Mexico", "Argentina" -> "Buenos Aires")

countriesAndCapitals("US")

countriesAndCapitals get "UL"

