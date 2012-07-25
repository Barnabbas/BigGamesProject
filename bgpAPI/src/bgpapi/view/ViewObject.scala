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

trait ViewObject extends Serializable {
    
    /**
     * The type of this Object. This is used to tell the view how this Object
     * should be displayed.
     */
    val viewType: ViewType
    
    /**
     * An unique name for this ViewObject, it should be formated like: 
     * {@code authorName.objectName}
     */
    val identifier: String
    
    /**
     * The variables that can be changed for this ViewObject.<br>
     * Variables are the Properties that a ViewEntity that is created from this
     * ViewObject will have. Those can be changed such the same entity will be 
     * drawn different.
     */
    val variables: Set[Property]
    
    /**
     * The Values of the Properties of this ViewObject.<br>
     * Those are the description of how this ViewObject will look like. 
     * Note that it is required to have a value for all properties in 
     * {@code viewType.allTypes}.
     */
    val values: Map[Property, Any]
    
    /**
     * Gets the value of the Property {@code property} or an empty option if 
     * this ViewObject does not have this Property.
     */
    def apply(property: Property) = values.get(property)
    
    override def toString = identifier

}
