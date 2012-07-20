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
trait ViewObject {
    
    /**
     * The type of this Object. This is used to tell the view how this Object
     * should be displayed.
     */
    val viewType: ViewType
    
    /**
     * Gets the value of the Property {@code property} or an empty option if 
     * this ViewObject does not have this Property.
     */
    def apply(property: Property): Option[Any]

}
