package biggamesproject.server.middleware

import scala.collection.mutable.HashMap


/**
 * A Topic is used to notify the clients that a certain change has happened.
 * It is used to let client subscribe to a certain Topic and when a change happens, the subscribed clients will be notified.
 * (This only happens in theory, the real implementation may differ).
 * 
 * To use it as a Server just use the publish method to send something.
 * 
 * @todo maybe other name, like Event or Observer
 * @todo id may become more public
 */
class Topic private(private[middleware] val id: String, publisher: TopicPublisher) {
	
	/**
	 * Sends {@code message} to all clients subscribed to this TOpic
	 */
	def publish(message: Any): Unit = publisher.publish(this, message)
	
	override def toString = s"Topic $id"

}

object Topic {
	
	/**
	 * A map between the id of the topics and the Topic itself
	 */
	private val topics = new HashMap[String, Topic]()
	
	/**
	 * Creates a new Topic with the given name.
	 * 
	 * Note that this method is not thread safe.
	 * 
	 * @param id the name of the Topic, has to be unique
	 * @param publisher the TopicPublisher which will manage the new Topic
	 */
	def apply(id: String)(implicit publisher: TopicPublisher): Topic = {
		// get the topic from topics, or add and return new topic
		topics.getOrElse(id, new Topic(id, publisher))
	}
}