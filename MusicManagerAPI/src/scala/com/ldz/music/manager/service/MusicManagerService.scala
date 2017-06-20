package com.ldz.music.manager.service

import com.ldz.converter.container.ConverterContainer
import com.ldz.external.api.ExternalAPIClient
import com.ldz.music.manager.constants.MusicTypes
import com.ldz.music.manager.model.{MusicType, UserMusicStatus}
import com.ldz.music.manager.model.bo.{MusicTypeBO, UserMusicStatusBO}
import com.ldz.music.manager.repository.{MusicTypeRepository, UserMusicStatusRepository}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import scala.collection.JavaConverters._

/**
  * Created by Loic on 20/06/2017.
  */
@Transactional
@Service
class MusicManagerService extends IMusicManagerService{

  @Autowired
  private val externalAPIClient: ExternalAPIClient = null

  @Autowired
  private val musicTypeRepository: MusicTypeRepository = null

  @Autowired
  private val userMusicStatusRepository: UserMusicStatusRepository = null

  @Autowired
  private val converterContainer: ConverterContainer = null

  override def addUserMusicStatus(userMusicStatusBO: UserMusicStatusBO): Unit = {

    val userMusicStatus = converterContainer.convert(userMusicStatusBO, classOf[UserMusicStatus])



    userMusicStatus.getMusicTypes.asScala.foreach(musicType => {
                                                      musicType.setUserMusicStatus(userMusicStatus);
                                                      musicTypeRepository.save(musicType)
                                                      })
    userMusicStatusRepository.save(userMusicStatus)

  }

  override def deleteUserMusicStatusFromUsername(username: String): Unit = {
    userMusicStatusRepository.deleteByUsername(username)
  }

  override def getMusicStatusFromUsername(username: String): UserMusicStatusBO = {
    converterContainer.convert( userMusicStatusRepository.findByUsername(username), classOf[UserMusicStatusBO])
  }

  override def getSourceurlFromUrlAndMusicType(url: String, musicType: MusicTypes.Value): String = {

    musicType match {
      case MusicTypes.SOUNDCLOUD => externalAPIClient.getSoundcloudSourceurlFromRessource(url).getBody.iframeURL
      case _ => null
    }

  }

  override def postMusicFromUsernameAndSourceurl(username: String, sourceUrl: String): Boolean = {

    val userMusicStatus = userMusicStatusRepository.findByUsername(username)

    userMusicStatus match {
      case `userMusicStatus` if userMusicStatus != null => {
        val musicType = converterContainer.convert(MusicTypeBO(MusicTypes.SOUNDCLOUD, sourceUrl), classOf[MusicType])
        musicTypeRepository.save(musicType)
        true
      }
      case _ => false
    }

  }
}
