package com.ldz.external.api.model

import com.ldz.external.api.enumeration.ExternalMusicKey

/**
  * Created by Loic on 19/06/2017.
  */
case class ExternalMusic(musicId: String, iframeURL: String, musicKey: Map[ExternalMusicKey.Value, String]) extends Serializable {

}
