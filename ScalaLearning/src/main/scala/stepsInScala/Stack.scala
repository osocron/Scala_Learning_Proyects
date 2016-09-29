package stepsInScala

import java.util.NoSuchElementException

/**
  * Created by osocron on 7/14/16.
  */
abstract class Stack[T] {
  def top: T
  def rest: Stack[T]
  def push(elem: T): Stack[T]
  def pop: (T, Stack[T])
  def isEmpty: Boolean
}

class EmptyStack[T] extends Stack[T] {
  override def top: T = throw new NoSuchElementException
  override def rest: Stack[T] = throw new NoSuchElementException
  override def push(elem: T): Stack[T] = NonEmptyStack(elem, new EmptyStack[T])
  override def pop: (T, Stack[T]) = throw new NoSuchElementException
  override def isEmpty: Boolean = true
}

case class NonEmptyStack[T](t: T, r: Stack[T]) extends Stack[T] {
  override def top: T = t
  override def rest: Stack[T] = r
  override def push(elem: T): Stack[T] = NonEmptyStack(elem, this)
  override def pop: (T, Stack[T]) = if (r.isEmpty) (t, r) else (t, NonEmptyStack(r.top, r.rest))
  override def isEmpty: Boolean = false
}
