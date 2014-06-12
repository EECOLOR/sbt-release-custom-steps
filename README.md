Simple library with release steps
=================================

See https://github.com/sbt/sbt-release for more information

Available release steps:

- UpdateVersionInFiles()

Usage
-----

Add the library to the `project/plugins.sbt` as a `libraryDependency`.

``` scala
libraryDependencies += "org.qirx" %% "sbt-release-custom-steps" % "0.4"

resolvers += "Rhinofly Internal Repository" at "http://maven-repository.rhinofly.net:8081/artifactory/libs-release-local"
```

Then in your build, just add the step (after adding the `releaseSettings`)

``` scala
import org.qirx.sbtrelease

releaseSettings

sbtrelease.UpdateVersionInFiles(file("README.md"), file("someOther.file"))
```