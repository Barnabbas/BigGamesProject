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
import bgpapplication.test.TestGame
import scala.actors.Actor
import scala.actors.Reactor

object Server extends App {
    
    // this is just a test version. Therefore it just takes a GameFactory itself
    // without really loading it
    
    /**
     * The GameFactory to use
     */
    private val gameFactory = TestGame
    
    private val game = gameFactory.createGame
    
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
    
    val networker = new Networker(game.viewObjects, playersReactor)
    
    /**
     * The actor of the Server.<br>
     * To this Actor request for joining the Server should be send
     */
    val actor: Actor = networker
    
    
    private def onPlayerJoin(name: String){
        // start waiting for when the player is done
        while (!networker.canStart){
            Thread sleep 100
        }
        val view = networker.remoteView
        game.start(view)
    }

}
