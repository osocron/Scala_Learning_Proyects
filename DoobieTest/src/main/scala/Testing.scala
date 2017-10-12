import doobie.imports._

import scalaz._
import Scalaz._
import scalaz.concurrent.Task

object Testing extends App {

  val xa = DriverManagerTransactor[Task](
    driver = "com.mysql.jdbc.Driver",
    url = "jdbc:mysql://localhost:3306/doobie_test",
    user = "doobie_test",
    pass = "doobie_test"
  )

  val program: ConnectionIO[Int] = 42.point[ConnectionIO]

  println(program.transact(xa).unsafePerformSync)

  val program2 = sql"SELECT 42".query[Int].unique

  val task2 = program2.transact(xa)

  println(task2.unsafePerformSync)

  val program3 =
    for {
      a <- sql"SELECT 42".query[Int].unique
      b <- sql"SELECT random()".query[Double].unique
    } yield (a, b)

  println(program3.transact(xa).unsafePerformSync)

}
