package biggamesproject.client.middleware

import scala.collection.mutable

/**
 * This is a simple implementation of the TopicSubsriber that allows message to be published to, which 
 * will be forwarded to all subscribed handlers.
 * The subsribe method is not thread-safe
 */
class TopicForwarder extends TopicSubscriber {
	
	/**
	 * Holds the subscribed Handlers per Topic.
	 * By default all topics are empty
	 */
	private val subscriptions: mutable.Map[Topic, mutable.Set[Handler]] = mutable.Map.empty
	
	override def subscribe(topic: Topic, handler: Handler): Unit = synchronized {
		// adding the handler to the subscriptions for topic
		val handlers = subscriptions.getOrElseUpdate(topic, mutable.Set.empty)
		handlers += handler
	}
	
	/**
	 * will forward {@code message} to all Handlers subsribed to {@code topic}
	 */
	def publish(topic: Topic, message: Any): Unit = synchronized {
		for (handler <- subscriptions(topic)) {
			handler(message)
		}
	}

}