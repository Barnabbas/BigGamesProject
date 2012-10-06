/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapplication.client.view


import bgpapi.view._
import bgpapplication.util.Debug
import scala.collection.mutable.HashSet
import bgpapplication.util.lwjgl._
import scala.collection.mutable.SynchronizedSet


// todo: design decisions
// - make it an object

/**
 * This is the class that will display everything to the client.<br>
 * It is controlled by adding and removing {@link Entity} instances from it.
 * Should only have one instance
 */
class ClientView {
    
    private val debug = Debug("ClientView")
    
    /**
     * The entities that are currently added to this View
     */
    private val entities = new HashSet[Entity]() with SynchronizedSet[Entity]
    
    // starting openGL
    display("BigGamesProject!!!"){
        entities.foreach(e => e.display)
    }
    
    def add(entity: Entity) = {
        entities += entity
        debug("added " + entity)
    }
    def remove(entity: Entity) = entities -= entity
    
    /**
     * Runs all data into the console.<br>
     * This is just for testing, will be removed very soon.
     */
    def run() = {
        debug("running...")
        for(entity <- entities){
            entity.display
        }
    }
     
}
