import sbt._
import de.element34.sbteclipsify._
import uk.co.rajaconsulting.sbtjna._


class QCoderProject(info: ProjectInfo) extends DefaultProject(info) with Eclipsify with JNAeratorProject {
	override def libraries = Map(
			"libavformat"-> List("libavformat/avformat.h"))
}
