package biggamesproject.server.middleware

import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfterAll
import akka.testkit.TestActorRef
import akka.actor.Actor
import akka.actor.ActorSystem

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
class TopicTest extends FunSuite with BeforeAndAfterAll {

	implicit val system = ActorSystem("test")

	test("Using the TopicMulticaster should forward messages") {

		// creating stuff
		val testClient1 = TestActorRef[TestClient]
		val testClient2 = TestActorRef[TestClient]
		implicit val publisher = new TopicMulticaster(Seq(testClient1, testClient2))
		val topic1 = Topic("topic")
		val topic2 = Topic("test")

		topic1.publish("hello world")

		// checking for whether we received the right message
		assert(testClient1.underlyingActor.last === ("topic", "hello world"))
		assert(testClient2.underlyingActor.last === ("topic", "hello world"))

		topic2.publish(2)

		// checking for whether we received the right message
		assert(testClient1.underlyingActor.last === ("test", 2))
		assert(testClient2.underlyingActor.last === ("test", 2))
	}

	override def afterAll() = {
		system.shutdown()
	}

}

/**
 * A fake client used for testing
 */
class TestClient extends Actor {

	var last: Any = _

	override def receive = {
		case message => last = message
	}

}
