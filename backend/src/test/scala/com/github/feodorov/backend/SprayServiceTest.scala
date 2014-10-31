package com.github.feodorov.backend

import com.github.feodorov.model.{ApiImpl, Api}
import com.github.feodorov.model.Models.{Profile, User, Cat}
import org.scalatest.{Matchers, FlatSpec}
import sorm.{InitMode, Entity, Instance}
import spray.http.StatusCodes
import spray.testkit.ScalatestRouteTest

/**
 * @author kfeodorov 
 * @since 31.10.14
 */
class SprayServiceTest extends FlatSpec with Matchers with ScalatestRouteTest with SprayService {

  def actorRefFactory = system

  override val api: Api = new Instance(
    entities = Set(Entity[Cat](), Entity[User](), Entity[Profile]()),
    url = system.settings.config getString "db.url",
    initMode = InitMode.Create
  ) with ApiImpl

  Main.populateDb(api)

  import JsonProtocol._
  import SprayJsonSupport._

  "The service" should "return list of cats" in {
    Get("/cats?username=Admin") ~> route ~> check {
      responseAs[List[Cat]] should be (Seq(Cat("Barsik", 5, User("Admin", Profile("admin"))), Cat("Murzik", 6, User("User", Profile("user")))))
      response.status should be (StatusCodes.OK)
    }
   }
}
