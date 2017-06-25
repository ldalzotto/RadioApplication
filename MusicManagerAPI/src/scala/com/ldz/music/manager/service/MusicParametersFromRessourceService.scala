package com.ldz.music.manager.service
import com.ldz.converter.container.ConverterContainer
import com.ldz.external.api.ExternalAPIClient
import com.ldz.music.manager.constants.MusicTypes
import com.ldz.music.manager.model.bo.MusicTypeBO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
  * Created by Loic on 25/06/2017.
  */
@Service
class MusicParametersFromRessourceService extends IMusicParametersFromressource {

  @Autowired
  private val externalAPIClient: ExternalAPIClient = null

  @Autowired
  private val converterContainer: ConverterContainer = null

  override def getMusicSourceFromRessource(musicType: MusicTypes.Value , url: String): MusicTypeBO = {

    val externalMusicDto =
          musicType match {
            case MusicTypes.SOUNDCLOUD =>
              externalAPIClient.getSoundcloudSourceurlFromRessource(url).getBody
            case anytype => throw new RuntimeException(s"Unknown music types : ${anytype}")
          }

    var musicTypeBO = converterContainer.convert(externalMusicDto, classOf[MusicTypeBO])
    MusicTypeBO(musicType, musicTypeBO.getSourceUrl, musicTypeBO.getMusicParameters)
  }
}
