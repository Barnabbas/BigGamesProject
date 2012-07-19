/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapi.view

// todo: design decisions:
// - add a type for each property
// - add a default value?
// - add a pool of properties

/**
 * A Property is used to retrieve data from ViewObjects and for ViewEntities to 
 * set or get settings from.
 * 
 *  @param name the name of this proeprty 
 */
final class Property private[view] (val name: Symbol) {
    
    private[view] def this(name: String) = this(Symbol(name))
    
    override def equals (a: Any) = 
        a.isInstanceOf[Property] && a.asInstanceOf[Property].name == name
    
    override def hashCode = name.hashCode
    
    override def toString = name.toString

}

object Property {
    def apply(name: Symbol) = new Property(name)
    def apply(name: String): Property = apply(Symbol(name))
    
    implicit def symbolToProperty(symbol: Symbol) = Property(symbol)
}
