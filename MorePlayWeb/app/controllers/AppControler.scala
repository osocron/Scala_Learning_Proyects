package controllers

import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
/**
  * Created by osocron on 6/20/16.
  */
object AppControler extends Controller {

  val enquiryForm = Form(
    tuple(
      "emailId" -> email,
      "userName" -> optional(text),
      "question" -> nonEmptyText
    )
  )


}
