package chapter1

import akka.actor.Actor
import akka.event.Logging
import chapter1.messages.SetString

/**
  * Created by osocron on 9/9/16.
  */
class MessageActor extends Actor {

  var message = ""
  val log = Logging(context.system, this)

  override def receive: Receive = {
    case SetString(msg) => {
      log.info("recieved message - {}", msg)
      message = msg
    }
    case o =>
  }

}
