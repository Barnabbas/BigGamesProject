package biggamesproject.client.middleware

import akka.actor.Actor

/**
 * An Actor that forwards all the Messages it get to the TopicForwarder.
 * It expects the message in the format 
 */
class ForwarderActor(listener: TopicForwarder) extends Actor {

	override def receive = {
		case (topicID: String, message) => {
			val topic = Topic(topicID)(listener)
			listener.publish(topic, message)
		}
	}
	
	
}