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
import bgpapi.view.Property
import bgpapi.view.ViewObject
import bgpapi.view.ViewType
import bgpapplication.client.ResourceManager
import bgpapplication.client.view.ClientView
import bgpapplication.networking.Message
import bgpapplication.networking.Message._
import scala.actors.Actor
import scala.actors.Actor._

/**
 * A new Networker that will hold a connection with {@code server}
 * @param server the Server to connect with
 * @param clientActor the Actor of the Client to inform changes to
 * @throws ConnectionDeniedException if {@code server} does not except the connection
 */
class Networker(server: Actor, clientActor: Actor) extends Actor{
    
    /**
     * Runs this Networker
     */
    override def act() = {
        connect()
        waitFor(Message.load.Start)
        load()
        play()
        waitFor(Message.StartGame)
    }
    
    /**
     * Starts the connection with a server.
     * @throws ConnectionDeniedException if the server rejects the connection
     */
    private def connect() = {
        import register._
        
        server ! Request("first Networker")
        receive{
            case Deny(reason) => throw new Networker.ConnectionDeniedException(reason)
            case Accept => // we are done
        }
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
        
        var isRunning = true
        
        while(isRunning){
            receive{
                case obj: ViewObject => ResourceManager.saveResource(obj)
                case Message.load.Finish => isRunning = false//done
            }
        }
    }
    
    /**
     * handles the view stuff
     */
    private def play() = {
        
        // just use the ViewActor
        val view = (clientActor !? "Start Game").asInstanceOf[ClientView]
        val viewActor = new ViewActor(view, server)
        viewActor.start()
        while(true) {
            receive {
                case m => viewActor.forward(m)
            }
        }
    }
    
}

object Networker{
    
    /**
     * An error indicating that the connection with a server is rejected
     * @param reason the reason of the deny
     */
    class ConnectionDeniedException(reason: String) extends Exception(reason)
    
    private[networking] def wrongMessage(m: Any) = {
        System.err.println("Getting unknown message: " + m)
    }
    
}

/**
 * Just some test for the ViewActor; will be removed later on
 */
private object ViewActorTest extends App{
    
    val networker = new Networker(server, client)
    client.start()
    server.start()
    networker.start()
    
    println("everything started")
    
    
    object server extends Actor{
        
        val vObject = new ViewObject{
            override val viewType = ViewType.text
            override val identifier = "Barnabbas.test.helloWorld"
            override val variables = Set.empty[Property]
            override def apply(prop: Property) = {
                Option("Hello World!")
            }
        }
        
        override def act = {
            println("server started")
            loop {
                receive{
                    case register.Request(name) => {
                            println("register " + name)
                            
                            val netw = sender
                            
                            netw ! register.Accept
                            
                            netw ! load.Start
                            // sending objects
                            netw ! vObject
                            netw ! load.Finish
                            
                            // running the view
                            val id = 1
                            netw ! view.Add(id, vObject.identifier)
                            
                            println("Done modifying the view!")
                        }
                }
            }
        }
    }
    
    object client extends Actor{
        override def act = {
            loop{
                
                receive{
                    case "Start Game" => sender ! new ClientView
                }
            }
        }
    }
}

