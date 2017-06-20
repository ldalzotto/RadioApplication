package com.ldz.external.model

import com.ldz.external.api.enumeration.ExternalMusicKey

/**
  * Created by Loic on 19/06/2017.
  */
case class ExternalMusicBO(musicId: String, iframeURL: String, musicKey: Map[ExternalMusicKey.Value, String]) {

}
