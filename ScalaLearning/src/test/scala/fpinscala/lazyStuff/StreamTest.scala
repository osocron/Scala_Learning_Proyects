package fpinscala.lazyStuff

import org.scalatest.FunSuite

/**
  * Created by osocron on 9/15/16.
  */
class StreamTest extends FunSuite {

  val stream = Stream(1,2,3,4,5,6)
  val stream2 = Stream(1, 2, 3)

  test("Stream to list should return a list") {
    assert(stream.toList == List(1,2,3,4,5,6))
  }

  test("take should take first 3 elements") {
    assert(stream.take(3).toList == List(1,2,3))
  }

  test("take by unfold should work as well") {
    assert(stream.takeByUnfold(3).toList == List(1,2,3))
  }

  test("takeWhile should take first four elements") {
    assert(stream.takeWhile(_ < 5).toList == List(1,2,3,4))
  }

  test("exists should return true given p") {
    assert(stream.exists(_ == 2))
  }

  test("forall should return false given p") {
    assert(!stream.forAll(_ == 2))
  }

  test("filter should return first three elements") {
    assert(stream.filter(_ < 4).toList == List(1,2,3))
  }

  test("append should work") {
    assert(stream.append(Stream(7)).toList == List(1,2,3,4,5,6,7))
  }

  test("constant of 4 sohuld return true when exists is applied") {
    assert(Stream.constant(4).exists(_ == 4))
  }

  test("from should return List(1,2,3,4,5) when take(5) is called") {
    assert(Stream.from(1).take(5).toList == List(1,2,3,4,5))
  }

  test("fib should return the first six fibonnacci numbers") {
    assert(Stream.fib.take(6).toList == List(1, 1, 2, 3, 5, 8))
  }

  test("from by unfold should be the same as from") {
    assert(Stream.fromByUnfold(1).take(5).toList == List(1,2,3,4,5))
  }

  test("fib by unfold should be equal to fibc") {
    assert(Stream.fibsByUnfold.take(6).toList == List(1, 1, 2, 3, 5, 8))
  }

  test("startsWith should return true for stream and stream2") {
    assert(Stream.startsWith(stream, stream2))
  }

}
