package model

/**
  * Created by osocron on 4/15/16.
  */
case class Task(id: Int, name: String)

object Task {

  private var taskList: List[Task] = List()

  def all: List[Task] = taskList

  def add(name: String) = {
    val newId = taskList.length match {
      case 0 => 1
      case _ => taskList.last.id + 1
    }
    taskList = taskList ++ List(Task(newId, name))
  }

  def delete(id: Int) = taskList = taskList.filterNot(_.id == id)

}
