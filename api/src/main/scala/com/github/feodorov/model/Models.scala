package com.github.feodorov.model

/**
 * @author kfeodorov 
 * @since 31.10.14
 */
object Models {
  case class Cat(name: String, age: Int, owner: User)

  case class Profile(name: String)

  case class User(name: String, profile: Profile)
}
