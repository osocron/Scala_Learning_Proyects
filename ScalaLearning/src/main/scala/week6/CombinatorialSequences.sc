def isPrime(x: Int): Boolean = 2 until x forall (y => x % y != 0)

val n = 7

for {
  i <- 1 until n
  j <- 1 until i
  if isPrime(i + j)
} yield (i,j)


