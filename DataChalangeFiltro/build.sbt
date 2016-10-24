name := "DataChalangeFiltro"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.apache.spark" % "spark-core_2.11" % "2.0.0",
  "com.github.aishfenton" % "vegas_2.11" % "0.2.3",
  "org.scala-saddle" % "saddle-core_2.11" % "1.3.4",
  "com.github.tototoshi" %% "scala-csv" % "1.3.3",
  "org.scalanlp" %% "breeze" % "0.12",
  "org.scalanlp" %% "breeze-natives" % "0.12",
  "org.scalanlp" %% "breeze-viz" % "0.12",
  "com.nrinaudo" % "kantan.csv_2.11" % "0.1.14")

resolvers += "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/"
    