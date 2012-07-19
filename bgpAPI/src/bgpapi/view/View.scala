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
     * Adds a new instance of {code viewType} to this View and will return 
     * the ViewEntity which can be used to control this instance.
     */
    def add(viewType: ViewType): ViewEntity
    
    /**
     * Removes a ViewEntity from this View.
     * It is required that {@code viewEntity} is an Entity that is gotten from
     * this View.
     */
    def remove(viewEntity: ViewEntity)

}
