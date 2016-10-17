package fpinscala.parallel

import java.util.concurrent._

/**
  * Created by osocron on 10/6/16.
  */

object Par {

  /**
    * A parallel computation can be described as a function from an
    * [[ExecutorService]] to a [[Future]]
    *
    * @tparam A The type of the parallel computation.
    */
  type Par[A] = ExecutorService => Future[A]

  /**
    * The pure or unit function. Given an element of type `A` it
    * returns an `A` wrapped in a [[Par]] container
    *
    * @param a  The computation to be performed
    * @tparam A The type of the computation
    * @return   An `A` wrapped in a [[Par]]
    */
  def unit[A](a: A): Par[A] = ex => UnitFuture(a)

  private case class UnitFuture[A](get: A) extends Future[A] {
    def isDone = true
    def get(timeout: Long, units: TimeUnit) = get
    def isCancelled = false
    def cancel(evenIfRunning: Boolean): Boolean = false
  }

  /**
    * Runs the parallel computation given an [[ExecutorService]]
    *
    * @param s   The ExecutorService
    * @param a   The parallel computation
    * @tparam A  The type of the parallel computation
    * @return    A [[Future]]
    */
  def run[A](s: ExecutorService)(a: Par[A]): Future[A] = a(s)

  /**
    * Starts a computation on a separate thread.
    *
    * @param a  A parallel computation
    * @tparam A The type of the parallel computation
    * @return   A parallel computation executed on a different thread
    */
  def fork[A](a: => Par[A]): Par[A] = ex => ex.submit(new Callable[A] {
    override def call(): A = a(ex).get
  })

  /**
    * An asynchronous version of the function
    *
    * @param a  A computation of type `A`
    * @tparam A The type of the computation
    * @return   A forked parallel computation
    */
  def async[A](a: => A): Par[A] = fork(unit(a))

  def delay[A](fa: => Par[A]): Par[A] = ex => fa(ex)

  def lazyUnit[A](a: => A): Par[A] = fork(unit(a))

  /**
    * Maps over two [[Par]] computations
    *
    * @param a  A parallel computation
    * @param b  A parallel computation
    * @param f  A function that takes an `A` and a `B` and returns a `C`
    * @tparam A The type of a
    * @tparam B The type of b
    * @tparam C The type of c
    * @return   A parallel computation of type `C`
    */
  def map2[A, B, C](a: Par[A], b: Par[B])(f: (A, B) => C): Par[C] = ex => {
    val af = a(ex)
    val bf = b(ex)
    UnitFuture(f(af.get, bf.get))
  }

  def asyncF[A,B](f: A => B): A => Par[B] = a => lazyUnit(f(a))

  def sortPar(l: Par[List[Int]]): Par[List[Int]] = map(l)(a => a.sorted)

  /**
    * Applies a function to the parallel computation.
    *
    * @param fa The parallel computation
    * @param f  The function to be applied
    * @tparam A The type of the parallel computation
    * @tparam B The result type of the function
    * @return   A [[Par]] of type `B`
    */
  def map[A,B](fa: Par[A])(f: A => B): Par[B] =
    map2(fa, unit(()))((a,_) => f(a))

  /**
    * Maps over a list in parallel. Cool
    *
    * @param l The list of elements
    * @param f The function to be applied in parallel
    * @tparam A The type of the elements in the List
    * @tparam B The resulting type of the function
    * @return   A parallel list of elements of type `B`
    */
  def parMap[A,B](l: List[A])(f: A => B): Par[List[B]] = l match {
    case Nil => unit(Nil)
    case h :: t => map2(unit(f(h)), parMap(t)(f))(_ :: _)
  }

  /**
    * Given a List of [[Par]] return a [[Par]] containing the list.
    * It's like a switch.
    *
    * @param l  The List
    * @tparam A The type of the List
    * @return   A switched version.
    */
  def sequence[A](l: List[Par[A]]): Par[List[A]] =
    l.foldRight[Par[List[A]]](unit(List()))((h, t) => map2(h, t)(_ :: _))

  def parMapViaSequence[A, B](l: List[A])(f: A => B): Par[List[B]] = fork {
    val fbs: List[Par[B]] = l.map(asyncF(f))
    sequence(fbs)
  }

  def parFilter[A, B](l: List[A])(f: A => Boolean): Par[List[A]] = fork {
    val pars = l map asyncF((a: A) => if (f(a)) List(a) else List())
    map(sequence(pars))(_.flatten)
  }

  def choice[A](a: Par[Boolean])(ifTrue: Par[A], ifFalse: Par[A]): Par[A] = ex =>
    if (run(ex)(a).get) ifFalse(ex)
    else ifFalse(ex)

  def choiceN[A](n: Par[Int])(choices: List[Par[A]]): Par[A] =
    es => {
      val ind = run(es)(n).get // Full source files
      run(es)(choices(ind))
    }

  def chooser[A,B](p: Par[A])(choices: A => Par[B]): Par[B] =
    es => {
      val k = run(es)(p).get
      run(es)(choices(k))
    }

  /* `chooser` is usually called `flatMap` or `bind`. */
  def flatMap[A,B](p: Par[A])(choices: A => Par[B]): Par[B] =
  es => {
    val k = run(es)(p).get
    run(es)(choices(k))
  }

  def join[A](a: Par[Par[A]]): Par[A] =
    es => run(es)(run(es)(a).get())

}