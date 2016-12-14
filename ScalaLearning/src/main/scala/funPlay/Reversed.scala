package funPlay

import scalaz.{Foldable, Monad, Monoid}

object Reversed extends App {

  def reversed[A, F[_]](fa: F[A])
                       (implicit
                        monoid: Monoid[F[A]],
                        monad: Monad[F],
                        foldable: Foldable[F]): F[A] = {
    foldable.foldRight(fa, monoid.zero)((a, f) =>
      monoid.append(f, monad.pure(a)))
  }

}