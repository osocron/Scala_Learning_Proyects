package com.sisco.db

import akka.actor.Actor
import akka.event.Logging
import com.sisco.messages.SetRequest

import scala.collection.mutable

/**
  * Beginning with he book. Learning Akka.
  */
class SiscoDB extends Actor {

  val map = new mutable.HashMap[String, Object]
  val log = Logging(context.system, this)

  override def receive: Receive = {
    case SetRequest(key, value) => {
      log.info("recieved request - key: {} value: {}", key, value)
      map.put(key, value)
    }
    case o => log.info("recieved unknown message")
  }

}
