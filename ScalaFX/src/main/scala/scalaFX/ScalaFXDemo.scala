package scalaFX

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage


/**
  * Created by osocron on 9/02/16.
  */
class ScalaFXDemo extends Application {

  override def start(primaryStage: Stage) {
    primaryStage.setTitle("Sup!")
    val root: Parent = FXMLLoader.load(getClass.getResource("Login.fxml"))
    primaryStage.setScene(new Scene(root, 700, 500))
    primaryStage.show()
  }
}

object Test {
  def main(args: Array[String]) {
    Application.launch(classOf[ScalaFXDemo], args: _*)
  }
}
