/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapi.view

/**
 * ViewObjects are used to tell the View what a new Entity should look like.<br>
 * ViewObjects are normally retrieved from the filesystem and will be loaded to 
 * the client to make it possible for the client to display items
 */

import bgpapi.Resource
import bgpapi.properties.PropertyHolder
import bgpapi.properties.Setting

trait ViewObject extends Resource with PropertyHolder[Setting, Setting.Value] {
    
    
    /**
     * The Definition that used to define the variables this ViewObject will have
     */
    def definition: ViewDefinition
    
    override def requirements = Set(definition)

}
