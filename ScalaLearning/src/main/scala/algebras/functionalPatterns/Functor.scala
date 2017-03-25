package algebras.functionalPatterns

import java.util.Date

trait Functor[F[_]] {
  def map[A, B](a: F[A])(f: A => B): F[B]
}

trait Applicative[F[_]] extends Functor[F] {

  def ap[A,B](fa: => F[A])(f: => F[A => B]): F[B]

  def apply2[A,B,C](fa: F[A], fb: F[B])(f: (A, B) => C): F[C] = ap(fb)(map(fa)(f.curried))

  def lift2[A,B,C](f: (A, B) => C): (F[A], F[B]) => F[C] = apply2(_, _)(f)

  def unit[A](a: => A): F[A]

}

object FunctorUses {

  def fmap[F[_]:Functor[F],A,B](fa: F[A])(f: A => B): F[B] = implicitly[Functor[F]].map(fa)(f)

  trait Account
  def accountsOpenedBefore(date: Date): List[Account] = ???
  def accountFor(no: String): Option[Account] = ???
  def interestOn(a: Account): BigDecimal = ???
  def close(a: Account): Account = ???

  def calculateInterest(dt: Date): List[BigDecimal] = fmap(accountsOpenedBefore(dt))(interestOn)
  def calculateInterest(no: String): Option[BigDecimal] = fmap(accountFor(no))(interestOn)
  def closeAccount(no: String): Option[Account] = fmap(accountFor(no))(close)

}