package fpinscala.parsers

/**
  * Created by osocron on 10/17/16.
  */
trait Parsers[ParseError, Parser[_]] {
  def run[A](p: Parser[A])(input: String): Either[ParseError, A]
  def char(c: Char): Parser[Char]
}
