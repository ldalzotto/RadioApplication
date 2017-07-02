package com.ldz.project.service.music

import com.ldz.enumeration.ExternalMusicKey
import com.ldz.music.manager.MusicTypeClient
import com.ldz.music.manager.model.UserMusicStatusDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import collection.JavaConverters._


/**
  * Created by ldalzotto on 23/06/2017.
  */
@Service
class MusicService extends IMusicService {

  @Autowired
  private val musicManagerClient: MusicTypeClient = null

  override def addMusicFromUrlAndMusicPlatform(username: String, url: String, musicPlatform: String): Boolean = {
    val iframeUrl = musicManagerClient.getMusicParametersFromUrlAndMusicplatform(url, musicPlatform)
    Option(musicManagerClient.postMusicFromSiteurl(username, iframeUrl.getBody)) match {
      case Some(result) => true
      case None => false
    }
  }

  override def getMusicStatusOfCurrentClient(username: String): UserMusicStatusDTO = {
    musicManagerClient.getUserFromUsername(username).getBody()
  }

  override def getAllDistinctArtistsFromClient(username: String): Seq[String] = {
    musicManagerClient.getUserFromUsername(username).getBody match {
      case UserMusicStatusDTO(_, musicTypeDTO) => {

        musicTypeDTO.asScala.distinct.map(musicType => {
          val musicparameter = Option(musicType.musicParameters) match {
            case Some(mp) => mp
            case None => throw new RuntimeException("No author found !!")
          }
          val author = musicparameter.asScala.get(ExternalMusicKey.AUTHOR) match {
            case Some(authorMap) => authorMap.asInstanceOf[String]
            case None => throw new RuntimeException("No author found !!")
          }
          author
        })

      }
    }
  }
}
