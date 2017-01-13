package messages

import akka.actor.ActorRef

/**
  * Created by osocron on 9/01/17.
  */
class ActorMessages

object ActorMessages {

  case class SpeakerConnected(out: ActorRef)

  case class ModeratoConnected(out: ActorRef)

  case class SpeakerSays(msg: String)

  case class ModeratorSays(msg: String)

  case class SpeakerNotConnected(msg: String)

  case class ModeratorNotConnected(msg: String)

}
