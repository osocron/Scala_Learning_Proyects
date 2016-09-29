import math.Ordering

def msort[T](xs: List[T])(implicit ord: Ordering[T]): List[T] = {
  val n = xs.length / 2
  if (n == 0) xs
  else {
    def merge(xs: List[T], ys: List[T]): List[T] = (xs,ys) match {
      case (Nil,ys) => ys
      case (xs,Nil) => xs
      case (x :: xs1, y :: ys1) =>
        if (ord.lt(x,y)) x :: merge(xs1, ys)
        else y :: merge(xs, ys1)
    }
    val (fst,snd) = xs splitAt n
    merge(msort(fst),msort(snd))
  }
}

val nums = List(2,-4,5,7,1)
val fruits = List("apple","cinnamon","tomato","strawberry","coconut")
msort(nums)
msort(fruits)

def pack[T](xs: List[T]): List[List[T]] = xs match {
  case Nil => Nil
  case y :: ys =>
    val (zs1,zs2) = xs span (z => z == y)
    zs1 :: pack(zs2)
}

pack(List("a","a","a","b","b","c","d","d"))

def encode[T](xs: List[T]): List[(T,Int)] = pack(xs) map (ys => (ys.head,ys.length))

encode(List("a","a","a","b","b","c","d","d"))

encode(List(1,1,1,2,3,3,3,5,7,7,7,8,9,9,10,11,11,11,11,11))

val list = List(1,2,3,4,5,6)

List("Noe","Juan","Pedro","Chucho")


