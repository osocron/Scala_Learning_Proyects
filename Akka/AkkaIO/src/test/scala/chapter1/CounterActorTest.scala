package chapter1

import akka.actor.ActorSystem
import akka.testkit.TestActorRef
import chapter1.messages.{AskCounter, SetString}
import org.scalatest.{BeforeAndAfterEach, FunSpecLike, Matchers}
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._

import scala.concurrent.{Await, Future}

/**
  * Created by osocron on 9/9/16.
  */
class CounterActorTest extends FunSpecLike with Matchers with BeforeAndAfterEach  {

  implicit val actorSystem = ActorSystem()
  implicit val timeout = Timeout(15 seconds)

  describe("Counter Actor") {
    describe("given a string message") {

      it("should return the accumulated counter") {
        val words = "Hello world this is awesome!"
        val actorRef = TestActorRef(new CounterActor)
        actorRef ! SetString("Hello")
        actorRef ! SetString(" world")
        actorRef ! SetString(" this")
        actorRef ! SetString(" is awesome!")
        val future: Future[Int] = (actorRef ? AskCounter).mapTo[Int]
        val result = Await.result(future, timeout.duration)
        result should equal(words.length)
      }

    }
  }

}
