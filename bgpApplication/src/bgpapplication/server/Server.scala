/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapplication.server

/**
 * The Server is the main object for the server packages.<br>
 * It will run the Server part of the application, so it is possible to start 
 * Games with it, which can be joined by clients.
 * To let a client join a Game he should send a {@code register.Request} Message
 * as defined in {@link Message}.
 */
import bgpapi.game._
import bgpapplication.server.networking.Networker
import bgpapplication.server.resources.GameLoader
import bgpapplication.server.resources.ResourceLoader
import bgpapplication.util.Debug
import java.io.File
import scala.actors.Actor
import scala.actors.Reactor
import scala.actors.remote.RemoteActor

// todo: rewrite: it became very ugly after adding the start def

object Server {
    
    private val debug = Debug("Server")
    
    private var networker: Networker = _
    
    private var game: Game = _
    
    
    /**
     * Starts the server such that it will use the resources from {@code loader}.<br>
     * It will immediately create a Game that is based on the data loaded from {@code loader}.
     */
    def start(loader: ResourceLoader) = {

        game = GameLoader.createGame(loader)
        
        
        playersReactor.start()
        networker = new Networker(loader, playersReactor)
        networker.start()
        
        // registering on the networker
        RemoteActor.alive(61090)
        RemoteActor.register('BigGameProject, networker)
    }
    
    
    /**
     * The Reactor that will be notified when a Player joins this Game
     */
    private val playersReactor = new Reactor[String] {
        override def act() = {
            react{
                case name => Actor.actor(onPlayerJoin(name)); act()
            }
        }
    }
    
    
    /**
     * The actor of the Server.<br>
     * To this Actor request for joining the Server should be send
     */
    def actor: Actor = networker
    
    /**
     * Determines if we can start a Game
     */
    def canStart = networker.canStart
    /**
     * Starts a Game.
     * Requires {@code canStart}
     */
    def startGame() = {
        require(canStart, "Can not start the Game yet")
        networker.allowPlayers = false
        game.start(networker.remoteView)
        
        debug("Game started!")
    }
    
    /* --- joined player stuff --- */
    /**
     * The names of the players that joined this Server
     */
    private var joinedPlayers = List.empty[String]
    
    /**
     * A List containing the names of the players that joined this Server
     */
    def players = joinedPlayers
    
    /**
     * Will be called when the list of players changes
     */
    var onPlayerChange: () => Unit = _
    
    
    private def onPlayerJoin(name: String){
        debug(name + " has joined the Game.")
        
        joinedPlayers ::= name
        
        onPlayerChange()
    }

}
