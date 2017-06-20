package com.ldz.music.manager.model.converter.dto.to.bo

import com.ldz.converter.container.annot.BeanConverter
import com.ldz.converter.container.inter.IConverter
import com.ldz.music.manager.model.MusicTypeDTO
import com.ldz.music.manager.model.bo.MusicTypeBO
import org.springframework.stereotype.Component

/**
  * Created by Loic on 20/06/2017.
  */
@Component
@BeanConverter(initialBeanClass = classOf[MusicTypeDTO], targetBeanClass = classOf[MusicTypeBO])
class MusicTypeDTOtoBO extends IConverter[MusicTypeDTO, MusicTypeBO]{
  override def apply(musicTypeDto: MusicTypeDTO): MusicTypeBO = {
    musicTypeDto match {
      case `musicTypeDto` if musicTypeDto != null => MusicTypeBO(musicTypeDto.musicType, musicTypeDto.sourceUrl)
      case _ => null
    }
  }
}
