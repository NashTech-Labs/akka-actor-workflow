package com.nashtech.service

import com.nashtech.model.User

class UserService {

  private var userDetails: Seq[User] = Seq.empty

  def addUsers(user: Seq[User]): Option[String] = {
    if(user.nonEmpty) {
      userDetails ++= user
      Some("Added New User")
    }
    else None
  }

  def getUser(userId: Int): Option[Seq[User]] = {
    val user = userDetails.filter(user => user.id == userId)
    if(user.nonEmpty) Some(user)
    else None
  }

  def getAllUser: Seq[User] = {
    userDetails
  }

  def deleteUser(userId: Int): Option[String] = {
    val user = userDetails.filterNot(user => user.id == userId)
    if (user.nonEmpty) {
      userDetails = user
      None
    }
    else Some("Deleted User Data")
  }

}
