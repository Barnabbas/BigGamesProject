package biggamesproject.client.middleware

/**
 * The TopicSubscriber handles the subscribing of Topics and makes sure the right functions are called for the Topics.
 * This hold the 
 */
trait TopicSubscriber {
	
	/**
	 * Subscribes the method to Topic.
	 * This will let {@code handler} be called when a new message has been published for {@code topic}
	 * @param topic the Topic to subscribe the method to
	 * @param handler the method that will be called when a new message has been published, as parameter it receives the message. 
	 */
	def subscribe(topic: Topic, handler: Handler): Unit

}