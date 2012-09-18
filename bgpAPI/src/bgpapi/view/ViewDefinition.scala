/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapi.view

/**
 * A ViewDefinition is an object that defines what variables a ViewObject has.
 * Those variables are used for ViewEntities to know what they can control.<br>
 * ViewDefinitions are loaded like the ViewObjects from the filesystem and should
 * be supplied the developer.
 */
import bgpapi.Resource

trait ViewDefinition extends Resource with Serializable {
    
    /**
     * The variables that a ViewObject of this definition will have.<br>
     * Currently the Property class is used for this. But this may change when 
     * the variables get more functionality.
     */
    val variables: Set[Property[_ <: Any]]
    
    override def requirements = Set.empty
}
