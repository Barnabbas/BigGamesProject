/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapplication

/**
 * The ViewTypes that are used for this Application. Both for the server and the
 * Client
 */
import bgpapi.view.ViewType
import bgpapi.properties.Setting

object ViewTypes {
    
  /**
   * This object contains all properties used by the ViewTypes
   */
  private object settings{
    val size = new Setting("size")
  }
  
  /**
   * This ViewType represents the squares. This is just a testing ViewType
   */
  object square extends ViewType('square, Set(settings.size)){
    val size = settings.size
  }
    
  private val types = Map('square -> square)
    
  /**
   * Gets the type named {@code name}.
   */
  def apply(name: Symbol) = types(name)
  /**
   * Gets the type named {@code name}.
   */
  def apply(name: String): ViewType = apply(Symbol(name))

}
