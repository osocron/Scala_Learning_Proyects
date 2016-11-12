package fpinscala.monads

import fpinscala.state.State
import fpinscala.testing.Gen

/**
  * A Monad is a Type Class that defines an unit and a flatMap function.
  * By providing an implementation for unit and flatMap we get map and map2
  * for free!
  */

trait Monad[M[_]] extends Functor[M] {
  /**This two functions are required in order for a Type to have
    * a monad instance*/
  def unit[A](a: => A): M[A]
  def flatMap[A, B](ma: M[A])(f: A => M[B]): M[B]
  /**This other two functions we get for free just by having the two above*/
  override def map[A, B](ma: M[A])(f: A => B): M[B] = flatMap(ma)(a => unit(f(a)))
  def map2[A, B, C](ma: M[A], mb: M[B])(f: (A, B) => C): M[C] =
    flatMap(ma)(a => map(mb)(b => f(a, b)))

  def sequence[A](lma: List[M[A]]): M[List[A]] =
    lma.foldRight(unit(List[A]()))((ma, mla) => map2(ma, mla)(_ :: _))
  def traverse[A,B](la: List[A])(f: A => M[B]): M[List[B]] =
    la.foldRight(unit(List[B]()))((a, mlb) => map2(f(a), mlb)(_ :: _))

  def replicateM[A](n: Int, ma: M[A]): M[List[A]] =
    sequence(List.fill(n)(ma))

  def factor[A,B](ma: M[A], mb: M[B]): M[(A, B)] = map2(ma, mb)((_, _))
  def cofactor[A,B](e: Either[M[A], M[B]]): M[Either[A, B]] = e match {
    case Left(v) => map(v)(Left(_))
    case Right(v) => map(v)(Right(_))
  }
  def compose[A,B,C](f: A => M[B], g: B => M[C]): A => M[C] =
    a => flatMap(f(a))(g)

  def _flatMap[A,B](ma: M[A])(f: A => M[B]): M[B] =
    compose((_: Unit) => ma, f)(())

  def join[A](mma: M[M[A]]): M[A] = flatMap(mma)(ma => ma)

}

object Monad {

  val genMonad = new Monad[Gen] {
    override def unit[A](a: => A): Gen[A] = Gen.unit(a)
    override def flatMap[A, B](ma: Gen[A])(f: (A) => Gen[B]): Gen[B] = ma flatMap f
  }

  def stateMonad[S] = new Monad[({type lambda[x] = State[S,x]})#lambda] {
    def unit[A](a: => A): State[S,A] = State(s => (a, s))
    def flatMap[A,B](st: State[S,A])(f: A => State[S,B]): State[S,B] =
      st flatMap f
  }

}
