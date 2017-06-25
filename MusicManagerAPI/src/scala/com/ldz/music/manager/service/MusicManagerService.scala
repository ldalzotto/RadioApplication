package com.ldz.music.manager.service

import com.ldz.converter.container.ConverterContainer
import com.ldz.enumeration.ExternalMusicKey
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
  private val musicParametersFromRessourceService: IMusicParametersFromressource = null

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

  override def getMusicparametersFromUrlAndMusicType(url: String, musicType: MusicTypes.Value): MusicTypeBO = {
    musicParametersFromRessourceService.getMusicSourceFromRessource(musicType, url)
  }

  override def postMusicFromUsernameAndSourceurl(username: String, musicDetail: MusicTypeBO): MusicTypeBO = {

    val musicType =
        Option(userMusicStatusRepository.findByUsername(username)) match {
          case Some(userMusicStatus) => {
            val musicType = converterContainer.convert(musicDetail, classOf[MusicType])
            musicType.setType(musicType.getType)
            musicType.setUserMusicStatus(userMusicStatus)
            musicType.setMusicParameters(musicDetail.getMusicParameters.asJava)
            Option(musicTypeRepository.save(musicType))
          }
          case None => None
        }

    musicType match {
      case Some(mt) => converterContainer.convert(mt, classOf[MusicTypeBO])
      case None => throw new RuntimeException(s"An error occured wile saving ${musicDetail}")
    }

  }
}
