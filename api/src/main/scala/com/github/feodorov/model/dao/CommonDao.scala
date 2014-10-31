package com.github.feodorov.model.dao

import sorm.Persisted

/**
 * @author kfeodorov 
 * @since 31.10.14
 */
private[model] trait CommonDao {

  def selectAll[T <: scala.AnyRef](implicit evidence: scala.reflect.runtime.universe.TypeTag[T]): Stream[T with Persisted]

  def insertAll[T <: scala.AnyRef](data: IndexedSeq[T])(implicit evidence: scala.reflect.runtime.universe.TypeTag[T]): IndexedSeq[T with Persisted]

  def insert[T <: scala.AnyRef](data: T)(implicit evidence: scala.reflect.runtime.universe.TypeTag[T]): T with Persisted
}
