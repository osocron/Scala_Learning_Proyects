package controllers

import javax.inject.Inject

import akka.actor.Actor.Receive
import akka.actor.{ActorRef, ActorSystem, Props}
import akka.stream.Materializer

import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.streams.ActorFlow
import play.api.mvc._

class Application @Inject() (implicit system: ActorSystem, materializer: Materializer) extends Controller {

  val supervisorProps: Props = Supervisor.props

  val supervisorRef: ActorRef = system.actorOf(supervisorProps)

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  import akka.actor._

  case class SpeakerConnected(out: ActorRef)

  case class ModeratoConnected(out: ActorRef)

  case class SpeakerSays(msg: String)

  case class ModeratorSays(msg: String)

  case class SpeakerNotConnected(msg: String)

  case class ModeratorNotConnected(msg: String)

  def speakerSocket: WebSocket = WebSocket.accept[String, String] { _ =>
    ActorFlow.actorRef(out => {
      supervisorRef ! SpeakerConnected(out)
      SpeakerActor.props(out, supervisorRef)
    })
  }

  def moderatorSocket: WebSocket = WebSocket.accept[String, String] { _ =>
    ActorFlow.actorRef(out => {
      supervisorRef ! ModeratoConnected(out)
      ModeratorActor.props(out, supervisorRef)
    })
  }

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



  class SpeakerActor(out: ActorRef, supervisor: ActorRef) extends Actor {

    import scala.concurrent.duration._

    val cancellable: Cancellable =
      system.scheduler.schedule(0.microseconds, 5.second, out , "speaking")

    def receive: Receive = {
      case msg: String => supervisor ! SpeakerSays("The speaker says: " + msg)
      case ModeratorNotConnected(m) => out ! m
    }

  }

  object SpeakerActor {
    def props(out: ActorRef, supervisor: ActorRef) = Props(new SpeakerActor(out, supervisor))
  }



  class ModeratorActor(out: ActorRef, supervisor: ActorRef) extends Actor {

    import scala.concurrent.duration._

    val cancellable: Cancellable =
      system.scheduler.schedule(0.microseconds, 5.second, out , "moderating")

    def receive: Receive = {
      case msg: String => supervisor ! ModeratorSays("The moderator says: " + msg)
      case SpeakerNotConnected(m) => out ! m
    }

  }

  object ModeratorActor {
    def props(out: ActorRef, supervisor: ActorRef) = Props(new ModeratorActor(out, supervisor))
  }


}