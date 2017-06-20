package com.ldz.external.api.model

import com.ldz.external.api.enumeration.ExternalMusicKey

import scala.beans.BeanProperty

/**
  * Created by Loic on 19/06/2017.
  */
case class ExternalMusicDTO(@BeanProperty musicId: String, @BeanProperty iframeURL: String, @BeanProperty musicKey: Map[ExternalMusicKey.Value, String]) {

}
