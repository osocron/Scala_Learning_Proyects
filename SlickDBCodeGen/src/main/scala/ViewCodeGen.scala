import java.sql.Driver

import slick.codegen.SourceCodeGenerator
import slick.driver.MySQLDriver
import slick.driver.MySQLDriver.api._

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import slick.jdbc.meta._

/**
  * Created by osocron on 12/01/17.
  */
object ViewCodeGen extends App {

  val driver: Driver = new com.mysql.jdbc.Driver

  val db = Database.forDriver(driver,
    "jdbc:mysql://localhost:3306/iris?useSSL=false", "iris", "iris")

  val tablesAndViews = MTable.getTables(None, None, None, Some(Seq("VIEW")))
  val modelAction = MySQLDriver.createModel(Some(tablesAndViews))
  val modelFuture = db.run(modelAction)

  val codegenFuture = modelFuture.map(model => new SourceCodeGenerator(model))

  val path = getClass.getResource("").getPath

  Await.ready(
    codegenFuture.map(_.writeToFile("slick.driver.MySQLDriver", path, "views", "Views", "Views.scala")), 20 seconds)

}
