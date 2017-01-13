package actors

import akka.actor.{Actor, ActorRef, Props}
import messages.ActorMessages._

/**
  * Created by osocron on 9/01/17.
  */
class Supervisor extends Actor {

  override def receive: Receive = talk(None, None)

  def talk(moderator: Option[ActorRef], speaker: Option[ActorRef]): Receive = {

    case ModeratoConnected(mOut) => context.become(talk(Some(mOut), speaker))

    case SpeakerConnected(sOut)  => context.become(talk(moderator, Some(sOut)))

    case SpeakerSays(sMsg) => moderator match {
      case Some(m) => m ! sMsg
      case None => sender() ! ModeratorNotConnected("Moderator not connected yet!")
    }

    case ModeratorSays(mMsg) => speaker match {
      case Some(s) => s ! mMsg
      case None => sender() ! SpeakerNotConnected("Speaker not connected yet!")
    }

  }

}

object Supervisor {
  def props = Props(new Supervisor)
}
