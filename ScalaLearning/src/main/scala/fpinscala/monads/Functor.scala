package fpinscala.monads

/**
  * Created by osocron on 4/11/16.
  */
trait Functor[F[_]] {

  def map[A,B](fa: F[A])(f: A => B): F[B]

  def distribute[A,B](fab: F[(A,B)]): (F[A], F[B]) = (map(fab)(_._1), map(fab)(_._2))

}

object Functor {

  val listFunctor = new Functor[List] {
    override def map[A, B](fa: List[A])(f: (A) => B): List[B] = fa map f
  }

}

