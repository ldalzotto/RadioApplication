package com.ldz.external.inter

import com.ldz.external.api.enumeration.ExternalMusicKey

/**
  * Created by Loic on 14/06/2017.
  */
trait ISoundCloundService {
  def getMusicIdFromRessource(ressource: String): String

  def getMusicparametersFromRessource(ressource: String): Seq[Tuple2[String, ExternalMusicKey.Value]]

  def getIframeRessourceFromMusicId(musicId: String): String
}
