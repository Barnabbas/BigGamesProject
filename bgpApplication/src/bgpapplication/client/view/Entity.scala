/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapplication.client.view

/**
 * An Entity is a ViewElement that can be displayed by a Renderer.<br>
 * Entities are used by {@link ClientView} to display everything
 */
import bgpapi.view._

/**
 * An Entity that will displayed according to {@code viewObject} and {@code viewEntity}.
 * @param viewObject how this Entity should be displayed
 * @param ViewEntity additional properties that can be set during run-time
 */
class Entity(viewObject: ViewObject) {
    
    /**
     * the ViewEntity containing the properties for this Entity
     */
    // todo: use real viewEntities
    val viewEntity = new ViewEntity(viewObject)
    
    private val renderer = Renderer.get(viewObject.viewType)
    
    require(renderer.test(viewObject), "Can not display the ViewObject")
    require(renderer.test(viewEntity), "Can not display the ViewEntity")
    
    /**
     * Displays this Entity
     */
    def display = renderer.display(viewObject, viewEntity)
}
