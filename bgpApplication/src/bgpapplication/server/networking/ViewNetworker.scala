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
import bgpapplication.util.Debug
import scala.actors.Actor
import scala.collection.mutable.HashMap
import Client._

/**
 * A new Networker that will send all data to {@code clients}
 */
private[networking] class ViewNetworker(clients: List[Client]) extends View {
    
    /**
     * A broadcaster used to send messages (such that we can send FIFO, I hope).
     * Don't know if this one can still get blocked...
     */
    private val broadcaster = new Actor(){
        override def act() = react{
            case m: Message => {
                    clients.foreach(c => c ! m)
                    debug("Broadcasting " + m)
                    act()
                }
            case x => 
                throw new IllegalArgumentException("Broadcaster found unknow message: " + x)
        }
    }.start()
    
    // telling all clients to start
    broadcast(Message.StartGame)
    
    /**
     * A debugger used for testing (can better be placed in the companion object...)
     */
    private val debug = new Debug("ViewNetworker")
    
    /**
     * The entities that are created by this class
     */
    private var entities = new HashMap[ViewEntity, Int]
    
    override def createEntity(viewObject: ViewObject, variables: (Property, Any)*): ViewEntity = {
        val varMap = variables.toMap
        val entity = new ServerViewEntity(viewObject, varMap)
        val id = genEntityID()
        synchronized{entities += (entity -> id)}
        
        broadcast(Create(id, viewObject.identifier, varMap))
        
        return entity
    }
    
    override def add(viewEntity: ViewEntity) = {
        val id = entities(viewEntity)
        broadcast(Add(id))
    }
    
    override def remove(viewEntity: ViewEntity) = {
        val id = entities(viewEntity)
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
        broadcaster ! message
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
    private class ServerViewEntity(viewObject: ViewObject, vars: Map[Property, Any])
    extends ViewEntity(viewObject: ViewObject, vars) {
        override def onUpdate(p: Property, v: Any) = updateEntity(this, p, v)
    }

}
