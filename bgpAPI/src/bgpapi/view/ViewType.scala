/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapi.view

/**
 * A ViewType is a definition for a ViewEntity. It used to decide what type 
 * of ViewEntity an ViewEntity is and is used to define the properties of a 
 * ViewEntity.
 */

final class ViewType (private val name: Symbol, val properties: List[Property[_ <: Any]])
extends Serializable{    
  override def toString = name.toString
  override def hashCode = name.hashCode
  override def equals (a: Any) = a match {
    case vt: ViewType => name == vt.name && properties == vt.properties
    case _ => false
  }
}
