/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapplication.client

// todo: design decisions:
// - move it to another package?

/**
 * The ResourceManager manages the ViewObjects such that they can be retrieved
 * by id here, including the required resources.
 * Currently this is just an id-manager for ViewObjects
 */
import bgpapi.view.ViewObject
import bgpapplication.client.view.Renderer

object ResourceManager {
    
    private var objects = Map.empty[String, ViewObject]
    
    def getObject(id: String) = objects(id)
    
    /**
     * Saves {@code obj} to this ResourceManager.<br>
     * {@code obj} must be useable for {@code Renderer} otherwise an exception 
     * will be thrown.
     */
    def saveResource(obj: ViewObject) = {
        require(Renderer.test(obj), "ViewObject " + obj.identifier + " is wrongly formatted!")
        objects += (obj.identifier -> obj)
    }

}
