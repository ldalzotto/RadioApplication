package com.ldz.music.manager.model.converter.bo.to.dto

import com.ldz.converter.container.annot.BeanConverter
import com.ldz.converter.container.inter.IConverter
import com.ldz.music.manager.model.MusicTypeDTO
import com.ldz.music.manager.model.bo.MusicTypeBO
import org.springframework.stereotype.Component

/**
  * Created by Loic on 20/06/2017.
  */
@Component
@BeanConverter(initialBeanClass = classOf[MusicTypeBO], targetBeanClass = classOf[MusicTypeDTO])
class MusicTypeBOtoDTO extends IConverter[MusicTypeBO, MusicTypeDTO]{

  override def apply(musicTypeBO: MusicTypeBO): MusicTypeDTO = {
    musicTypeBO match {
      case `musicTypeBO` if musicTypeBO != null => MusicTypeDTO(musicTypeBO.getMusicTypes, musicTypeBO.getSourceUrl, musicTypeBO.getMusicParameters)
      case _ => null
    }
  }


}