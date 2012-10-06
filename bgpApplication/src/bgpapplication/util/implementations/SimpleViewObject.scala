/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapplication.util.implementations

import bgpapi.view._
import bgpapplication.util.PropertyMap._
import bgpapi.properties.Setting

final class SimpleViewObject(override val identifier: String,
                    viewDef: ViewDefinition,
                    viewType: ViewType,
                    values: SettingMap) extends ViewObject with Serializable {
    
    override def apply(setting: Setting) = values(setting)
    override def definer = viewType
    override def definition = viewDef
}
