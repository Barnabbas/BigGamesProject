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
     * The settings that this GameFactory has.
     */
    val settings: List[String]
    
    /**
     * Creates a new Game.
     * @param theme the GameTheme to run for the Game
     */
    def createGame(theme: GameTheme): Game

}
