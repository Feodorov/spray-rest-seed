package com.github.feodorov.backend

import akka.actor.Actor
import com.github.feodorov.model.Api

class HttpActor(apiImpl: Api) extends Actor with SprayService {
  override val api: Api = apiImpl

  def actorRefFactory = context
  def receive = runRoute(route)
}