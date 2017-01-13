package actors

import akka.actor.{Actor, ActorRef, ActorSystem, Cancellable, Props}
import messages.ActorMessages._

/**
  * Created by osocron on 9/01/17.
  */
class SpeakerActor(out: ActorRef, supervisor: ActorRef, system: ActorSystem) extends Actor {

  import scala.concurrent.duration._

  val cancellable: Cancellable =
    system.scheduler.schedule(0.microseconds, 5.second, out , "speaking")

  def receive: Receive = {
    case msg: String => supervisor ! SpeakerSays("The speaker says: " + msg)
    case ModeratorNotConnected(m) => out ! m
  }

}

object SpeakerActor {
  def props(out: ActorRef, supervisor: ActorRef, system: ActorSystem) = Props(new SpeakerActor(out, supervisor, system))
}
