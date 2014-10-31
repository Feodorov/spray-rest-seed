package com.github.feodorov.model.dao.impl

import com.github.feodorov.model.dao.CommonDao
import sorm.{Persisted, Instance}


/**
 * @author kfeodorov 
 * @since 31.10.14
 */
private[model] trait CommonDaoImpl extends CommonDao { db: Instance  =>

  def selectAll[T <: scala.AnyRef](implicit evidence: scala.reflect.runtime.universe.TypeTag[T]): Stream[T with Persisted] = db.query[T].fetch()

  def insertAll[T <: scala.AnyRef](data: IndexedSeq[T])(implicit evidence: scala.reflect.runtime.universe.TypeTag[T]): IndexedSeq[T with Persisted] = data.map(db.save(_))

  def insert[T <: scala.AnyRef](data: T)(implicit evidence: scala.reflect.runtime.universe.TypeTag[T]): T with Persisted = db.save[T](data)

}
