package chapter6

import org.scalatest.FunSuite

/**
  * Created by osocron on 3/29/16.
  */
class CommandTest extends FunSuite {

  test("Remote control should turn on the light"){
    SimpleRemoteControl.setCommand(new LightOnCommand(new Light))
    assert(SimpleRemoteControl.buttonWasPressed() === println("Light On!"))
  }

  test("Remote control should open garage door"){
    SimpleRemoteControl.setCommand(new GarageDoorCommand(new GarageDoor))
    assert(SimpleRemoteControl.buttonWasPressed() === println("Garage door open!"))
  }

}
