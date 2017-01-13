package actors

import akka.actor.{Actor, ActorRef, ActorSystem, Cancellable, Props}
import messages.ActorMessages._

/**
  * Created by osocron on 9/01/17.
  */
class ModeratorActor(out: ActorRef, supervisor: ActorRef, system: ActorSystem) extends Actor {

  import scala.concurrent.duration._

  val cancellable: Cancellable =
    system.scheduler.schedule(0.microseconds, 5.second, out , "moderating")

  def receive: Receive = {
    case msg: String => supervisor ! ModeratorSays("The moderator says: " + msg)
    case SpeakerNotConnected(m) => out ! m
  }

}

object ModeratorActor {
  def props(out: ActorRef, supervisor: ActorRef, system: ActorSystem) = Props(new ModeratorActor(out, supervisor, system))
}
