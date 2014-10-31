package com.github.feodorov.model.dao

import com.github.feodorov.model.Models.Cat
import sorm.Persisted

/**
 * @author kfeodorov 
 * @since 31.10.14
 */
private[model] trait CatDao {

  def getCatsByName(name: String): Stream[Cat with Persisted]

  def getCatsByOwner(owner: String): Stream[Cat with Persisted]

}
