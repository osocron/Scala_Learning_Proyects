package actors

import akka.actor.{Actor, Props}
import messages.{MapData, ReduceData, Result, WordCount}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import akka.routing.RoundRobinPool

/**
  * Created by osocron on 7/15/16.
  */
class MapActor extends Actor {

  val STOP_WORDS_LIST = List("a", "am", "an", "and", "are", "as", "at", "be",
    "do", "go", "if", "in", "is", "it", "of", "on", "the", "to")

  override def receive: Receive = {
    case message: String => sender ! evaluateExpression(message)
  }

  def evaluateExpression(line: String): MapData = MapData {
    line.split("""\s+""").foldLeft(ArrayBuffer.empty[WordCount]) {
      (index, word) =>
        if (!STOP_WORDS_LIST.contains(word.toLowerCase))
          index += WordCount(word.toLowerCase, 1)
        else
          index
    }
  }

}

class ReduceActor extends Actor {

  override def receive: Receive = {
    case MapData(dataList) => sender ! reduce(dataList)
  }

  def reduce(words: IndexedSeq[WordCount]): ReduceData = ReduceData {
    words.foldLeft(Map.empty[String, Int]) { (index, words) =>
      if (index contains words.word)
        index + (words.word -> (index(words.word) + 1))
      else
        index + (words.word -> 1)
    }
  }

}

class AggregateActor extends Actor {

  val finalReduceMap = new mutable.HashMap[String, Int]

  override def receive: Receive = {
    case ReduceData(reduceDataMap) => aggregateInMemoryReduce(reduceDataMap)
    case Result => sender ! finalReduceMap.toString()
  }

  def aggregateInMemoryReduce(reducedList: Map[String, Int]): Unit = {
    for ((key, value) <- reducedList) {
      if (finalReduceMap contains key)
        finalReduceMap(key) = value + finalReduceMap(key)
      else
        finalReduceMap += (key -> value)
    }
  }

}

class MasterActor extends Actor {

  val mapActor = context.actorOf(RoundRobinPool(5).props(Props[MapActor]), "map")
  val reduceActor = context.actorOf(RoundRobinPool(5).props(Props[ReduceActor]), "reduce")
  val aggregateActor = context.actorOf(Props[AggregateActor], "aggregate")

  override def receive: Receive = {
    case line: String => mapActor ! line
    case mapData: MapData => reduceActor ! mapData
    case reduceData: ReduceData => aggregateActor ! reduceData
    case Result => aggregateActor forward Result
  }
}
