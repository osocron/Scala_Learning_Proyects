name := "ScalaLearning"

version := "1.0"

scalaVersion := "2.12.1"

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)

libraryDependencies ++= Seq(
  "org.scalactic" %% "scalactic" % "3.0.0",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test",
  "org.scalaz" %% "scalaz-core" % "7.2.7",
  "org.scalacheck" %% "scalacheck" % "1.13.4" % "test",
  "org.typelevel" %% "cats" % "0.9.0",
  "com.github.thangiee" %% "freasy-monad" % "0.5.0",
  "com.chuusai" %% "shapeless" % "2.3.2")

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
    