package com.ldz.external.inter

import com.ldz.enumeration.ExternalMusicKey

/**
  * Created by Loic on 14/06/2017.
  */
trait ISoundCloundService {
  def getMusicIdFromRessource(ressource: String): String

  def getMusicparametersFromRessource(ressource: String): Seq[Tuple2[ExternalMusicKey.Value, String]]

  def getIframeRessourceFromMusicId(musicId: String): String
}
