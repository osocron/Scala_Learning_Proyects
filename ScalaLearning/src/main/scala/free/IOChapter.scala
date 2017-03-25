package free

import scala.io.StdIn
import scalaz.Monad


object IOChapter {

  case class Player(name: String, score: Int)

  def winnerMsg(p: Player): String = p.name + " is the winner!"

  //You cant's kid me, this is the Free Monad!
  trait IO[F[_],+A]
  case class Pure[F[_],+A](get: A) extends IO[F,A]
  case class Request[F[_],I,+A](expr: F[I], recieve: I => IO[F,A]) extends IO[F,A]

  trait Run[F[_]] {
    def apply[A](expr: F[A]): (A, Run[F])
  }

  trait Console[A]
  case object ReadLine extends Console[Option[String]]
  case class PrintLine(s: String) extends Console[Unit]


  sealed trait TailRec[A] {
    def flatMap[B](f: A => TailRec[B]): TailRec[B] = FlatMap(this, f)
    def map[B](f: A => B): TailRec[B] = flatMap(f andThen (Return(_)))
  }
  case class Return[A](a: A) extends TailRec[A]
  case class Suspend[A](resume: () => A) extends TailRec[A]
  case class FlatMap[A,B](sub: TailRec[A], k: A => TailRec[B]) extends TailRec[B]

  object TailRec extends Monad[TailRec] {
    override def bind[A, B](fa: TailRec[A])(f: (A) => TailRec[B]): TailRec[B] = fa flatMap f
    override def point[A](a: => A): TailRec[A] = Return(a)
  }

}

object NextIO {

  trait IO[F[_], +A]
  case class Pure[F[_], +A](get: A) extends IO[F,A]
  case class Request[F[_],I,+A](expr: F[I],
                                receive: I => IO[F,A]) extends IO[F,A]
  case class BindMore[F[_],A,+B](force: () => IO[F,A],
                                 f: A => IO[F,B]) extends IO[F,B]
  case class BindRequest[F[_],I,A,+B](expr: F[I], receive: I => IO[F,A],
                                      f: A => IO[F,B]) extends IO[F,B]
  case class More[F[_],A](force: () => IO[F,A]) extends IO[F,A]

  trait Run[F[_]] {
    def apply[A](expr: F[A]): (A, Run[F])
  }

}

object NextState {

  sealed trait ST[S,A] { self =>
    protected def run(s: S): (A,S)
    def map[B](f: A => B): ST[S,B] = new ST[S,B] {
      def run(s: S): (B, S) = {
        val (a, s1) = self.run(s)
        (f(a), s1)
      }
    }
    def flatMap[B](f: A => ST[S,B]): ST[S,B] = new ST[S,B] {
      def run(s: S): (B, S) = {
        val (a, s1) = self.run(s)
        f(a).run(s1)
      }
    }
  }

  object ST {
    def apply[S,A](a: => A): ST[S, A] = {
      lazy val memo = a
      new ST[S,A] {
        def run(s: S): (A, S) = (memo, s)
      }
    }
    def runST[A](st: RunnableST[A]): A = st[Null].run(null)._1
  }

  sealed trait STRef[S,A] {
    protected var cell: A
    def read: ST[S,A] = ST(cell)
    def write(a: => A): ST[S,Unit] = new ST[S,Unit] {
      def run(s: S): (Unit, S) = {
        cell = a
        ((), s)
      }
    }
  }

  object STRef {
    def apply[S,A](a: A): ST[S, STRef[S,A]] = ST(new STRef[S,A] {
      var cell: A = a
    })
  }

  //Program
  for {
    r1 <- STRef[Nothing,Int](1)
    r2 <- STRef[Nothing,Int](1)
    x <- r1.read
    y <- r2.read
    _ <- r1.write(y+1)
    _ <- r2.write(x+1)
    a <- r1.read
    b <- r2.read
  } yield (a,b)

  //How to run it?

  trait RunnableST[A] {
    def apply[S]: ST[S,A]
  }

  //poly version of the program

  val p = new RunnableST[(Int, Int)] {
    def apply[S]: ST[S, (Int, Int)] = for {
      r1 <- STRef(1)
      r2 <- STRef(2)
      x <- r1.read
      y <- r2.read
      _ <- r1.write(y+1)
      _ <- r2.write(x+1)
      a <- r1.read
      b <- r2.read
    } yield (a,b)
  }

}

object Test extends App {
  import NextState._
  println(ST.runST(p))
}