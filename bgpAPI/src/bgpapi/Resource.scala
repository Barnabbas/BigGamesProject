/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapi

/**
 * A Resource is an object that is loaded from outside of the application.<br>
 * Those are loaded by the Game developers, for example the ViewObjects and such.
 * Those Resources can be used to be streamed to the Clients.
 */
trait Resource extends Immutable {
    
    /**
     * An unique name for the Resource.<br>
     * Should be of the format {@code authorName.resourceName} or 
     * {@code authorName.gameName.resourceName} (based on your own preference)
     */
    val identifier: String
    
    /**
     * The identifiers of the Resources this Resource needs.
     */
    def requirements: List[String]
    
    override def toString = identifier
}
