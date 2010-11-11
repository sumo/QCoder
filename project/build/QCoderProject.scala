import sbt._
import de.element34.sbteclipsify._
import uk.co.rajaconsulting.sbtjna._


class QCoderProject(info: ProjectInfo) extends DefaultProject(info) with Eclipsify with JNAeratorProject {
	override def libraries = Map(
			"avformat"-> List("libavformat/avformat.h"),
			"avcodec"-> List("libavcodec/avcodec.h"),
			"avutil"-> List("libavutil/avutil.h"))               
	override def includePaths = List("src/main/headers") 
  val scalatest = "org.scalatest" % "scalatest" % "1.2" % "test"
}
