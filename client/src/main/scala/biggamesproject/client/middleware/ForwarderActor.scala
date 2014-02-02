package biggamesproject.client.middleware

import akka.actor.Actor

/**
 * An Actor that forwards all the Messages it get to the TopicForwarder.
 * It expects the message in the format 
 */
class ForwarderActor(forwarder: TopicForwarder) extends Actor {
	
	private implicit val subscriber = forwarder

	override def receive = {
		case (topicID: String, message) => {
			val topic = Topic(topicID)
			forwarder.publish(topic, message)
		}
	}
	
	
}