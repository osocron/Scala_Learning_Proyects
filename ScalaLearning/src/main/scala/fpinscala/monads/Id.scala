package fpinscala.monads

/**
  * Created by osocron on 4/11/16.
  */
case class Id[A](value: A) {
  def map[B](f: A => B): Id[B] = Id(f(value))
  def flatMap[B](f: A => Id[B]): Id[B] = f(value)
}

object Id {

  val idMonad = new Monad[Id] {
    /** This two functions are required in order for a Type to have
    * a monad instance */
    override def unit[A](a: => A): Id[A] = Id(a)
    override def flatMap[A, B](ma: Id[A])(f: (A) => Id[B]): Id[B] = ma.flatMap(f)
  }

}
