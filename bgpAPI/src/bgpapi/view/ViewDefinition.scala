/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapi.view

import bgpapi.Resource
import bgpapi.properties.PropertyDefiner
import bgpapi.properties.Variable

trait ViewDefinition extends Resource with Serializable with PropertyDefiner[Variable[_]] {
        
    override def requirements = Set.empty
}
