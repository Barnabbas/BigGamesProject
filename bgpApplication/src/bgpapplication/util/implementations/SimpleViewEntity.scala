/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapplication.util.implementations

import bgpapi.view.ViewObject
import bgpapi.view.ViewEntity
import bgpapi.properties.Variable
import bgpapi.properties._

/**
 * An implementation for the ViewEntity trait.
 * @param viewObject the ViewObject that this Entity instances of.
 * @param initVariables the initial values of the properties for this ViewEntity.
 */
class SimpleViewEntity(viewObject: ViewObject, initVariables: Map[Variable, Variable.Value])
    extends ViewEntity with Serializable {
  // todo: testing checkvariables again
  // checking for valid variables
  checkVariables();

  /**
   * A Map between the properties and the values
   */
  private var values = initVariables

  /**
   * Gets the value of variable {@code v}
   * @param p the Variable to get the value of
   *
   * @throws NoSuchElementException if there is no value for {@code v}
   */
  final override def apply(v: Variable) = values(v)

  final override def definer = viewObject.definition

  /**
   * Sets the value of {@code p} to {@code v}.<br>
   * You can only set values of properties in {@code properties}.
   * @param p the Property to set the value of
   * @param v the new value
   */
  final override def update(variable: Variable, value: Variable.Value) = {
    values += variable -> value
    onUpdate(variable, value)
  }

  /**
   * Will be called when a property value got changed.<br>
   * Can be overridden to be notified when this ViewEntity is updated
   */
  protected def onUpdate(variable: Variable, value: Variable.Value) = {
    // to override
  }

  /**
   * Checks if all variables of the ViewObject has been set.<br>
   * Checked using the ViewDefinition currently.
   * This will throw a IllegalArgumentException when not all variables are available
   * @throws IllegalArgumentException if not all the required variables are included
   */
  private def checkVariables() = {
    val variables = definer.properties
    val isComplete = {
      variables.forall { v =>
        initVariables.isDefinedAt(v)
      }
    }
    require(isComplete, "Not all variables are set in " + viewObject +
      "\nmissing: " + (variables &~ initVariables.keySet))
  }
}
