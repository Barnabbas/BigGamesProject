/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapplication.client.view


import bgpapi.view._
import java.util.Scanner
import scala.actors.Actor
import scala.collection.mutable.HashSet

// todo: design decisions
// - make it more immutable

/**
 * This is the class that will display everything to the client.<br>
 * It is controlled by adding and removing {@link Entity} instances from it.
 */
class ClientView {
    
    private val entities = new HashSet[Entity]
    
    // starting a deamon that will respond on "view" in the console
    private val actor = new Actor{
        override def act = {
            val scanner = new Scanner(System.in)
            while (scanner.hasNext()){
                if (scanner.nextLine == "view"){
                    for (entity <- entities){
                        entity.display
                    }
                }
            }
        }
    }
    actor.start()
    
    def add(entity: Entity) = entities += entity
    def remove(entity: Entity) = entities -= entity
}
