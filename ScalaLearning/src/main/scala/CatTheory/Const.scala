package CatTheory

import cats.functor.Bifunctor

/**
  * Created by osocron on 22/12/16.
  */
sealed case class Const[A, B](get: A)

object Const {
  implicit def ConstFunctor[X, Y] = new Bifunctor[Const[X, Y]] {
    override def bimap[A, B, C, D](fab: Const[A, B])(f: (A) => C, g: (B) => D): Const[C, B] = new Const[C, B](f(fab.get))
  }
}