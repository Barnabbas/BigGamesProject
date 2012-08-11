/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapplication.server.networking

import bgpapi.Resource
import bgpapi.view.ViewDefinition
import bgpapi.view.ViewObject
import bgpapplication.networking.NetworkViewObject
import bgpapplication.util.Debug
import scala.actors.Actor
import Client._
import scala.collection.mutable.HashMap

/**
 * This class will load Resources to all the clients.<br>
 * The clients can be added later on.<br>
 * At the moment the ResourceLoader will only load ViewObjects to the clients.
 */

/**
 * Constructs a new ResourceLoader that will load {@code res} to all 
 * clients that are added to this ResourceLoader
 */
private[networking] class ResourceNetworker(loader: ResourceLoader){
    
    private val debug = new Debug("ResourceLoader")
    debug("Getting resources " + loader)
    
    
    /**
     * The resources in order to send
     */
    private val resources = loader.requiredResources
    
    /**
     * The data to send (in order)
     */
    private val sendData = {
        for (res <- resources) yield res match{
            case vObj: ViewObject => NetworkViewObject(vObj)
            case vDef: ViewDefinition => new ResourceNetworker.AppViewDefinition(vDef)
            case red => res
         }
    }
    
    debug("Will be sending: " + sendData)
    
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
            sendData.foreach(d => {client ! d; debug("sending " + d)})
            client ! Finish
        }
    }
    
    /**
     * Determines whether this ResourceLoader is still busy loading some resources
     * to a client.
     */
    def isBusy: Boolean = 
        !actors.forall(actor => actor.getState == Actor.State.Terminated)
    
    /**
     * Gives a list that is a topolical sort of the Reources in {@code resources}
     */
    def topologicalSort(resources: Map[String, Resource]) = {
        
        // todo: make real topological sort
        // (this will propably be easier when we have on file to load all
        
        resources.values.filter(r => r.isInstanceOf[ViewDefinition]) ++
                resources.values.filter(r => r.isInstanceOf[ViewObject])
    }
    
}

object ResourceNetworker {
    
    /**
     * temporary class to get a ViewDefinition implementation
     */
    private class AppViewDefinition(definition: ViewDefinition) extends ViewDefinition {
        override val identifier = definition.identifier
        override val variables = definition.variables
    }
}
