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
import bgpapplication.server.resources.ResourceLoader
import scala.actors.remote.Node
import scala.actors.remote.RemoteActor

object Controller{
    
    // todo: redesign, this should control the GUI not the way around
    
    // temporary methods, those will change when we get remote servers
    
    def startClient(address: String) = {
        val serverActor = RemoteActor.select(new Node(address, 61090), 'BigGameProject)
        require(serverActor != null)
        Client.start(serverActor)
    }
    
    def startServer(loader: ResourceLoader) = {
        Server.start(loader)
    }

}
