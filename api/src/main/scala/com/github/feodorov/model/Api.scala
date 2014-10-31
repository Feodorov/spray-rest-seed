package com.github.feodorov.model

import com.github.feodorov.model.dao.impl.{CommonDaoImpl, UserDaoImpl, CatDaoImpl}
import com.github.feodorov.model.dao.{CatDao, CommonDao, UserDao}

/**
 * @author kfeodorov 
 * @since 31.10.14
 */

trait Api extends CatDao with UserDao with CommonDao

trait ApiImpl extends sorm.Instance with Api with CatDaoImpl with UserDaoImpl with CommonDaoImpl
