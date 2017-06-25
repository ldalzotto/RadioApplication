package com.ldz.external.api.model

import com.ldz.enumeration.ExternalMusicKey

import java.util.Map
import scala.beans.BeanProperty

/**
  * Created by Loic on 19/06/2017.
  */
case class ExternalMusicDTO(@BeanProperty iframeURL: String,
                            @BeanProperty musicKey: Map[ExternalMusicKey.Value, String]) {

}
