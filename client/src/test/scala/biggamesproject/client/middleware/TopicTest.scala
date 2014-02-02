package biggamesproject.client.middleware

import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfterAll
import akka.actor.ActorSystem
import akka.actor.Props
import akka.testkit.TestActorRef

class TopicTest extends FunSuite with BeforeAndAfterAll {
	
	implicit val system = ActorSystem("test")
	implicit val forwarder = new TopicForwarder()
	val actor = TestActorRef(Props(new ForwarderActor(forwarder)))
	
	// counter used for testing handlers
	var message1: Any = "nothing send yet" // well except maybe that "nothing send yet"
	var counter2 = 0
	
	test("Creating two Topics with the same name, should return the same Topic") {
		val topic1 = Topic("topic")
		val topic2 = Topic("topic")
		assert(topic1 === topic2)
	}
	
	test("Using the TopicForwarder should forward messages to the subscriptions") {
		
		// creating stuff
		val topic1 = Topic("1")
		val topic2 = Topic("2")
		// adding subscriptions
		topic1.subscribe { m =>
			message1 = m
		}
		topic2.subscribe { _ =>
			counter2 += 1
		}
		topic2.subscribe { _ =>
			counter2 += 1
		}
		
		// testing topic1
		actor ! ("1", "message")
		assert(message1 === "message")
		
		// testing topic2
		actor ! ("2", "other message which will not be checked")
		assert(counter2 === 2)
		
	}

}