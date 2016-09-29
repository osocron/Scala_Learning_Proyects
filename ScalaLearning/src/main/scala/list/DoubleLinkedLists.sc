abstract class DLinkedList {
  def value: Int
  def next: DLinkedList
  def prev: DLinkedList
  def +(input: Int): DLinkedList
  def isFirst: Boolean
  def isEmpty: Boolean
  def isLast: Boolean
}

class First (int: Int) extends DLinkedList {
  override def value: Int = int
  override def prev: DLinkedList = error("First element of list")
  override def next: DLinkedList = error("First element of list")
  override def +(input: Int): DLinkedList = new Last(this,input)
  override def isFirst: Boolean = true
  override def isEmpty: Boolean = false
  override def isLast: Boolean = false
}

class Last (pointerToPrevious: DLinkedList, int: Int) extends DLinkedList {
  override def value: Int = int
  override def prev: DLinkedList = pointerToPrevious
  override def next: DLinkedList = error("First element of list")
  override def +(input: Int) = {
    val transformed = new NonEmpty(pointerToPrevious,int,new Empty)
    transformed + input
  }
  override def isFirst: Boolean = false
  override def isEmpty: Boolean = false
  override def isLast: Boolean = true
}

class Empty extends DLinkedList {
  override def value: Int = error("Empty cell")
  override def prev: DLinkedList = error("Empty cell")
  override def next: DLinkedList = error("Empty cell")
  override def +(input: Int): DLinkedList = error("Empty cell")
  override def isFirst: Boolean = false
  override def isEmpty: Boolean = true
  override def isLast: Boolean = false
}

class NonEmpty(pointerToPrev: DLinkedList, int: Int, pointerToNext: DLinkedList) extends DLinkedList {
  override def value: Int = int
  override def prev: DLinkedList = pointerToPrev
  override def next: DLinkedList = pointerToNext
  override def +(input: Int): DLinkedList = new Last(this, input)
  override def isFirst: Boolean = false
  override def isEmpty: Boolean = false
  override def isLast: Boolean = false
}

val dLinkedLinst = new First(3)+4+5+6+7+8

def printValues(list: DLinkedList):Unit = {
  if (list.isFirst) println("I'm the first one and my value is: " + list.value + "\n")
  else {
    println("My value is: " + list.value + "\n")
    printValues(list.prev)
  }
}

def sumatoria(list: DLinkedList): Int = {
  if (list.isFirst) list.value
  else list.value + sumatoria(list.prev)
}

printValues(dLinkedLinst)
sumatoria(dLinkedLinst)


