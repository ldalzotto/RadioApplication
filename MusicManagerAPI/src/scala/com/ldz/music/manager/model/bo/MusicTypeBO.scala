package com.ldz.music.manager.model.bo

import com.ldz.music.manager.constants.MusicTypes

import scala.beans.BeanProperty

/**
  * Created by Loic on 20/06/2017.
  */
case class MusicTypeBO(@BeanProperty musicTypes: MusicTypes.Value, @BeanProperty sourceUrl: String) {

}
