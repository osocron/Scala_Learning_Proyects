sealed abstract class BinaryTree extends {
  def +(elem: Int): BinaryTree
  def value: Int
  def isEmpty: Boolean
  def left: BinaryTree
  def right: BinaryTree
}

class EmptyTree extends BinaryTree {
  override def +(elem: Int): BinaryTree = new NonEmptyTree(elem, new EmptyTree, new EmptyTree)
  override def value: Int = error("Empty Node")
  override def isEmpty: Boolean = true
  override def left: BinaryTree = error("Empty Tree")
  override def right: BinaryTree = error("Empty Tree")
}

class NonEmptyTree(internalValue: Int, leftTree: BinaryTree, rightTree: BinaryTree) extends BinaryTree {
  override def +(elem: Int): BinaryTree = {
    if (elem > internalValue) new NonEmptyTree(internalValue, left, right + elem)
    else new NonEmptyTree(internalValue,left + elem,right)
  }
  override def value: Int = internalValue
  override def isEmpty: Boolean = false
  override def left: BinaryTree = leftTree
  override def right: BinaryTree = rightTree
}

val tree = new EmptyTree+1+2+3+4+5+6+21

def sumTree(tree: BinaryTree): Int = {
  if (tree.isEmpty) 0
  else tree.value + sumTree(tree.left) + sumTree(tree.right)
}

sumTree(tree)

def treeToList(tree: BinaryTree, list: List[Int]):List[Int] = {
  def iterLeft(remainingTree: BinaryTree, lista: List[Int]):List[Int] = {
    if (remainingTree.isEmpty) lista
    else iterLeft(remainingTree.left, lista :+ remainingTree.value)
  }
  def iterRight(remainingTree: BinaryTree, lista: List[Int]):List[Int] = {
    if (remainingTree.isEmpty) lista
    else iterRight(remainingTree.right, lista :+ remainingTree.value)
  }
  iterLeft(tree,list) ++ iterRight(tree.right,list)
}

val listTree = treeToList(tree,List())
listTree.max

def listToBinaryTree(tree: BinaryTree, lista: List[Int]):BinaryTree = lista match {
  case Nil => tree
  case h :: t => listToBinaryTree(tree + h, t)
}

val list = List(33,2,56,12,75,32,78,1,7,23,74,12,8)
val treeFromList = listToBinaryTree(new EmptyTree, list)
sumTree(treeFromList)