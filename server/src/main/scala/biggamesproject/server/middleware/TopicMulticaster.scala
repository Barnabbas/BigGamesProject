package biggamesproject.server.middleware

import akka.actor.ActorRef


/**
 * A TopicPublisher which simply multicasts a number of topics to the clients
 * @param client the Actors to multicast to
 * @todo maybe create separated client class
 */
class TopicMulticaster(clients: Seq[ActorRef]) extends TopicPublisher {
	
	override def publish(topic: Topic, message: Any) = clients foreach (_ ! (topic.id, message))
	
}