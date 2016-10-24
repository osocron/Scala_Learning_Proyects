package scaladata

import org.apache.spark.sql.SparkSession

/**
  * Created by osocron on 9/24/16.
  */

object ScalaData extends App {

  val spark = SparkSession
    .builder()
    .master("local")
    .appName("ScalaData")
    .getOrCreate()

  import spark.implicits._

  val data = spark.read.option("header", "true")
    .csv(ScalaData.getClass.getResource("/nyc_crime2014.csv").getPath)

  data.printSchema()

  data.select("PARK").show()

  val filtered = data.filter($"Rape" > 0)

  filtered.show()

  val grouped = data.groupBy("BOROUGH")

  grouped.count().show()

}
