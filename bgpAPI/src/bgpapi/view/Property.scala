/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapi.view

// todo: design decisions:
// - add a default value?

/**
 * A Property is used to retrieve data from ViewObjects and for ViewEntities to 
 * set or get settings from.
 * 
 *  @param name the name of this proeprty 
 */
final class Property[T] (val name: Symbol)
    extends Serializable {
    
  private[view] def this(name: String) = this(Symbol(name))
    
  override def toString = name.toString
    
  /**
   * The name of this Property as String
   */
  def stringValue = name.name

}

object Property{
  /**
   * A PropertyTupe is a Tuple such that the type of the property is the type 
   * of the value.
   */
  type PropertyTuple[T] = (Property[T], T)
}
