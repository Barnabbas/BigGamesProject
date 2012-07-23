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

object ResourceManager {
    
    private var objects = Map.empty[String, ViewObject]
    
    def getObject(id: String) = objects(id)
    
    def saveResource(obj: ViewObject) = {
        objects += (obj.identifier -> obj)
    }

}
