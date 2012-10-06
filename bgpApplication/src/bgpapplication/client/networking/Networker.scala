/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapplication.client.networking

/**
 * The Networker is the class that maintains the connection with a Server.<br>
 * A Networker can send the following messages to the Actor used for the ClientActor.<br>:
 * "Start Game": this should return a ClientView this Networker can send the changes to.
 */
import bgpapi.view.ViewDefinition
import bgpapi.view.ViewObject
import bgpapplication.client.resources._
import bgpapplication.client.view.ClientView
import bgpapplication.networking._
import bgpapplication.networking.Message._
import scala.actors.AbstractActor
import scala.actors.Actor
import scala.actors.Actor._
import bgpapplication.util.Debug
import scala.actors.remote.RemoteActor

/**
 * A new Networker that will hold a connection with {@code server}
 * @param server the Server to connect with
 * @param clientActor the Actor of the Client to inform changes to
 * @throws ConnectionDeniedException if {@code server} does not except the connection
 */
class Networker(server: AbstractActor, clientActor: Actor) extends Actor {

  /**
   * Runs this Networker
   */
  override def act() = {

    // todo: not sure whether this work, but this may solve the class not found exception
    // appears to be not working with multiple processes :s
    RemoteActor.classLoader = getClass().getClassLoader()
    
    connect()
    waitFor(Message.load.Start)
    load()
    waitFor(Message.StartGame)
    play()
  }

  /**
   * Starts the connection with a server.
   * @throws ConnectionDeniedException if the server rejects the connection
   */
  private def connect() = {
    import register._

    Networker.debug("starting to connect to server")
    server ! Request("/me")
    receive {
      case Deny(reason) => throw new Networker.ConnectionDeniedException(reason)
      case Accept => // we are done
    }

    Networker.debug("connected")
  }

  /**
   * Waits for a certain message from the Server
   * @param message the Message to wait for
   */
  private def waitFor(Message: Message) = {
    receive {
      case Message => // do nothing: just continue
      case m => Networker.wrongMessage(m)
    }
  }

  /**
   * Handles the loading state
   */
  private def load() = {

    Networker.debug("start Loading")

    var isRunning = true

    while (isRunning) {
      receive {
        case obj: ViewObject => {
          ResourceManager.saveResource(obj)
          Networker.debug("receiving: " + obj)
        }
        case definition: ViewDefinition => {
          ResourceManager.saveResource(definition)
          Networker.debug("receiving: " + definition)
        }
        case Message.load.Finish => isRunning = false //done
      }
    }
  }

  /**
   * handles the view stuff
   */
  private def play() = {

    Networker.debug("Start playing")

    // just use the ViewActor
    val view = (clientActor !? "Start Game").asInstanceOf[ClientView]
    val viewActor = new ViewActor(view, server)
    viewActor.start()

    while (true) {
      receive {
        case m => {
          Networker.debug("forwarding " + m)
          viewActor.forward(m)
        }
      }
    }
  }

}

object Networker {

  /**
   * An error indicating that the connection with a server is rejected
   * @param reason the reason of the deny
   */
  class ConnectionDeniedException(reason: String) extends Exception(reason)

  private[networking] def wrongMessage(m: Any) = {
    System.err.println("Getting unknown message: " + m)
  }

  private[networking] val debug = Debug("Networker(Client)")

}
