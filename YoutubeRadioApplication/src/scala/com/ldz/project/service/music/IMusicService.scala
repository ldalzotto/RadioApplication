package com.ldz.project.service.music

import com.ldz.music.manager.model.UserMusicStatusDTO

/**
  * Created by ldalzotto on 23/06/2017.
  */
trait IMusicService {
  def addMusicFromUrlAndMusicPlatform(username: String, url: String, musicPlatform: String): Boolean

  def getMusicStatusOfCurrentClient(username: String): UserMusicStatusDTO

  def getAllDistinctArtistsFromClient(username: String): Seq[String]

  def getFilteredArtistsFromInput(username: String, musicInput: String): Seq[String]
}
