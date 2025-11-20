import org.typelevel.sbt.tpolecat.*

ThisBuild / organization := "org.scalabridge"
ThisBuild / scalaVersion := "3.7.0"

// This disables fatal-warnings for local development. To enable it in CI set the `SBT_TPOLECAT_CI` environment variable in your pipeline.
// See https://github.com/typelevel/sbt-tpolecat/?tab=readme-ov-file#modes
ThisBuild / tpolecatDefaultOptionsMode := VerboseMode

lazy val catsEffectV = "3.6.3"
lazy val ironV = "3.2.1"
lazy val MunitCatsEffectScalaCheck = "1.0.4"
lazy val Munit = "1.2.0"

lazy val root = (project in file(".")).settings(
  name := "static-site-generator",
  libraryDependencies ++= Seq(
    // "core" module - IO, IOApp, schedulers
    // This pulls in the kernel and std modules automatically.
    "org.typelevel" %% "cats-effect" % catsEffectV,
    // concurrency abstractions and primitives (Concurrent, Sync, Async etc.)
    "org.typelevel" %% "cats-effect-kernel" % catsEffectV,
    // standard "effect" library (Queues, Console, Random etc.)
    "org.typelevel" %% "cats-effect-std" % catsEffectV,

    // for data semantics
    "io.github.iltotore" %% "iron" % ironV,

    // for better enumeration
    "com.beachape" %% "enumeratum" % "1.9.0",
    "com.github.j-mie6" %% "parsley" % "4.6.1",
    "org.scalameta" %% "munit" % Munit,
    "org.scalameta" %% "munit-scalacheck" % Munit,
    "org.typelevel" %% "munit-cats-effect" % "2.1.0" % Test,
    "org.typelevel" %% "scalacheck-effect-munit" % MunitCatsEffectScalaCheck % Test
  )
)
