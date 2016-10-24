package com.sisco.db

import akka.actor.ActorSystem
import akka.testkit.TestActorRef
import com.sisco.messages.SetRequest
import org.scalatest.{BeforeAndAfterEach, FunSpecLike, Matchers}

/**
  * Created by osocron on 9/9/16.
  */
class SiscoDBTest extends FunSpecLike with Matchers with BeforeAndAfterEach {

  implicit val system = ActorSystem()

  describe("SiscoDB") {
    describe("given SetRequest") {
      it("should place key/value into map") {
        val actorRef = TestActorRef(new SiscoDB)
        actorRef ! SetRequest("key", "value")
        val siscoDB = actorRef.underlyingActor
        siscoDB.map.get("key") should equal (Some("value"))
      }
    }
  }



}
