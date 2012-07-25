/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapplication.server.networking

import bgpapi.view.ViewObject
import bgpapplication.util.AppViewObject
import scala.actors.Actor
import Client._

/**
 * This class will load Resources to all the clients.<br>
 * The clients can be added later on.<br>
 * At the moment the ResourceLoader will only load ViewObjects to the clients.
 */

/**
 * Constructs a new ResourceLoader that will load {@code viewObjects} to all 
 * clients that are added to this ResourceLoader
 */
private[networking] class ResourceLoader(viewObjects: List[ViewObject]){
    
    /**
     * Using AppViewObjects to be sure the client can read them.<br>
     * (sometimes you got strange implementations from the client.)
     */
    private val appViewObjects = viewObjects map (vo => new AppViewObject(vo))
    
    /**
     * The clients that are added
     */
    private var actors = List.empty[Actor]
    
    /**
     * Adds {@code client} such that it will also receive all the viewObjects.
     */
    def addClient(client: Client) = {
        import bgpapplication.networking.Message.load._
        actors ::= Actor.actor{
            client ! Start
            appViewObjects.foreach(vo => client ! vo)
            client ! Finish
        }
    }
    
    /**
     * Determines whether this ResourceLoader is still busy loading some resources
     * to a client.
     */
    def isBusy: Boolean = 
        !actors.forall(actor => actor.getState == Actor.State.Terminated)
    
    
}
