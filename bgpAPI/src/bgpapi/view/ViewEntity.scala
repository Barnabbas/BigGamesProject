/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapi.view

import scala.collection.mutable.HashMap

// todo: design decisions:
// - should this be immutable?
// - make a seperate property class, to make it java friendly
// - should it be abstract

/**
 * A ViewEntity is an "instance" of an ViewObject that is added to the View.
 * It can be used to set and get properties from, such that the View will be changed
 * 
 */

/**
 * A New ViewEntity that can be used to control an instance of {@code viewObject}.
 * @param viewObject the ViewObject this Entity will be an instance of
 * @param initVariables the values for the variables to start with
 * @throws IllegalArgumentException if {@code initVariables} is not defined for
 * all variables required by {@code viewObject}.
 */
class ViewEntity(viewObject: ViewObject, initVariables: Map[Property[_ <: Any], Any]) {
    
    // checking for valid variables
    checkVariables();
    
    /**
     * A Map between the properties and the values
     */
    private val values = new HashMap ++ initVariables
    
    /**
     * Gets the value of property {@code p}
     * @param p the Property to get the value of
     * 
     * @throws NoSuchElementException if there is no value for {@code property}
     */
    final def apply[T](p: Property[T]): T = values(p) asInstanceOf
    
    /**
     * Sets the value of {@code p} to {@code v}.<br>
     * You can only set values of properties in {@code properties}.
     * @param p the Property to set the value of
     * @param v the new value
     */
    final def update[T](p: Property[T], v: T) = {
        require(properties(p), "This Entity got no " + p + " property")
        values(p) = v
        onUpdate(p, v)
    }
    
    /**
     * The Properties that can have value set, for this Entity
     */
    final val properties = viewObject.definition.variables
    
    /**
     * Will be called when a property value got changed.<br>
     * Can be overriden to be notified when this ViewEntity is updated
     */
    protected def onUpdate[T](p: Property[T], v: T) = {
        // to override
    }
    
    /**
     * Checks if all variables of the ViewObject has been set.<br>
     * Checked using the ViewDefinition currently.
     * This will throw a IllegalArgumentException when not all variables are available
     * @throws IllegalArgumentException if not all the required variables are included
     */
    private def checkVariables() = {
        val isComplete = {
            viewObject.definition.variables.forall{v =>
                initVariables.isDefinedAt(v)
            }
        }
        require(isComplete, "Not all variables are set in " + viewObject + 
                "\nmissing: " + (viewObject.definition.variables &~ initVariables.keySet))
    }
    
}
