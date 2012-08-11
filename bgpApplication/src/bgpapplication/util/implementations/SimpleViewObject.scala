/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapplication.util.implementations

/**
 * The implementation of the ViewObject trait.
 */

import bgpapi.view._

final class SimpleViewObject(override val identifier: String,
                    viewDef: ViewDefinition,
                    override val viewType: ViewType,
                    values: Map[Property, PropertyValue]) extends ViewObject {
    
    override def apply(prop: Property) = values.get(prop)
    override def definition = viewDef
}
