package controllers

import javax.inject.Inject

import akka.actor.{ActorRef, ActorSystem}
import akka.stream.Materializer
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.streams.ActorFlow
import play.api.mvc._

class Application @Inject() (implicit system: ActorSystem, materializer: Materializer) extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  import akka.actor._


  def socket = WebSocket.accept[String, String] { request =>
    ActorFlow.actorRef(out => MyWebSocketActor.props(out))
  }

  class MyWebSocketActor(out: ActorRef) extends Actor {

    import scala.concurrent.duration._

    val cancellable = system.scheduler.schedule(
      0.microseconds, 5.second, out , "tick")

    def receive = {
      case msg: String =>
        out ! ("You think you cool?: " + msg)
    }
  }

  object MyWebSocketActor {
    def props(out: ActorRef) = Props(new MyWebSocketActor(out))
  }

}