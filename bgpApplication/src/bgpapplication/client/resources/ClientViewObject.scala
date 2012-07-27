/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapplication.client.resources

/**
 * The ViewObject implementation of the Client.<br>
 * This ViewObject is based from the NetworkViewObject and uses the Resource Manager to complete itself.
 */
import bgpapi.view._
import bgpapplication.networking.NetworkViewObject

/**
 * Creates a new ViewObject that is build around {@code networkObject}.
 * It is required that {the {@code requirements} are already loaded.
 */
final class ClientViewObject(networkObject: NetworkViewObject) extends ViewObject {
    private val viewDef = ResourceManager.getDefinition(networkObject.definitionID)
    
    override def apply(property: Property) = networkObject.values(property)
    override def definition = viewDef
    override val identifier = networkObject.identifier
    override val viewType = networkObject.viewType
}
