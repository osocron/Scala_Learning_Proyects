package controllers

import model.{Artist, Task}
import play.api._
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import views.html

class Application extends Controller {

  val enquiryForm = Form(
    tuple(
      "emailId" -> email,
      "username" -> optional(text),
      "question" -> nonEmptyText
    )
  )

  def index = Action {
    Redirect(routes.Application.tasks)
  }

  def tasks = Action {
    Ok(views.html.index(Task.all))
  }

  def newTask = Action(parse.urlFormEncoded) {
    implicit request =>
      Task.add(request.body.get("taskName").get.head)
      Redirect(routes.Application.index)
  }

  def deleteTask(id: Int) = Action {
    Task.delete(id)
    Ok
  }

  def listArtist = Action {
    Ok(views.html.home(Artist.fetch))
  }

  def fetchArtistByName(name: String) = Action {
    Ok(views.html.home(Artist.fetchByName(name)))
  }

  def search(name: String, country: String) = Action {
    val result = Artist.fetchByNameOrCountry(name, country)
    if(result.isEmpty) NoContent
    else Ok(views.html.home(result))
  }

  def search2(name: Option[String], country: String) = Action {
    val result = name match {
      case Some(n) => Artist.fetchByNameOrCountry(n, country)
      case None => Artist.fetchByCountry(country)
    }
    if (result.isEmpty) NoContent
    else Ok(views.html.home(result))
  }

  def subscribe = Action(parse.json) {
    request =>
      val reqData = request.body
      val emailId = (reqData \ "emailId").as[String]
      val interval = (reqData \ "interval").as[Int]
      Ok(s"added $emailId to subscriber's list and will send updat every $interval days\n")
  }


  def askUs = Action {
    implicit request =>
      Ok(views.html.askUs(enquiryForm))
  }

  def enquire = Action {
    implicit request =>
      enquiryForm.bindFromRequest.fold(
        errors => BadRequest(views.html.askUs(errors)),
        query => {
          println(query.toString)
          Redirect(routes.Application.tasks)
        }
      )
  }

}