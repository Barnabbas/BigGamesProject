/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapplication.util.implementations

/**
 * A Simple implementation of the GameTheme trait.
 */
import bgpapi.game.GameTheme
import bgpapi.view.ViewObject
import bgpapi.Resource

final class SimpleGameTheme(override val identifier: String,
                            objects: Map[String, ViewObject]) extends GameTheme {
    
    override def getObject(setting: String) = objects(setting)
    override def requirements = objects.values.toSet[Resource]
    

}
