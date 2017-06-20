package com.ldz.music.manager.service

import com.ldz.music.manager.constants.MusicTypes
import com.ldz.music.manager.model.bo.UserMusicStatusBO

/**
  * Created by Loic on 20/06/2017.
  */
trait IMusicManagerService {
  def addUserMusicStatus(userMusicStatusBO: UserMusicStatusBO): Unit

  def deleteUserMusicStatusFromUsername(username: String): Unit

  def getMusicStatusFromUsername(username: String): UserMusicStatusBO

  def getSourceurlFromUrlAndMusicType(url: String, musicType: MusicTypes.Value): String

  def postMusicFromUsernameAndSourceurl(username: String, sourceUrl: String): Boolean
}
