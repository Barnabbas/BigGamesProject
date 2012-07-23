/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapi.game

import bgpapi.view.View

/**
 * A GameFactory is used to create Games
 */
trait GameFactory {
    
    /**
     * An unique name for the Factory.<br>
     * Should be of the format {@code authorName.gameName}
     */
    val identifier: String
    
    /**
     * Creates a new Game.
     */
    def createGame: Game

}
