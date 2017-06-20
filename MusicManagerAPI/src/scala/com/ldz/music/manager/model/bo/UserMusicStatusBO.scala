package com.ldz.music.manager.model.bo

import scala.beans.BeanProperty


/**
  * Created by Loic on 20/06/2017.
  */
case class UserMusicStatusBO(@BeanProperty username: String, @BeanProperty musicTypeBOs: Seq[MusicTypeBO]) {
}
