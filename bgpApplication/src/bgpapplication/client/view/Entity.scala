/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapplication.client.view

/**
 * An Entity is a ViewElement that can be displayed by a Renderer.<br>
 * Entities are used by {@link ClientView} to display everything
 */
import bgpapi.view._
import bgpapplication.util.implementations.SimpleViewEntity
import bgpapi.properties.Property
import bgpapi.properties.Setting
import bgpapi.properties.Variable

/**
 * An Entity that will displayed according to {@code viewObject} and {@code viewEntity}.
 * @param viewObject how this Entity should be displayed
 * @param ViewEntity additional properties that can be set during run-time
 */
class Entity(viewObject: ViewObject, variables: Map[Variable, Variable.Value]) {
    
  /**
   * the ViewEntity containing the properties for this Entity
   */
  val viewEntity = new SimpleViewEntity(viewObject, variables)
    
  /* The renderer that will render this entity */
  private val renderer = Renderer(viewObject)
    
  /**
   * Displays this Entity
   */
  def display = renderer.display(this)
    
  /**
   * Gets the value of the Property {@code prop} of this Entity.
   */
  private[view] def apply(prop: Setting) = {
    
    import Setting.Value._
    
    viewObject(prop) match {
      case Data(data) => data
      case Setting.Value.Variable(variable) => viewEntity(variable).data
    }
  }
}
