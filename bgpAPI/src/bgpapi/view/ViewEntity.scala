/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapi.view

import bgpapi.properties.PropertyHolder
import bgpapi.properties.Variable

/**
 * A ViewEntity is an "instance" of an ViewObject that is added to the View.
 * It can be used to set and get properties from, such that the View will be changed
 * for this certain entity.
 */
trait ViewEntity extends PropertyHolder[Variable[_], Variable.Value[_]] {
    
    /**
     * Sets the value of {@code p} to {@code v}.<br>
     * You can only set values of properties in {@code properties}.
     * @param p the Property to set the value of
     * @param v the new value
     */
    def update[T](p: Variable[T], v: Variable.Value[T]): Unit
    
}
