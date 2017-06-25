package com.ldz.music.manager.service

import com.ldz.enumeration.ExternalMusicKey
import com.ldz.music.manager.constants.MusicTypes
import com.ldz.music.manager.model.bo.{MusicTypeBO, UserMusicStatusBO}

/**
  * Created by Loic on 20/06/2017.
  */
trait IMusicManagerService {
  def addUserMusicStatus(userMusicStatusBO: UserMusicStatusBO): Unit

  def deleteUserMusicStatusFromUsername(username: String): Unit

  def getMusicStatusFromUsername(username: String): UserMusicStatusBO

  def getMusicparametersFromUrlAndMusicType(url: String, musicType: MusicTypes.Value): MusicTypeBO

  def postMusicFromUsernameAndSourceurl(username: String, musicDetail: MusicTypeBO): MusicTypeBO
}
