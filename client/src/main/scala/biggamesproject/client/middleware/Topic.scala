package biggamesproject.client.middleware

import scala.collection.mutable.HashMap
import scala.collection.mutable.SynchronizedMap

/**
 * A Topic is used to receive notifications when Events happens.
 * The Topics are used to subscribe to, to receive the notifications
 * @todo maybe other name
 * @todo maybe an unsubscribe function?
 */
class Topic private(private[middleware] val id: String, subscriber: TopicSubscriber) {
	
	/**
	 * Subscribes to this Topic,
	 * Such that when new messages arrived the given function will be called.
	 * @param handler the method to be executed when a message has been published,
	 *  the message will be given in the function parameter
	 */
	def subscribe(handler: Handler): Unit =
		subscriber.subscribe(this, handler)

}

object Topic {
	
	/**
	 * A map between the id of the topics and the Topic itself
	 */
	private val topics = new HashMap[String, Topic] with SynchronizedMap[String, Topic]
	
	/**
	 * Creates a new Topic with the given name.
	 * 
	 * Note that this method is not thread safe.
	 * 
	 * @param id the name of the Topic, has to be unique
	 * @param subscriber the TopicSubscriber to use as implementation for subscribing
	 */
	def apply(id: String)(implicit subscriber: TopicSubscriber): Topic = {
		// get the topic from topics, or add and return new topic
		topics.getOrElseUpdate(id, new Topic(id, subscriber))
	}
}