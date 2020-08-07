name := "simple3d"

version := "0.1"

scalaVersion := "2.12.10"

libraryDependencies ++= Seq (
  "com.badlogicgames.gdx" % "gdx-backend-lwjgl" % "1.9.11",
  "com.badlogicgames.gdx" % "gdx-platform" % "1.9.11" classifier "natives-desktop",
)
