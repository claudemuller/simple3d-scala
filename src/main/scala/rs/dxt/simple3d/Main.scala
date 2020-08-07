package rs.dxt.simple3d

import com.badlogic.gdx.backends.lwjgl.{LwjglApplication, LwjglApplicationConfiguration}

object Main extends App {
  val config = new LwjglApplicationConfiguration
  config.title = "Simple 3D Demo"
  config.width = 800
  config.height = 600
  config.forceExit = true
  new LwjglApplication(new Engine, config)
}
