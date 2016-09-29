val xs = Array(1,2,3,44)

xs map (x => x * 2)

val string = "Hello World"

string filter (c => c.isUpper)

val range = 1 to 10

range map (x => x*x)

range forall (x => x % 2 == 0)

val zipped = range zip string

zipped.unzip

string flatMap(c => List('.',c))

range.sum

range.max

def isPrime(x: Int): Boolean = 2 until x forall (y => x % y != 0)

isPrime(16)