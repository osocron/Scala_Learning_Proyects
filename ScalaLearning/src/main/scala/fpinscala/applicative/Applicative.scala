package fpinscala.applicative

import fpinscala.monads.Functor

/**
  * Created by osocron on 4/11/16.
  */
trait Applicative[F[_]] extends Functor[F] {
  def map2[A,B,C](fa: F[A], fb: F[B])(f: (A, B) => C):F[C]
  def apply[A,B](fab: F[A => B])(fa: F[A]): F[B]
  def unit[A](a: A):F[A]
}
