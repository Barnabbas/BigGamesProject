/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapplication.util

/**
 * An implementation of {@link ViewObject} that is used to wrap other ViewObjects in to 
 * send it over the network.<br>
 * This class is probably temporary and will be removed when we get a real ViewObject
 * reader.
 */
import bgpapi.view.ViewObject

final class AppViewObject(obj: ViewObject) extends ViewObject {
    override val viewType = obj.viewType
    override val identifier = obj.identifier
    override val variables = obj.variables
    override val values = obj.values
}
