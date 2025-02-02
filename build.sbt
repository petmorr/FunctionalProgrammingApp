ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.4"

ThisBuild / libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.10" % Test

lazy val root = (project in file("."))
  .settings(
    name := "CW1"
  )
