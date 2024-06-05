package com.nashtech.app

import akka.actor.{ActorRef, ActorSystem}
import akka.pattern.ask
import akka.util.Timeout
import com.nashtech.actor.UserActor
import com.nashtech.model.ActorModel.{AddUser, DeleteUser, GetAllUser, GetUser}
import com.nashtech.model.User
import com.nashtech.service.UserService

import scala.concurrent.duration.DurationInt
import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}
import scala.util.{Failure, Success}

object Driver extends App {

  implicit val ec: ExecutionContextExecutor = ExecutionContext.global
  implicit val timeout: Timeout = Timeout(10.seconds)
  private val system = ActorSystem("user")

  private val userService = new UserService

  private val userData = Seq(
    User(101, "Manish", "Mishra", 7878675645L),
    User(102, "Ravi", "Kumar", 6578908765L),
    User(103, "Rohit", "Sharma", 9090898978L)
  )

  private val userActor: ActorRef = system.actorOf(UserActor.props(userService), "userActor")

  private val addNewUser = userActor ? AddUser(userData)

  addNewUser.onComplete {
    case Failure(exception) => println("\nGet Exception while processing")
    case Success(value) => println("\nAdded new User :" + value)
  }

  private val getNotExistedUser = userActor ? GetUser(104)
  getNotExistedUser.onComplete {
    case Failure(exception) => println("\nGet Exception while processing")
    case Success(value) => println("\nGet Non Existing User :" + value)
  }

  private val getExistingUser = userActor ? GetUser(101)
  getExistingUser.onComplete {
    case Failure(exception) => println("\nGet Exception while processing")
    case Success(value) => println("\nGet Existing User :" + value)
  }

  private val getAllUser = userActor ? GetAllUser
  getAllUser.onComplete {
    case Failure(exception) => println("\nGet Exception while processing")
    case Success(value) => println("\nAll User :" + value)
  }

  private val deleteNonExistingUser = userActor ? DeleteUser(104)
  deleteNonExistingUser.onComplete {
    case Failure(exception) => println("\nGet Exception while processing")
    case Success(value) => println("\nDelete Non Existing:" + value)
  }

  private val deleteExistingUser = userActor ? DeleteUser(101)
  deleteExistingUser.onComplete {
    case Failure(exception) => println("\nGet Exception while processing")
    case Success(value) => println("\nDelete Existing:" + value)
  }

  private val getAllUserAgain = userActor ? GetAllUser
  getAllUserAgain.onComplete {
    case Failure(exception) => println("\nGet Exception while processing")
    case Success(value) => println("\nAll User:" + value)
  }

}
