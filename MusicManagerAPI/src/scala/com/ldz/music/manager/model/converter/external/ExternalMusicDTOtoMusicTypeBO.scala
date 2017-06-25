package com.ldz.music.manager.model.converter.external

import com.ldz.converter.container.annot.BeanConverter
import com.ldz.converter.container.inter.IConverter
import com.ldz.external.api.model.ExternalMusicDTO
import com.ldz.music.manager.model.bo.MusicTypeBO
import org.springframework.stereotype.Component

import collection.JavaConverters._


/**
  * Created by Loic on 25/06/2017.
  */
@Component
@BeanConverter(initialBeanClass = classOf[ExternalMusicDTO], targetBeanClass = classOf[MusicTypeBO])
class ExternalMusicDTOtoMusicTypeBO extends IConverter[ExternalMusicDTO, MusicTypeBO]{
  override def apply(externalMusicDTO: ExternalMusicDTO): MusicTypeBO = {
    Option(externalMusicDTO) match {
      case Some(`externalMusicDTO`) =>
        MusicTypeBO(null, `externalMusicDTO`.getIframeURL, `externalMusicDTO`.getMusicKey.asScala.toMap)
      case None => null
    }
  }
}
