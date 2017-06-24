package com.ldz.project.service.music

import com.ldz.music.manager.MusicTypeClient
import com.ldz.music.manager.model.UserMusicStatusDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
  * Created by ldalzotto on 23/06/2017.
  */
@Service
class MusicService extends IMusicService {

  @Autowired
  private val musicManagerClient: MusicTypeClient = null

  override def addMusicFromUrlAndMusicPlatform(username: String, url: String, musicPlatform: String): Boolean = {
    val iframeUrl = musicManagerClient.getRessourceUrlFromUrlAndMusicplatform(url, musicPlatform)
    musicManagerClient.postMusicFromSiteurl(username, iframeUrl.getBody).getBody
  }

  override def getMusicStatusOfCurrentClient(username: String): UserMusicStatusDTO = {
    musicManagerClient.getUserFromUsername(username).getBody()
  }
}
