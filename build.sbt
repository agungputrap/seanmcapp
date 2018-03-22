name := "seanmcapp"

version := "0.0"

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  // framework
  "com.typesafe.akka" %% "akka-http" % "10.1.0-RC2",
  "com.typesafe.akka" %% "akka-stream" % "2.5.9",

  // json serializer
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.0-RC2",

  // http builder
  "org.scalaj" % "scalaj-http_2.12" % "2.3.0",

  // ORM
  "com.typesafe.slick" %% "slick" % "3.2.1",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.2.1",
  "org.slf4j" % "slf4j-nop" % "1.6.4",

  // postgre driver
  "org.postgresql" % "postgresql" % "42.1.3",

  // H2 driver
  "com.h2database" % "h2" % "1.4.192",

  // scalatest
  "org.scalatest" %% "scalatest" % "3.0.5" % "test"
)

enablePlugins(JavaAppPackaging)