package com.github.feodorov.backend

import akka.actor.{Props, ActorSystem}
import akka.io.IO
import com.github.feodorov.model.{Api, ApiImpl}
import com.github.feodorov.model.Models.{Profile, User, Cat}
import sorm.{InitMode, Entity, Instance}
import spray.can.Http


object Main extends App {

  def populateDb(api: Api) = {
    val adminProfile = api.insert(Profile(name = "admin"))
    val userProfile = api.insert(Profile(name = "user"))

    val adminUser = api.insert(User(name = "Admin", profile = adminProfile))
    val normalUser = api.insert(User(name = "User", profile = userProfile))

    api.insertAll(IndexedSeq(Cat(name = "Barsik", age = 5, owner = adminUser), Cat(name = "Murzik", age = 6, owner = normalUser)))
  }

  private def waitForExit() = {
    def waitEOF(): Unit = Console.readLine() match {
      case "exit" => system.shutdown()
      case _ => waitEOF()
    }
    waitEOF()
  }

  implicit val system = ActorSystem("actor-system")

  val api = new Instance(
    entities = Set(Entity[Cat](), Entity[User](), Entity[Profile]()),
    url = system.settings.config getString "db.url",
    initMode = InitMode.Create
  ) with ApiImpl

  populateDb(api)

  val service = system.actorOf(Props(new HttpActor(api)), "service-actor")
  val host = system.settings.config getString "service.host"
  val port = system.settings.config getInt "service.port"
  IO(Http) ! Http.Bind(service, host, port)

  Console.println(s"Server started ${system.name}, $host:$port")
  Console.println("Type `exit` to exit....")

  waitForExit()
  system.shutdown()
}
