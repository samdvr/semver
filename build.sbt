ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.1.2"

lazy val root = (project in file("."))
  .settings(
    name := "semver"
  )

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-parse" % "0.3.7",
  "org.scalacheck" %% "scalacheck" % "1.15.4" % "test"
)
