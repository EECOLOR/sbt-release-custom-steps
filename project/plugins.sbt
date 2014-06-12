addSbtPlugin("com.github.gseitz" % "sbt-release" % "0.8.3")

// we depend on ourselves
unmanagedSourceDirectories in Compile += baseDirectory.value / "src" / "main" / "scala"
