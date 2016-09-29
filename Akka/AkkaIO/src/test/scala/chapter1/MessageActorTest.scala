package chapter1

import akka.actor.ActorSystem
import akka.testkit.TestActorRef
import chapter1.messages.SetString
import org.scalatest.{BeforeAndAfterEach, FunSpecLike, Matchers}
/**
  * Created by osocron on 9/9/16.
  */
class MessageActorTest extends FunSpecLike with Matchers with BeforeAndAfterEach {

  implicit val system = ActorSystem()

  describe("MessageActor") {
    describe("given a message message") {

      it("should store a passed message") {
        val actorRef = TestActorRef(new MessageActor)
        actorRef ! SetString("Hello World!")
        val msgActor = actorRef.underlyingActor
        msgActor.message should equal("Hello World!")
      }

      it("should store the last passed message") {
        val actorRef = TestActorRef(new MessageActor)
        actorRef ! SetString("Hello World!")
        actorRef ! SetString("Hello again")
        val msgActor = actorRef.underlyingActor
        msgActor.message should equal("Hello again")
      }

    }
  }

}
