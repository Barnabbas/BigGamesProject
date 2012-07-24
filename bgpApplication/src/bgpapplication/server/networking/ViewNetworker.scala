/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapplication.server.networking

/**
 * The Networker used for the View
 */
import bgpapi.view._
import bgpapplication.networking.Message
import bgpapplication.networking.Message.view._
import scala.actors.Actor
import scala.collection.mutable.HashMap
import Client._

/**
 * A new Networker that will send all data to {@code clients}
 */
private[networking] class ViewNetworker(clients: List[Client]) extends View {
    
    // telling all clients to start
    Actor.actor(clients.foreach(c => c ! Message.StartGame))
    
    /**
     * The entities that are created by this class
     */
    private var entities = new HashMap[ViewEntity, Int]
    
    override def add(viewObject: ViewObject): ViewEntity = {
        val entity = new ServerViewEntity(viewObject)
        val id = genEntityID()
        synchronized{entities += (entity -> id)}
        broadcast(Add(id, viewObject.identifier))
        
        return entity
    }
    
    override def remove(viewEntity: ViewEntity) = {
        val id = entities(viewEntity)
        synchronized{entities -= viewEntity}
        broadcast(Remove(id))
    }
    
    /**
     * Sets the Property {@code prop} of {@code viewEntity} to {@code value} for
     * all clients. This will not modify {@code viewEntity} itself
     */
    private def updateEntity(viewEntity: ViewEntity,
                                            prop: Property, value: Any) {
        val id = entities(viewEntity)
        broadcast(Change(id, prop, value))
    }
    
    /**
     * Sends {@code message} to all Actors
     */
    private def broadcast(message: Message) = {
        Actor.actor{
            for (client <- clients){
                client ! message
            }
        }
    }
    
    private var id = Integer.MIN_VALUE
    /**
     * Generates a new entity ID
     */
    private def genEntityID() = {
        // just a simple function that keeps counting up
        
        assume(id < Integer.MAX_VALUE)
        
        id += 1
        id
    }
    
    /**
     * The ViewEntity implementation for the Server
     */
    private class ServerViewEntity(viewObject: ViewObject) extends ViewEntity(viewObject: ViewObject) {
        override def onUpdate(p: Property, v: Any) = updateEntity(this, p, v)
    }

}
