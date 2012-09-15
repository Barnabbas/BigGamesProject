/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapi.view

/**
 * A View is a collection of ViewObjects and can be used to decide what should 
 * be shown in a view.
 */
trait View {
    
    /**
     * Creates a new instance of {@code viewObject} and return s
     * the ViewEntity which can be used to control this instance.
     * The ViewEntity will have its values set like defined by {@code values}.<br>
     * To make this Entity visible in this View it has to be added through 
     * {@link #add(ViewEntity)}
     * 
     * @param viewObject the ViewObject to create as a new entity to the View
     * @param variables the values that the entity will have at start.
     * @throws IllegalArgumentException if not all variables of the ViewObject 
     * has been set in {@code variables}
     */
    @throws(classOf[IllegalArgumentException])
    def createEntity(viewObject: ViewObject, variables: (Property, Any)*): ViewEntity
    
    /**
     * Adds {@code viewEntity} to this View.<br>
     * It is required that {@code viewENtity} is an Entity that is gotten from
     * this View.
     */
    def add(viewEntity: ViewEntity)
    
    /**
     * Removes a ViewEntity from this View.
     * It is required that {@code viewEntity} is an Entity that is gotten from
     * this View.
     */
    def remove(viewEntity: ViewEntity)

}
