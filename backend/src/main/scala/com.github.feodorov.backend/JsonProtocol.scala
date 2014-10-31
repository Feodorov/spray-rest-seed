package com.github.feodorov.backend

import com.github.feodorov.model.Models.{Cat, User, Profile}
import spray.json.DefaultJsonProtocol

/**
 * @author kfeodorov 
 * @since 31.10.14
 */
object JsonProtocol extends DefaultJsonProtocol {
  implicit val profile = jsonFormat1(Profile)
  implicit val user = jsonFormat2(User)
  implicit val cat = jsonFormat3(Cat)

  def unmixCat(catPersisted: Cat with sorm.Persisted): Cat = {
    val profile = catPersisted.owner.profile.asInstanceOf[Profile with sorm.Persisted].mixoutPersisted[Profile]._2
    val user = catPersisted.owner.asInstanceOf[User with sorm.Persisted].mixoutPersisted[User]._2
    catPersisted.mixoutPersisted[Cat]._2.copy(owner = user.copy(profile = profile))
  }
}