package biggamesproject.server.middleware

/**
 * A TopicPublisher allows Topics to publish messages.
 * This hold the implementation for the publish method
 * 
 * @todo may also change name (see Topic for more information)
 */
trait TopicPublisher {
	
	
	/**
	 * Publish a message for the given Topic.
	 * This let the given message be send to who are subscribed to the topic
	 * @param topic the Topic to publish a message to
	 * @param message the message to send
	 */
	def publish(topic: Topic, message: Any): Unit

}