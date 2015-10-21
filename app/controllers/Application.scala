package controllers

import play.api.libs.json._
import play.api.mvc._
import models.Book._
import models.Todo._
import play.api.data.Form._

object Application extends Controller {

  def index = Action {
      //Ok("test")
      Ok(views.html.index())
  }

  def listBooks = Action {
    Ok(Json.toJson(books))
  }
  
  //val todoForm: Form[Todo] = Form {
  //    mapping(
  //      "name"     -> text
  //    ){Todo.apply}(Todo.unapply)
  //}
  
  //def addTodo = Action { implicit request => 
  //  val todo = todoForm.bindFromRequest.get
  //  DB.save(todo)
  //  Redirect(routes.Application.index())
  //}
  
  def saveBook = Action(BodyParsers.parse.json) { request =>
    val b = request.body.validate[Book]
    b.fold(
      errors => {
        BadRequest(Json.obj("status" -> "OK", "message" -> JsError.toFlatJson(errors)))
      },
      book => {
        addBook(book)
        Ok(Json.obj("status" -> "OK"))
      }
    )
  }
  
  def listTodos = Action {
    Ok(Json.toJson(todos))
  }

  def saveTodo = Action(BodyParsers.parse.json) { request =>
    val b = request.body.validate[Todo]
    b.fold(
      errors => {
        BadRequest(Json.obj("status" -> "OK", "message" -> JsError.toFlatJson(errors)))
      },
      todo => {
        addTodo(todo)
        Ok(Json.obj("status" -> "OK"))
      }
    )
  }
}
