package com.nashtech.model

object ActorModel {
  case class AddUser(user: Seq[User])

  case class GetUser(id: Int)
  
  case object GetAllUser

  case class DeleteUser(id: Int)
  
  case object JobSchedule
}
