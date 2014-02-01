package biggamesproject.client

package object middleware {
	
	/**
	 * This type represents the callback function used to subscribe to Topics.
	 * The parameter will receive the message.
	 */
	type Handler = Any => Unit
	
}