import org.qirx.sbtrelease

releaseSettings

name := "sbt-release-custom-steps"

organization := "org.qirx"

// just a fancy way to say we depend on this library
addSbtPlugin("com.github.gseitz" % "sbt-release" % "0.8.3" % "provided")

libraryDependencies ++= Seq(
  sbtDependency.value % "provided",
  "org.specs2" %% "specs2" % "2.3.7" % "test"
)

val onlyScalaSources = unmanagedSourceDirectories := Seq(scalaSource.value)

inConfig(Compile)(onlyScalaSources)

inConfig(Test)(onlyScalaSources)

publishTo <<= version(rhinoflyRepo)

credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

sbtrelease.UpdateVersionInFiles(file("README.md"))

def rhinoflyRepo(version: String) = {
  val repo = if (version endsWith "SNAPSHOT") "snapshot" else "release"
  Some("Rhinofly Internal " + repo.capitalize + " Repository" at "http://maven-repository.rhinofly.net:8081/artifactory/libs-" + repo + "-local")
}
