import sbt._
import de.element34.sbteclipsify._
import uk.co.rajaconsulting.sbtjna._

class QCoderProject(info: ProjectInfo) extends DefaultProject(info) with Eclipsify with JNAeratorProject {
  override def libraries = List(
    "avutil" -> List("libavutil/avutil.h", "libavutil/common.h", "libavutil/rational.h", "libavutil/error.h", "libavcodec/opt.h"),
    "avformat" -> List("libavformat/avformat.h", "libavutil/avutil.h", "libavformat/avio.h"),
    "avcodec" -> List("libavutil/avutil.h", "libavcodec/opt.h", "libavcodec/avcodec.h"))

  /**
   * Include path for ubuntu is
   * export JNAERATOR_INCLUDE_PATH=/usr/include/linux/:/usr/include/gcc/x86_64-linux-gnu/4.4/include/:/usr/include/:/usr/lib/gcc/x86_64-linux-gnu/4.4/include
   */
  override def includePaths = List("src/main/headers", "src/main/headers/libavutil", "src/main/headers/libavcodec",
    "src/main/headers/libavformat")
  override lazy val verbose = true
  override lazy val scalaOut = false
  override lazy val runtime = JNAeratorRuntime.JNAerator

  val scalatest = "org.scalatest" % "scalatest" % "1.2" % "test"
  val sl4j = "org.clapper" %% "grizzled-slf4j" % "0.5"
  //val sl4jSimple = "org.slf4j" % "slf4j-simple" % "1.6.1"
  val sl4jLog4j = "org.slf4j" % "slf4j-log4j12" % "1.6.1"
  val junit = "junit" % "junit" % "4.7"
  override val jnaerator = "com.jnaerator" % "jnaerator" % "0.9.7"

  val mavenLocal = "Local Maven Repository" at "file://" + Path.userHome + "/.m2/repository"
}
