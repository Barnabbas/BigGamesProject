/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapplication.util

import bgpapi.properties._

/**
 * A ProeprtyMap is used to store the values for a ViewObject.<br>
 * This is a mapping between the properties and the PropertyValue.
 * 
 * @deprecated This will be removed in newer version
 */
object PropertyMap{
  type VariableMap = Map[Variable, Variable.Value]
  type SettingMap = Map[Setting, Setting.Value]
}
  