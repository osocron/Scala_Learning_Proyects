package handlers

import org.scalatest.FunSuite

import scalaj.http.Token

/**
  * Created by ventas on 15/07/2016.
  */
class YRequestHandler$Test extends FunSuite {

  val yHandler = YRequestHandler
  val consumer = Token(
    "dj0yJmk9Zmg4RG11YXlIaHNGJmQ9WVdrOWNFRllObFp6TkRnbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmeD05Yg--",
    "4080d9428cccf5e49c3aaff0f8b62f554b323fdc"
  )
  val response = yHandler.sendRequest(consumer, "2502265")
  val parsedResponse = yHandler.parseRequest(response)

  test("http GET request to Yahoo should return status code 200 in sendRequest()") {
    assert(200 == response.code)
  }

  test("method parseResponse should return an XML object whose child language should be en-us") {
    assert("en-us" == (parsedResponse \\ "language").text )
  }

}
