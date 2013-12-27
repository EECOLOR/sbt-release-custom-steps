package org.qirx.sbtrelease

import java.io.File
import sbtrelease.ReleasePlugin.ReleaseKeys._
import sbtrelease.ReleaseStep
import sbtrelease.ReleaseStateTransformations._
import sbt._
import sbt.Keys._

object UpdateVersionInFiles {

  def apply(files: File*) =
    releaseProcess := withUpdatedFiles(releaseProcess.value, files)

  def withUpdatedFiles(steps: Seq[ReleaseStep], files: Seq[File]) =
    insert(
      step = updateVersionInFiles(files),
      before = commitReleaseVersion,
      in = steps)

  private def insert(step: ReleaseStep, before: ReleaseStep, in: Seq[ReleaseStep]) = {

    val (beforeStep, rest) = in.span(_ != before)
    (beforeStep :+ step) ++ rest
  }

  private def updateVersionInFiles(files: Seq[File]): ReleaseStep = { s: State =>
    val settings = Project.extract(s)

    val pattern = getPattern(settings)
    val version = settings.get(releaseVersion)(settings.get(Keys.version))
    val replacement = "$1" + version + "$2"

    def updateFile(file: File) = {
      val contents = IO.read(file)
      val newContents = contents.replaceAll(pattern, replacement)
      IO.write(file, newContents)
      vcs(settings).add(file.getAbsolutePath) !! s.log
    }

    files.foreach(updateFile)

    s
  }

  private def getPattern(settings:Extracted) = {

    val organization = settings.get(Keys.organization)
    val name = settings.get(Keys.name)
    val % = "\"\\s+%+\\s+\"" // " %% " or "   % "
    val > = "(\""
    val < = ")"
    val versionPattern = "[\\w\\.-_]+"

    // "org.qirx" %% "sbt-webjar" % "version"
    > + organization + % + name + % + < + versionPattern + > + <
  }

  private def vcs(settings:Extracted) =
    settings.get(versionControlSystem)
      .getOrElse(sys.error("Aborting release. Working directory is not a repository of a recognized VCS."))
}