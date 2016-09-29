def last[T](xs: List[T]):T = xs match{
  case List() => throw new Error("Empty List")
  case List(x) => x
  case h :: tail => last(tail)
}

last(List("one","two","three"))

def init[T](xs: List[T]): List[T] = xs match {
  case List() => throw new Error("Empty List")
  case List(x) => List()
  case y :: ys => y :: init(ys)
}

init(List("one","two","three"))

def concat[T](xs: List[T], ys: List[T]): List[T] = xs match {
  case List() => ys
  case z :: zs => z :: concat(zs,ys)
}

concat(List("one","two","three"),List("one","two","three"))

def reverse[T](xs: List[T]): List[T] = xs match {
  case List() => xs
  case y :: ys => reverse(ys) ++ List(y)
}

reverse(List("one","two","three"))

def removeAt[T](xs: List[T], n: Int): List[T] = n match {
  case (x: Int) if n >= xs.length => throw new Error("N out of bounds")
  case 0 => xs.tail
  case _ => xs.head :: removeAt(xs.tail, n - 1)
}

removeAt(List("one","two","three","four","five"), 2)

def flatten(xs: List[Any]): List[Any] = xs.flatten

