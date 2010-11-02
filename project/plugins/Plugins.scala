import sbt._
class QCoderPlugins(info: ProjectInfo) extends PluginDefinition(info) {
	
  val sumogitHub = "sumo.github.com" at "http://sumo.github.com/maven"
  val eclipse = "de.element34" % "sbt-eclipsify" % "0.6.0"
  val jna = "raja-consulting.co.uk" % "sbt-jna-plugin" % "0.2"
}