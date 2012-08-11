/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapplication.util.implementations

import bgpapi.view.Property
import bgpapi.view.ViewDefinition

/**
 * A simple implementation of the ViewDefinition interface
 */

final class SimpleViewDefinition(override val identifier: String,
                                 override val variables: Set[Property]) extends ViewDefinition
