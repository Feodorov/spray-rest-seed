package com.github.feodorov.backend

import com.github.feodorov.model.Api
import spray.routing.{HttpService, Route}

import com.github.feodorov.model.Models._

trait SprayService extends HttpService {

  val api: Api

  import JsonProtocol._
  import SprayJsonSupport._

  def route : Route =
    path("cat" / Segment) { name: String =>
      parameters('username.as[String]) { (username) =>
        get {
          complete {
            api.getCatsByName(name).filter(_.owner.name == username).toList.map(unmixCat)
          }
        }
      }
    } ~ path("cats") {
      get {
        parameters('username.as[String]) { (username) =>
          complete {
            val allCats: List[Cat] = api.selectAll[Cat].map(unmixCat).toList

            api.getUserByName(username) match {
              case Some(user) if user.profile.name == "admin" => allCats
              case Some(user) if user.profile.name == "user" => allCats.filter(_.owner.name == username)
              case _ => List.empty[Cat]
            }
          }
        }
      }
    }
}
