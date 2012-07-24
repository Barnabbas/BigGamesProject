/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapplication.client.view


import bgpapi.view._
import bgpapplication.util.Debug
import java.util.Scanner
import scala.actors.Actor
import scala.collection.mutable.HashSet
import scala.swing.BoxPanel
import scala.swing.Orientation
import scala.swing.Panel

// todo: design decisions
// - make it more immutable

/**
 * This is the class that will display everything to the client.<br>
 * It is controlled by adding and removing {@link Entity} instances from it.
 */
class ClientView {
    
    private val debug = new Debug("ClientView")
    
    private val entities = new HashSet[Entity]
    
    def add(entity: Entity) = {
        entities += entity
        debug("added " + entity)
    }
    def remove(entity: Entity) = entities -= entity
    
    /**
     * Runs all data into the console.<br>
     * This is just for testing, will be removed very soon.
     */
    @deprecated
    def run() = {
        debug("running...")
        for(entity <- entities){
            entity.display
        }
    }
}
