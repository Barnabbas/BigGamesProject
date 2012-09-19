/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapi.view

/**
 * A ViewEntity is an "instance" of an ViewObject that is added to the View.
 * It can be used to set and get properties from, such that the View will be changed
 * for this certain entity.
 */
trait ViewEntity {
    
    /**
     * Gets the value of property {@code p}
     * @param p the Property to get the value of
     * 
     * @throws NoSuchElementException if there is no value for {@code property}
     */
    def apply[T](p: Property[T]): T
    
    /**
     * Sets the value of {@code p} to {@code v}.<br>
     * You can only set values of properties in {@code properties}.
     * @param p the Property to set the value of
     * @param v the new value
     */
    def update[T](p: Property[T], v: T)
    
    /**
     * The Properties that can have value set, for this Entity
     */
    val properties: Set[Property[_]]
    
    
}
