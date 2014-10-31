package com.github.feodorov.model

import org.scalatest.{Matchers, FlatSpec}


/**
 * @author kfeodorov 
 * @since 31.10.14
 */
import sorm._
import Models._

class Db extends Instance(
  entities = Set(Entity[Cat](), Entity[User](), Entity[Profile]()),
  url = "jdbc:h2:mem:test",
  initMode = InitMode.Create
)

class ApiTest extends FlatSpec with Matchers {

  def populateDb(api: Api) = {
    val adminProfile = api.insert(Profile(name = "admin"))
    val userProfile = api.insert(Profile(name = "user"))

    val adminUser = api.insert(User(name = "Admin 1", profile = adminProfile))
    val normalUser = api.insert(User(name = "Normal User 1", profile = userProfile))

    api.insertAll(IndexedSeq(Cat(name = "Barsik", age = 5, owner = adminUser), Cat(name = "Murzik", age = 6, owner = normalUser)))
  }

  "Api" should "work" in {

    val api = new Db with ApiImpl
    populateDb(api)

    api.selectAll[Cat] should have size 2
    api.selectAll[User] should have size 2
    api.selectAll[Profile] should have size 2

    api.getUserByName("Admin 1").map(_.name) should be (Some("Admin 1"))
    api.getUserByName("Admin 1").map(_.profile.name) should be (Some("admin"))

    api.getCatsByName("Barsik") should have size 1
  }
}
