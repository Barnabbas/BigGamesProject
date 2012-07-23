/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapplication.server.networking

/**
 * A Client is a small class representing a Client that is registered on this 
 * Server.
 */
import scala.actors.Actor
import scala.actors.OutputChannel

final class Client(val name: String, channel: OutputChannel[Any]) {
    val actor = channel.receiver
}

object Client {
    implicit def clientAsActor(client: Client): Actor = client.actor
}
