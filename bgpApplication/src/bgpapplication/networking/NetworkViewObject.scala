/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapplication.networking

/**
 * This class is used to send the information about the ViewObjects over the 
 * Network.
 */
import bgpapi.view.Property
import bgpapi.view.PropertyValue
import bgpapi.view.ViewObject
import bgpapi.view.ViewType

final class NetworkViewObject(val identifier: String,
                              val definitionID: String,
                              val viewType: ViewType,
                              val values: Map[Property, Option[PropertyValue]])
    extends Serializable

object NetworkViewObject{
    def apply(obj: ViewObject) = {
        val id = obj.identifier
        val defId = obj.definition.identifier
        val vt = obj.viewType
        val values = 
            (for(prop <- obj.viewType.properties) yield (prop -> obj(prop))).toMap
        
        new NetworkViewObject(id, defId, vt, values)
    }
}
