/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapplication.util.implementations

import bgpapi.view.ViewDefinition
import bgpapi.properties.Property
import bgpapi.properties.Variable

/**
 * A simple implementation of the ViewDefinition interface
 */

class SimpleViewDefinition(override val identifier: String,
                                 variables: Set[Variable]) extends ViewDefinition with Serializable{
  override def properties = variables
}
