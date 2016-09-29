package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {

  def example() = Action {
    Ok(views.html.example("Example HTML"))
  }

  def titus() = Action {
    implicit request =>
    val listaTitus = List[String]("ferguson","yackal","thermus","almair")
      Ok(views.html.titus("Harbringer",listaTitus))
  }

  def starbuzzIndex() = Action {
    Ok(views.html.starbuzz.starbuzzIndex("Starbuzz!"))
  }

  def starbuzzMission() = Action {
    Ok(views.html.starbuzz.mission("Starbuzz Mission"))
  }

  def lounge() = Action {
    Ok(views.html.lounge.lounge("Webtown Lounge"))
  }

  def elixir() = Action {
    Ok(views.html.lounge.beverages.elixir("Elixirs"))
  }

  def directions() = Action {
    Ok(views.html.lounge.about.directions("Directions"))
  }

  def journal() = Action {
    Ok(views.html.segway.journal("My trip around the USA on a Segway"))
  }
}