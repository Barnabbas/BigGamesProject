/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapplication.client.networking

import bgpapplication.client.resources.ResourceManager
import bgpapplication.client.view.ClientView
import bgpapplication.client.view.Entity
import bgpapplication.util.Debug
import scala.actors.AbstractActor
import scala.actors.Actor
import scala.collection.mutable.HashMap

/**
 * An Actor that will manage the communication with {@code server} and {@code view}.<br>
 * This class will only handle the Message.View messages.
 */
private[networking] class ViewActor(view: ClientView, server: AbstractActor) extends Actor {
    
    val debug = new Debug("ViewActor")
    
    val entities = new HashMap[Int, Entity]
    
    override def act = {
        import bgpapplication.networking.Message.view._
        
        loop {
            receive{
                case Add(id, obj) => {
                        debug("new object added: " + obj)
                        val entity = new Entity(ResourceManager.getObject(obj))
                        entities += (id -> entity)
                        view.add(entity)
                }
                    
                case Remove(id) => {
                        val entity = entities(id)
                        view.remove(entity)
                        entities -= id
                }
                case Change(id, prop, value) => {
                        val entity = entities(id)
                        entity.viewEntity(prop) = value
                }
            }
        }
        
    }

}
