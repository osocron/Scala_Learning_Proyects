import java.net.URL
import scala.util.Try

/**
  * Working with Try Catch in Scala. It's beautiful...
  * @param url
  * @return
  */

def parseURL(url: String): Try[URL] = Try(new URL(url))

parseURL("http://danielwestheide.com")

parseURL("sjflksndflk")

parseURL("http://danielwestheide.com").map(_.getProtocol)

parseURL("garbage").map(_.getProtocol)

/**
  *Mapping a Try[A] that is a Success[A] to a Try[B] results in a Success[B].
  * If it’s a Failure[A], the resulting Try[B] will be a Failure[B], on the other
  * hand, containing the same exception as the Failure[A]:
  *
  * If you chain multiple map operations, this will result in a nested Try
  * structure, which is usually not what you want. Consider this method that
  * returns an input stream for a given URL:
  */

import java.io.InputStream
def wrongInputStreamForURL(url: String): Try[Try[Try[InputStream]]] =
  parseURL(url).map { u =>
    Try(u.openConnection()).map(conn => Try(conn.getInputStream))
  }

/**
  * Since the anonymous functions passed to the two map calls each return a Try,
  * the return type is a Try[Try[Try[InputStream]]]. This is where the fact
  * that you can flatMap a Try comes in handy. The flatMap method on a Try[A]
  * expects to be passed a function that receives an A and returns a Try[B].
  * If our Try[A] instance is already a Failure[A], that failure is returned
  * as a Failure[B], simply passing along the wrapped exception along the
  * chain. If our Try[A] is a Success[A], flatMap unpacks the A value in it
  * and maps it to a Try[B] by passing this value to the mapping function.
  * This means that we can basically create a pipeline of operations that
  * require the values carried over in Success instances by chaining an
  * arbitrary number of flatMap calls. Any exceptions that happen along the
  * way are wrapped in a Failure, which means that the end result of the
  * chain of operations is a Failure, too. Let’s rewrite the inputStreamForURL
  * method from the previous example, this time resorting to flatMap:
  */

def inputStreamForURL(url: String): Try[InputStream] =
  parseURL(url).flatMap { u =>
    Try(u.openConnection()).flatMap(conn => Try(conn.getInputStream))
  }

/**
  * Filter and foreach
  *
  * Of course, you can also filter a Try or call foreach on it. Both work
  * exactly as you would expect after having learned about Option.
  * The filter method returns a Failure if the Try on which it is called
  * is already a Failure or if the predicate passed to it returns false
  * (in which case the wrapped exception is a NoSuchElementException).
  * If the Try on which it is called is a Success and the predicate
  * returns true, that Succcess instance is returned unchanged:
  */

def parseHttpURL(url: String) = parseURL(url).filter(_.getProtocol == "http")
parseHttpURL("http://apache.openmirror.de") // results in a Success[URL]
parseHttpURL("ftp://mirror.netcologne.de/apache.org") // results in a Failure[URL]

/**
  * The function passed to foreach is executed only if the Try is a Success,
  * which allows you to execute a side-effect. The function passed to foreach
  * is executed exactly once in that case, being passed the value wrapped by
  * the Success:
  */

parseHttpURL("http://danielwestheide.com").foreach(println)

/**
  * For comprehensions
  *
  * The support for flatMap, map and filter means that you can also use for
  * comprehensions in order to chain operations on Try instances. Usually,
  * this results in more readable code. To demonstrate this, let’s implement
  * a method that returns the content of a web page with a given URL using for
  * comprehensions.
  */

import scala.io.Source
def getURLContent(url: String): Try[Iterator[String]] =
  for {
    url <- parseURL(url)
    connection <- Try(url.openConnection())
    is <- Try(connection.getInputStream)
    source = Source.fromInputStream(is)
  } yield source.getLines()

/**
  * There are three places where things can go wrong, all of them covered
  * by usage of the Try type. First, the already implemented parseURL method
  * returns a Try[URL]. Only if this is a Success[URL], we will try to open a
  * connection and create a new input stream from it. If opening the connection
  * and creating the input stream succeeds, we continue, finally yielding the
  * lines of the web page. Since we effectively chain multiple flatMap calls
  * in this for comprehension, the result type is a flat Try[Iterator[String]].
  */

/**
  * Pattern Matching
  *
  * At some point in your code, you will often want to know whether a Try
  * instance you have received as the result of some computation represents
  * a success or not and execute different code branches depending on the
  * result. Usually, this is where you will make use of pattern matching.
  * This is easily possible because both Success and Failure are case classes.
  * We want to render the requested page if it could be retrieved, or print an
  * error message if that was not possible:
  */

import scala.util.Success
import scala.util.Failure
getURLContent("http://danielwestheide.com/foo") match {
  case Success(lines) => lines.foreach(println)
  case Failure(ex) => println(s"Problem rendering URL content: ${ex.getMessage}")
}

/**
  * Recovering from a Failure
  *
  * If you want to establish some kind of default behaviour in the case of
  * a Failure, you don’t have to use getOrElse. An alternative is recover,
  * which expects a partial function and returns another Try. If recover is
  * called on a Success instance, that instance is returned as is. Otherwise,
  * if the partial function is defined for the given Failure instance,
  * its result is returned as a Success.
  *
  * Let’s put this to use in order to print a different message depending on
  * the type of the wrapped exception:
  */

import java.net.MalformedURLException
import java.io.FileNotFoundException
val content = getURLContent("garbage") recover {
  case e: FileNotFoundException => Iterator("Requested page does not exist")
  case e: MalformedURLException => Iterator("Please make sure to enter a valid URL")
  case _ => Iterator("An unexpected error has occurred. We are so sorry!")
}

/**
  * We could now safely get the wrapped value on the Try[Iterator[String]]
  * that we assigned to content, because we know that it must be a Success.
  * Calling content.get.foreach(println) would result in Please make sure to
  * enter a valid URL being printed to the console.
  */

content.get.foreach(println)
