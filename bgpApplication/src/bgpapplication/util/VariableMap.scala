/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapi.util.view

/**
 * A map between properties and their values, such that the types of a Property
 * and a value in the same pair is the same.
 */
import bgpapi.view.Property
import bgpapi.view.Property._
import bgpapi.view._

trait PropertyMap{
  
  def apply[T](property: Property[T]): T
  def isDefinedAt(property: Property[_]): Boolean
  def toMap: Map[Property[_], Any]
  def +[T](pair: (Property[T], T)): PropertyMap
  def -[T](property: Property[_]): PropertyMap
  def keySet: Set[Property[_]]
  
}

object PropertyMap {
  def apply(pairs: PropertyTuple[_]*): PropertyMap = {
    val tuples = pairs map (p => propertyTuple2Tuple(p))
    val map = tuples.toMap[Property[_], Any]
    new SimplePropertyMap(map)
  }
  
  // todo: by far not the best way, now we just have a wrapper around a Map
  private class SimplePropertyMap(map: Map[Property[_], Any]) extends PropertyMap{
    override def apply[T](property: Property[T]) = map(property).asInstanceOf
    override def isDefinedAt(property: Property[_]) = map.isDefinedAt(property)
    override def toMap = map
    override def +[T](pair: (Property[T], T)) = new SimplePropertyMap(map + pair)
    override def -[T](property: Property[_]) = new SimplePropertyMap(map - property)
    override def keySet = map.keySet
  }
  
}

