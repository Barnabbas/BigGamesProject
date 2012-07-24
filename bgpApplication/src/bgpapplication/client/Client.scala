/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapplication.client

import bgpapplication.client.networking.Networker
import bgpapplication.client.view.ClientView
import scala.actors.Actor
import scala.actors.OutputChannel

/**
 * The Main Actor that will run the Client
 */

object Client {
    
    // starting the clientActor
    actor.start()
    
    /**
     * The ClientView used by this Client
     */
    lazy val view = new ClientView()
    
    /**
     * Starts this Client.
     * @param server the Server to register to
     */
    def start(server: Actor) = {
        val networker = new Networker(server, actor)
        networker.start()
    }
    
    /**
     * Starts a new Game by creating a new View and sending it to {@code actor}
     * @param actor the Actor that wants to start the game
     */
    private def startGame(actor: OutputChannel[Any]){
        actor ! view
    }
    
    /**
     * the Actor that is listening for any other Actor that wants to communicate
     * with this.
     */
    private object actor extends Actor{
        override def act(){
            while(true){
                receive{
                    case "Start Game" => startGame(sender)
                }
            }
        }
    }
    
}
