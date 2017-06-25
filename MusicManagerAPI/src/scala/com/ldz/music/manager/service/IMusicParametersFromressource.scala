package com.ldz.music.manager.service

import com.ldz.external.api.ExternalAPIClient
import com.ldz.music.manager.model.bo.MusicTypeBO
import com.ldz.music.manager.constants.MusicTypes

/**
  * Created by Loic on 25/06/2017.
  */
trait IMusicParametersFromressource {

  def getMusicSourceFromRessource(musicTypes: MusicTypes.Value, url: String): MusicTypeBO

}
