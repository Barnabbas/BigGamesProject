/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapi.game


import bgpapi.Resource
import bgpapi.view.ViewObject

/**
 * A GameTheme are the settings for a certain Game. And the GameTheme is required
 * to get the ViewObjects for a Game such that it can be played (and be viewed).
 */
trait GameTheme extends Resource {
    
    /**
     * The ViewObject for the Setting called {@code setting}
     */
    def getObject(setting: String): ViewObject
    
}
