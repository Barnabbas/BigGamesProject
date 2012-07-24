/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapplication


/**
 * The main class for this application.
 */
import bgpapplication.client.Client
import bgpapplication.server.Server

object Controller{
    
    // temporary methods, those will change when we get remote servers
    
    def startClient = {
        require(Server.actor != null)
        Client.start(Server.actor)
    }
    
    def startServer = {
        Server.start
    }

}
