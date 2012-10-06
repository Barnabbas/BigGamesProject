/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapplication.server.networking

import scala.actors.Actor
import scala.actors.Reactor
import bgpapi.view.View
import bgpapplication.networking.Message.register.Accept
import bgpapplication.networking.Message.register.Deny
import bgpapplication.networking.Message.register.Request
import bgpapplication.server.networking.Client.clientAsActor
import bgpapplication.server.resources.ResourceLoader
import bgpapplication.util.Debug
import scala.actors.remote.RemoteActor

/**
 * Creates a new Networker.<br>
 * This Networker will host a Game that requires {@code resources} to run.
 *
 * @param loader the ResourceLoader containing the resources that the client should have
 * @param playersReactor a Reactor where all new players will be send to.
 */
class Networker(loader: ResourceLoader, playersReactor: Reactor[String]) extends Actor {

  private val debug = Debug("Networker")

  /**
   * Whether new players are allowed to this Networker.<br>
   * Normally new player are allowed to enter at the start until the game is
   * started.
   */
  var allowPlayers = true

  /**
   * The ResourceLoader for this Networker.
   */
  private val resNetworker = new ResourceNetworker(loader)

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
  def canStart: Boolean = !resNetworker.isBusy

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
    
    
    // this makes sure that the remoteActor uses the default ClassLoader
    RemoteActor.classLoader = super.getClass().getClassLoader()
    
    while (true) {
      receive {
        case Request(name) => {
          debug("getting client request")
          if (allowPlayers) {
            val client = new Client(name, sender)
            clients ::= client

            client ! Accept
            resNetworker.addClient(client)
            playersReactor ! name
          } else {
            sender ! Deny("Server is not allowing players at the moment")
          }
        }
      }
    }
  }

}
