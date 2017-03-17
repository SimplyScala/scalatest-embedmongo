import sbt._
import sbt.Keys._

// sbt + publish-signed
object ScalaTestEmbededMongoBuild extends Build {
    lazy val root = Project(id = "simplyscala-server", base = file("."),
        settings = Project.defaultSettings ++ Seq(
            name := "scalatest-embedmongo",
            organization := "com.github.simplyscala",
            description := "API to use embeded mongoDb database for testing in Scala",

            version := "0.2.3-SNAPSHOT",

            scalaVersion := "2.12.1",

            crossScalaVersions := Seq("2.10.4", "2.11.1", "2.12.1"),

            libraryDependencies ++= Seq(
                "de.flapdoodle.embed"   % "de.flapdoodle.embed.mongo"   % "1.46.0",
                "org.scalatest"         %% "scalatest"                  % "3.0.1"       % "test",
                "org.mongodb"           %% "casbah"                     % "3.1.1"       % "test"
            ),

            parallelExecution := false,

            publishMavenStyle := true,
            publishArtifact in Test := false,
            pomIncludeRepository := { _ => false },

            pomExtra := (
                <url>https://github.com/SimplyScala/scalatest-embedmongo</url>
                    <licenses>
                        <license>
                            <name>GPLv3</name>
                            <url>http://www.gnu.org/licenses/gpl-3.0.html</url>
                            <distribution>repo</distribution>
                        </license>
                    </licenses>
                    <scm>
                        <url>git@github.com:SimplyScala/scalatest-embedmongo.git</url>
                        <connection>scm:git:git@github.com:SimplyScala/scalatest-embedmongo.git</connection>
                    </scm>
                    <developers>
                        <developer>
                            <id>ugobourdon</id>
                            <name>bourdon.ugo@gmail.com</name>
                            <url>https://github.com/ubourdon</url>
                        </developer>
                    </developers>
                ),

            publishTo <<= version { v: String =>
                val nexus = "https://oss.sonatype.org/"
                if (v.trim.endsWith("SNAPSHOT")) Some("snapshots" at nexus + "content/repositories/snapshots")
                else                             Some("releases" at nexus + "service/local/staging/deploy/maven2")
            }
        )
    )
}
