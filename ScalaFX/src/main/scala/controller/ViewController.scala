package controller

import javafx.application.Application
import javafx.fxml.FXML
import javafx.stage.Stage

import com.jfoenix.controls.JFXButton

import scalafx.scene.paint.Color

/**
  * Created by osocron on 4/7/16.
  */
class ViewController extends Application {

  @FXML
  var clickMeButton: JFXButton = _

  @FXML
  var btnMystery: JFXButton = _

  override def start(primaryStage: Stage): Unit = {}

  def onClick(): Unit = clickMeButton.getText match {
    case "Click Me" => {
      clickMeButton.setText("Don't click me!")
      clickMeButton.setRipplerFill(Color.Chartreuse)
    }
    case _ => clickMeButton.setText("Click Me"); clickMeButton.setRipplerFill(Color.Crimson)
  }

  def doMysteryAction(): Unit = {
    val stage: Stage = btnMystery.getScene.getWindow.asInstanceOf[Stage]
    stage.close()
  }

}
