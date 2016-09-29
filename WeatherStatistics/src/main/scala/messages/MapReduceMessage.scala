package messages

import scala.collection.mutable.ArrayBuffer

/**
  * Created by osocron on 7/15/16.
  */
sealed trait MapReduceMessage

case class WordCount(word: String, count: Int) extends MapReduceMessage

case class MapData(dataList: ArrayBuffer[WordCount])

case class ReduceData(reduceDataList: Map[String, Int])

case object Result extends MapReduceMessage
