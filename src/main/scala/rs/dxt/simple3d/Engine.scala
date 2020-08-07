package rs.dxt.simple3d

import com.badlogic.gdx.graphics.VertexAttributes.Usage
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight
import com.badlogic.gdx.graphics.g3d.{Environment, Material, Model, ModelBatch, ModelInstance}
import com.badlogic.gdx.graphics.g3d.utils.{CameraInputController, ModelBuilder}
import com.badlogic.gdx.graphics.{Color, GL20, PerspectiveCamera}
import com.badlogic.gdx.{ApplicationListener, Gdx}

class Engine extends ApplicationListener {
  var environment: Environment = _
  var cam: PerspectiveCamera = _
  var modelBatch: ModelBatch = _
  var model: Model = _
  var instance: ModelInstance = _
  var camController: CameraInputController = _

  override def create(): Unit = {
    environment = new Environment
    // Set the ambient light (0.4, 0.4, 0.4); alpha is ignored.
    environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f))
    // Add a DirectionalLight with the color of (0.8, 0.8, 0.8) and the direction of (-1.0, -0.8f, 0.2).
    environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, 0.2f))
    // ModelBatch is responsible for rendering.
    modelBatch = new ModelBatch
    // Field of view of 67 degrees.
    // Aspect ratio set to current width x height.
    cam = new PerspectiveCamera(67, Gdx.graphics.getWidth, Gdx.graphics.getHeight)
    // Set camera: 10 units to the right; 10 units up; and 10 units back.
    // The Z axis is pointing towards the viewer, so for the viewer, a positive Z value of camera
    //   is moving the viewer back (along Z).
    cam.position.set(10f, 10f, 10f)
    // Set camera to look at 0, 0, 0.
    cam.lookAt(0, 0, 0)
    // The near clipping plane distance.
    cam.near = 1f
    // The far clipping plane distance.
    cam.far = 300f
    // Update camera so that changes take effect.
    cam.update()

    showBox()

    camController = new CameraInputController(cam)
    Gdx.input.setInputProcessor(camController)
  }

  override def render(): Unit = {
    camController.update()

    Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth, Gdx.graphics.getHeight)
    // Clear the screen.
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT)

    modelBatch.begin(cam)
    // Render the ModelInstance
    modelBatch.render(instance, environment)
    // Finish the rendering.
    modelBatch.end()
  }

  override def resize(width: Int, height: Int): Unit = {}

  override def pause(): Unit = {}

  override def resume(): Unit = {}

  override def dispose(): Unit = {
    modelBatch.dispose()
    model.dispose()
  }

  private def showBox(): Unit = {
    // ModelBuilder is used to create models in code.
    val modelBuilder: ModelBuilder = new ModelBuilder
    // Create box with a side of 5, 5, 5.
    // Add a material with a green diffuse colour.
    // Add position and normal components to it.
    //   at the very least, Position is always needed when creating a model.
    // Normals are used here so that lighting works correctly.
    // Usage is a subclass of VertexAttributes.
    // A model contains everything on what to render and it keeps track of the resources.
    model = modelBuilder.createBox(5f, 5f, 5f,
      new Material(ColorAttribute.createDiffuse(Color.GREEN)),
      Usage.Position | Usage.Normal)
    // The ModelInstance understands where to render the model. By default that's at 0, 0, 0.
    instance = new ModelInstance(model)
  }
}
