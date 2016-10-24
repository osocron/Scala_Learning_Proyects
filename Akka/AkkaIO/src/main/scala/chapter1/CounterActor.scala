package chapter1

import akka.actor.Actor
import akka.actor.Actor.Receive
import akka.event.Logging
import chapter1.messages.{AskCounter, SetString}

/**
  * First attempt at creating an actor with no mutable state.
  * Vars are note evil in the context of the actor system, but it's still
  * nice to be able to do stuff without them.
  */
class CounterActor extends Actor {

  val log = Logging(context.system, this)

  override def receive: Receive = count(0)

  def count(c: Int): Receive = {

    case SetString(msg) =>
      val counter = c + msg.length
      log.info("Longitud de palabras = {}", counter.toString)
      context.become(count(counter))

    case AskCounter => sender ! c

  }

}
