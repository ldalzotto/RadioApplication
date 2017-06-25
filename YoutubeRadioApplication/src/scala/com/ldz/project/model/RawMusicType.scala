package com.ldz.project.model

import com.ldz.enumeration.ExternalMusicKey

import scala.beans.BeanProperty

/**
  * Created by Loic on 25/06/2017.
  */
case class RawMusicType(@BeanProperty musicType: String,
                        @BeanProperty sourceUrl: String,
                        @BeanProperty musicParameters: java.util.Map[ExternalMusicKey.Value, String]) {

    def this() {
      this(null, null, null)
    }

}
