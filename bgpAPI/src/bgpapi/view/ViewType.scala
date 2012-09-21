/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapi.view

import bgpapi.properties.PropertyDefiner
import bgpapi.properties.Setting

/**
 * A ViewType is a definition for a ViewEntity. It used to decide what type
 * of ViewEntity an ViewEntity is and is used to define the properties of a
 * ViewEntity.
 */

final class ViewType(private val name: Symbol, private val settings: Set[Setting[_ <: Any]])
		extends Serializable with PropertyDefiner[Setting[_]] {
	
	/** A Map between the names of the settings to the settings themself */
	private val setMap = settings.map(s => s.name -> s).toMap[String, Setting[_]]
	
	override def property(name: String) = setMap(name)
	override def properties = settings

	override def toString = name.toString
	override def hashCode = name.hashCode
	override def equals(a: Any) = a match {
		case vt: ViewType => name == vt.name && settings == vt.settings
		case _ => false
	}
}
