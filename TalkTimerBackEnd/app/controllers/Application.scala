package controllers

import javax.inject.Inject

import actors.{ModeratorActor, SpeakerActor, Supervisor}
import messages.ActorMessages._
import akka.actor._
import akka.stream.Materializer
import io.circe._
import play.api.libs.streams.ActorFlow
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global

class Application @Inject() (implicit system: ActorSystem,
                             materializer: Materializer) extends Controller {

  val supervisorRef: ActorRef = system.actorOf(Supervisor.props)

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def speakerSocket: WebSocket = WebSocket.accept[Json, Json] { _ =>
    ActorFlow.actorRef(out => {
      supervisorRef ! SpeakerConnected(out)
      SpeakerActor.props(out, supervisorRef, system)
    })
  }

  def moderatorSocket: WebSocket = WebSocket.accept[Json, Json] { _ =>
    ActorFlow.actorRef(out => {
      supervisorRef ! ModeratoConnected(out)
      ModeratorActor.props(out, supervisorRef, system)
    })
  }


}