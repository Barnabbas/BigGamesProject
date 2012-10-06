
package bgpapi

/**
 * This package contains classes to define and work with properties.
 *
 * Properties can be divided into two subclasses: Variables and Settings.
 * Variables are some sort of pointers (for example for inside the ViewEntities) that point to something that can change.
 * Settings are also some sort of pointers that point to something that is either raw data or it points to a variable.
 */

package object properties{
  
  /**
   * Wraps {@code a} into a Variable Value
   */
  implicit def any2VariableValue(a: Any): Variable.Value = Variable.Value(a)
  
}