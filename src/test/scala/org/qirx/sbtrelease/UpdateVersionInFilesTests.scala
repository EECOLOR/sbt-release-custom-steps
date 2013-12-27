package org.qirx.sbtrelease

import org.specs2.mutable.Specification
import sbt._

object UpdateVersionInFilesTests extends Specification {

  "UpdateVersionInFiles" should {

    "insert itself into the build steps" in {

      val settings = UpdateVersionInFiles(file("unknown"))
      pending("I do not know how to test this.")
    }

  }

}