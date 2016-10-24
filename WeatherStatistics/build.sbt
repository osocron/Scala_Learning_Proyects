name := "WeatherStatistics"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.scalaj" %% "scalaj-http" % "2.3.0",
  "org.scalatest" %% "scalatest" % "2.2.6" % "test",
  "org.scala-lang" % "scala-xml" % "2.11.0-M4",
  "com.typesafe.akka" % "akka-actor_2.11" % "2.4.8")
    