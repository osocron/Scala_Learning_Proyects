package chapter6

/**
  * The Command Pattern encapsulates a request as an object, thereby letting
  * you parameterize other objects with different requests, queue or log requests,
  * and support undoable operations.
  */

trait Command {
  def execute(): Unit
}

class LightOnCommand(light: Light) extends Command {
  override def execute(): Unit = light.on()
}

class Light {
  def on(): Unit = println("Light On!")
}

class GarageDoorCommand(garageDoor: GarageDoor) extends Command {
  override def execute(): Unit = garageDoor.open(); garageDoor.lightOn()
}

class GarageDoor {
  def open(): Unit = println("Garage door open!")
  def lightOn() = println("Light on!")
}

object SimpleRemoteControl {
  var slot: Command = null
  def setCommand(command: Command) = slot = command
  def buttonWasPressed() = slot.execute()
}

