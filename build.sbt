name := """java-play-websocket"""
organization := "com.vc"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.1"
val AkkaVersion = "2.5.31"

libraryDependencies ++= Seq(
  guice,
  "com.lightbend.akka" %% "akka-stream-alpakka-file" % "2.0.0-RC2",
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.xebia" % "jackson-lombok" % "1.1"
)
