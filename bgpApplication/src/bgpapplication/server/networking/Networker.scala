/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapplication.server.networking

/**
 * The main Networker for the server.<br>
 * Here is the communication done.
 */

import bgpapi.Resource
import bgpapi.view.View
import scala.actors.Actor
import scala.actors.Reactor

/**
 * Creates a new Networker.<br>
 * This Networker will host a Game that requires {@code resources} to run.
 * 
 * @param resources the Resources to run the Game on.
 * @param playersReactor a Reactor where all new players will be send to.
 */
class Networker(resources: List[Resource], playersReactor: Reactor[String]) extends Actor {
    
    
    /**
     * Whether new players are allowed to this Networker.<br>
     * Normally new player are allowed to enter at the start until the game is 
     * started.
     */
    var allowPlayers = true
    
    /**
     * The ResourceLoader for this Networker.
     */
    private val loader = new ResourceLoader(resources)
    
    /**
     * All the clients that are registered to this Networker
     */
    private var clients = List.empty[Client]
    
    /**
     * The view, this should only become initialized when canStart.<br>
     * Only {@link #remoteView} should acces this.
     */
    private lazy val view: View = {
        require(canStart)
        new ViewNetworker(clients)
    }
    
    /**
     * Determines whether the Networker is done loading to all players.
     */
    def canStart: Boolean = !loader.isBusy
    
    /**
     * Gets the Views of all the Clients.<br>
     * This requires {@link #canStart}.
     */
    def remoteView: View = {
        require(canStart, "The View is only available when this Networker can be started.")
        return view
    }
    
    override def act = {
        import bgpapplication.networking.Message.register._
        while(true){
            receive{
                case Request(name) => {
                      if (allowPlayers){
                          val client = new Client(name, sender)
                          clients ::= client
                          
                          client ! Accept
                          loader.addClient(client)
                          playersReactor ! name
                      } else {
                          sender ! Deny("Server is not allowing players at the moment")
                      }
                }
            }
        }
    }

}
