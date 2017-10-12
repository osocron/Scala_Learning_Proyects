name := "DoobieTest"

version := "1.0"

scalaVersion := "2.12.1"

libraryDependencies ++= Seq(
  "org.tpolecat" %% "doobie-core" % "0.4.1",
  "mysql" % "mysql-connector-java" % "5.1.38"
)
        