/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapplication.util.lwjgl

/**
 * The Display is used to start a window with LWJGL rendering.
 * It has to be passed a function that renders the content in openGL.
 * Warning: The Display runs in a different thread, so make sure that 
 * {@code body} can handle multithreading if needed.
 */
import org.lwjgl.opengl.DisplayMode
import org.lwjgl.opengl.{Display => glDisplay}
import org.lwjgl.opengl.GL11._
import scala.actors.Actor


object BgpDisplay{
  
  def apply(title: String)(body: => Unit) = Actor.actor{
        
    glDisplay.setDisplayMode(new DisplayMode(800, 600));
    glDisplay.create();
    glDisplay.setTitle(title)
            
            
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    glOrtho(0, 800, 0, 600, 1, -1);
    glMatrixMode(GL_MODELVIEW);
            
    while (!glDisplay.isCloseRequested()) {
			
                
      // Clear the screen and depth buffer
      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);	
		
      // running the given code
      body
			
      glDisplay.update();
    }
		
    glDisplay.destroy();
  }
}
