import org.json.simple.JSONArray

import scalaj.http._
import org.json.simple.parser.JSONParser

object NYTimesAPITest {

  def main(args: Array[String]) {
    val response: HttpResponse[String] = Http(
      "http://api.nytimes.com/svc/search/v2/articlesearch.json").params(
      Seq("q"->"pokemon","f1"->"headline","page"->"2","api-key"->"9ad2a2c52acaa59bfa9d518e01d314fa:18:74443476")
    ).asString
    println(response.toString)
    val parser = new JSONParser
    val obj: Object = parser.parse(response.body)
    println(obj.toString)
  }

}
