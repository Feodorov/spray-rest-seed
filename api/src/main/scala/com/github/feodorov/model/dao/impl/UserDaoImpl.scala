package com.github.feodorov.model.dao.impl

import com.github.feodorov.model.Models.User
import com.github.feodorov.model.dao.UserDao
import sorm.{Persisted, Instance}

/**
 * @author kfeodorov 
 * @since 31.10.14
 */
private[model] trait UserDaoImpl extends UserDao { db: Instance =>

  def getUserByName(name: String): Option[User with Persisted] = db.query[User].whereEqual("name", name).fetchOne()

}
