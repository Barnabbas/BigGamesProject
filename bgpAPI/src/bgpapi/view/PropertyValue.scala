/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapi.view

/**
 * The Value of a Property. This is the value that is filled in at a property 
 * at the ViewObject classes.<br>
 * A Property Value can either be the data for the value or a pointer to a variable.
 */
sealed trait PropertyValue extends Serializable

object PropertyValue{
    
    /**
     * A PropertyValue containing raw Data.
     * The value is immediatly the Data.
     */
    case class Data(data: Any) extends PropertyValue
    
    /**
     * A PropertyValue pointing to a variable.
     * The value is found in the Entity at the property {@code variable}.
     */
    case class Variable(variable: Property) extends PropertyValue
}
