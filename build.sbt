import org.typelevel.sbt.tpolecat.*

ThisBuild / organization := "org.scalabridge"
ThisBuild / scalaVersion := "2.13.17"

// This disables fatal-warnings for local development. To enable it in CI set the `SBT_TPOLECAT_CI` environment variable in your pipeline.
// See https://github.com/typelevel/sbt-tpolecat/?tab=readme-ov-file#modes
ThisBuild / tpolecatDefaultOptionsMode := VerboseMode

lazy val catsEffectV = "3.6.3"

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
    // better monadic for compiler plugin as suggested by documentation
    compilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1"),
    "com.github.j-mie6" %% "parsley" % "4.6.1",
    "org.typelevel" %% "munit-cats-effect" % "2.0.0" % Test,
  )
)
