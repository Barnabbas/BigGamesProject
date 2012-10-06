/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapplication.networking

import bgpapi.properties.Variable
import bgpapplication.util.PropertyMap._

sealed class Message

object Message{
    
    /**
     * Messages used for the registering process of the client to a server
     */
    object register{
        
        /**
         * Message used to indicate that a client wants to connect.<br>
         * After a request to a server a reply must be send immediatly (either
         * the accept or deny Message)
         * @param name the name the client wants to use
         */
        case class Request(name: String) extends Message
        /**
         * Message used to indicate that a client request is accepted
         */
        case object Accept extends Message
        
        /**
         * Message used to indicate that a client request is accepted
         * @param reason the reason of the deny
         */
        case class Deny(reason: String) extends Message
    }
    
    /**
     * Messages used to start and load a Game
     */
    object load{
        
        /**
         * Message indicating that the loading process will be started
         */
        case object Start extends Message
        
        /**
         * Message indicating that the loading process is finished
         */
        case object Finish extends Message
        
    }
    
    /**
     * A Message indicating that a Game can be started.
     */
    case object StartGame extends Message
    
    /**
     * Messages to change the view using ViewEntities
     */
    object view{
        
        /**
         * message used to inform the client that a new ViewEntity should be added
         * @param id the id of the Entity to add
         */
        case class Add(id: Int) extends Message

        /**
         * message used to inform the client that a new ViewEntity should be removed
         * @param id the ID of the Entity to remove
         */
        case class Remove(id: Int) extends Message
        
        /**
         * Message indicating that a new Entity has to be created with the given
         * variables set at start.
         * @param id the identifier for this new Entity
         * @param objectID the identifier of the Object this Entity is build on
         * @param variables the values of the variables to start with
         */
        case class Create(id: Int, objectID: String, variables: VariableMap) extends Message

        /**
         * message indicating that a property of a ViewEntity should be changed.
         * @param id the identifier of the Entity to change
         * @param prop the property that should be changed
         * @param value the new value for the property
         */
        case class Change(id: Int, prop: Variable, value: Variable.Value) extends Message
        
        // todo: handles errors like unknow id, or unknow viewObject id
    }
    
    
}
