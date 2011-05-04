import sbt._
class QCoderPlugins(info: ProjectInfo) extends PluginDefinition(info) {
	                                                                        
  val jnaeratorRepo = "JNAerator Maven Repository" at "http://jnaerator.sourceforge.net/maven"
  val nl4jRepo = "NativeLibs4Java" at "http://nativelibs4java.sourceforge.net/maven"
  val javaNetRepo = "Java.net Repository for Maven" at "http://download.java.net/maven/2"
  //val sumogitHub = "sumo.github.com" at "http://sumo.github.com/maven-repo"
  val local = Resolver.file("local", new java.io.File("../sumo.github.com/maven-repo/")) transactional()
  val eclipse = "de.element34" % "sbt-eclipsify" % "0.6.0"
  val jna = "raja-consulting.co.uk" % "sbt-jna-plugin" % "0.6"
  val jnaerator = "com.jnaerator" % "jnaerator" % "0.9.7"
}
