/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapi

/**
 * The package containing the classes to control the view.<br>
        
 This package is structured as following: we have a main class, the 
 View class. This class is used to control the view.<br>
 To add something new to the View we have to add something new used a 
 ViewObject. A ViewObject is an Object that is loaded from a file that
 describes what something looks like, when you have for example a 3D 
 model, the ViewObject will contain the vertices to paint the model. To 
 identify what a ViewObject is, it contains a ViewType that will describe
 the properties a ViewObject has.<br>
 After adding a new object to the View, you will get a ViewEntity. This
 ViewEntity can be used to control the new instance of the ViewObject 
 you have added. The variables that a ViewEntity can control are defined by 
 a ViewDefinition that is owned by each ViewObject.
 */
package object view {
    
  import Property._
  
  implicit def tuple2PropertyTuple[T](t: (Property[T], T)): PropertyTuple[T] = t
  implicit def propertyTuple2Tuple[T](p: PropertyTuple[T]): (Property[T], T) = (p._1, p._2)

}
