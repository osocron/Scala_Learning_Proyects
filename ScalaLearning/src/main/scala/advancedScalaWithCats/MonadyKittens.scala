package advancedScalaWithCats

import scala.language.higherKinds
/**
  * Created by osocron on 9/06/17.
  */
object MonadyKittens {

  import cats.Monad
  import cats.instances.option._
  import cats.instances.list._

  val opt1 = Monad[Option].pure(3)
  val opt2 = Monad[Option].flatMap(opt1)(a => Some(a + 2))
  val opt3 = Monad[Option].map(opt2)(a => 100 * 2)

  val list = Monad[List].pure(3)
  val list2 = Monad[List].flatMap(List(1, 2, 3))(x => List(x, x * 10))

  val om = Monad[Option]
  val f = (a: Int) => Some(a * 2)


}
