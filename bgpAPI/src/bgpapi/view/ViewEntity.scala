/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapi.view

import scala.collection.mutable.Map

// todo: design decisions:
// - should this be immutable?
// - make a seperate property class, to make it java friendly
// - use viewTypes? or make it more scalable by assuming that user knows the properties

/**
 * A ViewEntity is an "instance" of an ViewObject that is added to the View.
 * It can be used to set and get properties from, such that the View will be changed
 * 
 */
abstract class ViewEntity private[view](viewType: ViewType) {
    
    /**
     * A Map between the properties and the values
     */
    private val values = Map.empty[Property, Any]
    
    /**
     * Gets the value of property {@code p} or gets nothing when it is not set
     * @param p the Property to get the value of
     */
    def apply(p: Property) = values.get(p)
    
    /**
     * Sets the value of {@code p} to {@code v}.<br>
     * You can only set values of properties in {@code properties}.
     * @param p the Property to set the value of
     * @param v the new value
     */
    def update(p: Property, v: Any) = {
        require(viewType.properties.contains(p), "This Entity got no " + p + " property")
        values(p) = v
        onUpdate(p, v)
    }
    
    /**
     * The Properties that can have value set, for this Entity
     */
    def properties = viewType.properties
    
    /**
     * Will be called when a property value got changed
     */
    protected def onUpdate(p: Property, v: Any): Unit
    
}
