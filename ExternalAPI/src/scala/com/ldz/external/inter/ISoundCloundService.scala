package com.ldz.external.inter

/**
  * Created by Loic on 14/06/2017.
  */
trait ISoundCloundService {
  def getMusicIdFromRessource(ressource: String): String

  def getIframeRessourceFromMusicId(musicId: String): String
}
