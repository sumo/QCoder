import sbt._
import de.element34.sbteclipsify._
import uk.co.rajaconsulting.sbtjna._

class QCoderProject(info: ProjectInfo) extends DefaultProject(info) with Eclipsify with JNAeratorProject {
  override def libraries = List(
    "avformat" -> List("libavformat/avformat.h", "libavformat/avio.h"),
    "avcodec" -> List("libavcodec/avcodec.h"),
    "avutil" -> List("libavutil/avutil.h", "libavutil/rational.h"))

  /**
   * Include path for ubuntu is
   * export JNAERATOR_INCLUDE_PATH=/usr/include/linux/:/usr/include/gcc/x86_64-linux-gnu/4.4/include/:/usr/include/:/usr/lib/gcc/x86_64-linux-gnu/4.4/include
   */
  override def includePaths = List("src/main/headers")
  override lazy val verbose = true
  //override lazy val runtime = JNAeratorRuntime.JNA
  val scalatest = "org.scalatest" % "scalatest" % "1.2" % "test"
  val sl4j = "org.clapper" %% "grizzled-slf4j" % "0.4"
  val sl4jSimple = "org.slf4j" % "slf4j-simple" % "1.6.1"
  val bridj = "com.nativelibs4java" % "bridj" % "0.4"

  override def ivyXML =
    <dependencies>
      <dependency org="org.clapper" name="grizzled-slf4j_2.8.1" rev="0.4">
        <exclude module="slf4j-jdk14"/>
      </dependency>
    </dependencies>
}
