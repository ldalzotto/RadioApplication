package com.ldz.project.model.converter

import com.ldz.converter.container.annot.BeanConverter
import com.ldz.converter.container.inter.IConverter
import com.ldz.music.manager.model.MusicTypeDTO
import com.ldz.project.model.RawMusicType
import org.springframework.stereotype.Component

/**
  * Created by Loic on 25/06/2017.
  */
@Component
@BeanConverter(initialBeanClass = classOf[MusicTypeDTO], targetBeanClass = classOf[RawMusicType])
class MusicTypeDTOtoRaw extends IConverter[MusicTypeDTO, RawMusicType]{
  override def apply(musicTypeDTO: MusicTypeDTO): RawMusicType = {
    Option(musicTypeDTO) match {
      case Some(mtDTO) => RawMusicType(mtDTO.musicType.toString, mtDTO.sourceUrl, mtDTO.musicParameters)
      case None => null
    }

  }
}
