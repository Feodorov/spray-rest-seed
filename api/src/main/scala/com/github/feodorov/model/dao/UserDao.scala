package com.github.feodorov.model.dao

import com.github.feodorov.model.Models.User
import sorm.Persisted

/**
 * @author kfeodorov 
 * @since 31.10.14
 */
private[model] trait UserDao {

  def getUserByName(name: String): Option[User with Persisted]

}
