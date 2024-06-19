package com.nashtech.actor

import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorLogging, Props}
import com.nashtech.model.ActorModel.{AddUser, DeleteUser, GetAllUser, GetUser, JobSchedule}
import com.nashtech.service.UserService
import akka.pattern.pipe

import java.time.LocalDateTime
import scala.concurrent.{ExecutionContext, Future}

class UserActor(userService: UserService)(implicit val ec: ExecutionContext) extends Actor with ActorLogging {

  override def receive: Receive = {

    case AddUser(user) =>
      val result = Future(userService.addUsers(user))
      result.pipeTo(sender())

    case GetUser(id) =>
      val result = Future(userService.getUser(id))
      result.pipeTo(sender())

    case GetAllUser  =>
      val result = Future(userService.getAllUser)
      result.pipeTo(sender())

    case DeleteUser(id) =>
      val result = Future(userService.deleteUser(id))
      result.pipeTo(sender())

    case JobSchedule =>
      /* You can call your method that you want to run as per scheduled timing*/
      /* log.info(s"Your Job is Start Processing at :${LocalDateTime.now()}") */
      println(s"\nYour Job is Start Processing at :${LocalDateTime.now()}")

    case _ =>
      val result = Future("Not matched with Any Object")
      result.pipeTo(sender())
  }
}

object UserActor {
  def props(userService: UserService)(implicit ec: ExecutionContext): Props =
    Props(new UserActor(userService))
}

