package handlers

import scalaj.http._
import scala.xml._

/**
  * Created by ventas on 15/07/2016.
  */
object YRequestHandler {

  def sendRequest(token: Token, woeid: String): HttpResponse[String] =
    Http("http://weather.yahooapis.com/forecastrss").param("w", woeid).oauth(token).asString

  def parseRequest(response: HttpResponse[String]) = XML.loadString(response.body)

}
