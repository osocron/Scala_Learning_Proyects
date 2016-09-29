package chapter1

import akka.actor.Actor
import akka.actor.Actor.Receive
import chapter1.messages.{Chat, SetString, Subscribe}


class ChatActor extends Actor {

  override def receive: Receive = openChat(List())

  def openChat(members: List[String]): Receive = {

    case Subscribe(usr) => context.become(openChat(members :+ usr))

    case Chat(usr: String, msg: String) =>
      if (members.contains(usr)) println(msg)
      else sender ! SetString("Not subscribed")

  }

}
