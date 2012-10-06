/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapplication.client.view

/**
 * A Renderer is used to display ViewObjects
 */
import bgpapi.view._
import bgpapplication.ViewTypes
import bgpapplication.util.Debug
import bgpapi.properties.PropertyDefiner
import bgpapi.properties.Setting

trait Renderer {

  /**
   * Displays this Renderer with additional data from {@code entity}
   */
  def display(entity: Entity)

}

object Renderer {

  private val debug = Debug("Renderer")

  private val renderers = Map[PropertyDefiner[Setting], RendererFactory](ViewTypes.square -> SquareRenderer)
  debug("Renderers for: " + renderers.keySet)

  /**
   * Creates a Renderer for {@code viewObject}.
   * @param viewObject the ViewObject to get a Renderer of.
   */
  def apply(viewObject: ViewObject) = {
    val vType = viewObject.definer
    renderers(vType).apply(viewObject)
  }

  /**
   * Test whether {@code viewObject} can be used to create a Renderer
   */
  def test(viewObject: ViewObject): Boolean = {
    val vType = viewObject.definer
    renderers isDefinedAt vType
  }

  
  /* --- RendererFactory stuff  --- */

  private trait RendererFactory {
    def apply(obj: ViewObject): Renderer
  }
  

  /**
   * This will create Renderers to draw square. (... just for testing)
   */
  private object SquareRenderer extends RendererFactory {
    override def apply(obj: ViewObject) = new Renderer() {
      override def display(entity: Entity) = {
        import org.lwjgl.opengl.GL11._

        val size: Float = entity(ViewTypes.square.size) match {
          case f: Float => f
          case a => throw new IllegalArgumentException(a + " must be a float")
        }
        
        // set the color of the quad (R,G,B,A)
        glColor3f(0.5f, 0.5f, 1.0f);

        // draw quad
        glBegin(GL_QUADS);
        glVertex2f(100, 100);
        glVertex2f(100 + size, 100);
        glVertex2f(100 + size, 100 + size);
        glVertex2f(100, 100 + size);
        glEnd();
      }
    }
  }

}

