package com.github.feodorov.model.dao.impl

import com.github.feodorov.model.Models.Cat
import com.github.feodorov.model.dao.CatDao
import sorm.{Persisted, Instance}

/**
 * @author kfeodorov 
 * @since 31.10.14
 */
private[model] trait CatDaoImpl extends CatDao { db: Instance =>

  def getCatsByName(name: String): Stream[Cat with Persisted] = db.query[Cat].whereEqual("name", name).fetch()

  def getCatsByOwner(owner: String): Stream[Cat with Persisted] = db.query[Cat].whereEqual("owner.name", owner).fetch()
}
