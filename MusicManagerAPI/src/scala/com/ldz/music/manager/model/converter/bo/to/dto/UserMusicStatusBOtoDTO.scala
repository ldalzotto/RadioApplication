package com.ldz.music.manager.model.converter.bo.to.dto

import com.ldz.converter.container.ConverterContainer
import com.ldz.converter.container.annot.BeanConverter
import com.ldz.converter.container.inter.IConverter
import com.ldz.music.manager.model.{MusicTypeDTO, UserMusicStatusDTO}
import com.ldz.music.manager.model.bo.{MusicTypeBO, UserMusicStatusBO}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
  * Created by Loic on 20/06/2017.
  */
@Component
@BeanConverter(initialBeanClass = classOf[UserMusicStatusBO], targetBeanClass = classOf[UserMusicStatusDTO])
class UserMusicStatusBOtoDTO extends IConverter[UserMusicStatusBO, UserMusicStatusDTO]{

  @Autowired
  private val converterContainer: ConverterContainer = null

  override def apply(userMusicStatusBO: UserMusicStatusBO): UserMusicStatusDTO = {
    userMusicStatusBO match {
      case `userMusicStatusBO` if userMusicStatusBO != null => {
        val musicDto = userMusicStatusBO.getMusicTypeBOs.map(a => converterContainer.convert(a, classOf[MusicTypeDTO]))
        UserMusicStatusDTO(userMusicStatusBO.getUsername, musicDto)
      }
      case _ => null
    }
  }

}
