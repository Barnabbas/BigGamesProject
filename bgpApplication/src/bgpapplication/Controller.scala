/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapplication


/**
 * The main class for this application.
 */
import bgpapi.game.GameFactory
import bgpapplication.client.Client
import bgpapplication.server.Server

object Controller{
    
    // todo: redesign, this should control the GUI not the way around
    
    // temporary methods, those will change when we get remote servers
    
    def startClient = {
        require(Server.actor != null)
        Client.start(Server.actor)
    }
    
    def startServer(factory: GameFactory) = {
        Server.start(factory)
    }

}
