name := "vchat"
 
version := "1.0" 
      
lazy val `vchat` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
scalaVersion := "2.11.11"

resolvers += "local maven" at Path.userHome.asFile.toURI.toURL + ".m2/repository"

libraryDependencies ++= Seq( jdbc , cache , ws , specs2 % Test )
libraryDependencies ++= Seq(
  "org.atmosphere" % "atmosphere-play" % "2.3.1-SNAPSHOT",
  "com.typesafe.play" %% "play-java" % "2.5.18",
  "com.typesafe.play" % "play-enhancer" % "1.2.2"

)


      