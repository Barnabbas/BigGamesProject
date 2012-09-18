/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapi.view

/**
 * ViewObjects are used to tell the View what a new Entity should look like.<br>
 * ViewObjects are normally retrieved from the filesystem and will be loaded to 
 * the client to make it possible for the client to display items
 */

import bgpapi.Resource

trait ViewObject extends Resource {
    
    /**
     * The type of this Object. This is used to tell the view how this Object
     * should be displayed.
     */
    val viewType: ViewType
    
    /**
     * The Definition that used to define the variables this ViewObject will have
     */
    def definition: ViewDefinition
    
    
    /**
     * Gets the value of the Property {@code property}.
     * @throws NoSuchElementException if there is no value for {@code property}
     */
    def apply[T](property: Property[T]): T
    
    override def requirements = Set(definition)

}
