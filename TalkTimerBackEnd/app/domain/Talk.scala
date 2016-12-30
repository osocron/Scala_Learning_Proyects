package domain

/**
  * Let's start with the problem domain.
  *
  * We are in the domain of Talks, speakers and timers. Wen a Talk starts a moderator
  * will indicate that a talk has started and it will set a time limit. The
  * speaker will recieve updates on how much time it has left before the talk is over,
  * and the speaker will be notified when the time has actually finished.
  *
  * The moderator can extend the time of the talk and it can also substract time from
  * the talk. The moderator can also end the time and can also send messages to the
  * speaker.
  *
  *
  * Now let's continue with the solution domain.
  *
  */
sealed trait Talk
case class OnGoing(speaker: Speaker, moderator: Moderator) extends Talk
case class Paused(timeLeft: Long) extends Talk
case object Over extends Talk

trait Moderator

trait Speaker

trait Timer

trait Update

trait Message

sealed trait TalkServices {

}

