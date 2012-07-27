/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapplication.client.resources

// todo: design decisions:
// - move it to another package?

/**
 * The ResourceManager manages the ViewObjects such that they can be retrieved
 * by id here, including the required resources.
 * Currently this is just an id-manager for ViewObjects
 */
import bgpapi.view.ViewDefinition
import bgpapi.view.ViewObject
import bgpapplication.client.view.Renderer
import bgpapplication.util.Debug

object ResourceManager {
    
    private val debug = new Debug("ResourceManager")
    
    private var objects = Map.empty[String, ViewObject]
    private var definitions = Map.empty[String, ViewDefinition]
    
    /**
     * Gets the Object with {@code id} as identifier.
     * @throws NoSuchElement if there is no Object with {@code id} registered.
     */
    def getObject(id: String) = objects(id)
    /**
     * Gets the Definition with {@code id} as identifier.
     * @throws NoSuchElement if there is no Definition with {@code id} registered.
     */
    def getDefinition(id: String) = definitions(id)
    
    /**
     * Saves {@code obj} to this ResourceManager.<br>
     * {@code obj} must be useable for {@code Renderer} otherwise an exception 
     * will be thrown. And the Definition of {@code obj} must already be loaded.
     */
    def saveResource(obj: ViewObject) = {
        require(Renderer.test(obj), "ViewObject " + obj.identifier + " is wrongly formatted!")
        
        debug("Adding " + obj)
        
        objects += (obj.identifier -> obj)
    }
    
    def saveResource(definition: ViewDefinition) = {
        definitions += (definition.identifier -> definition)
    }

}
