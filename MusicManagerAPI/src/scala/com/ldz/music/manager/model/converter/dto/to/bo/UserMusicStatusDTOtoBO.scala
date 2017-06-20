package com.ldz.music.manager.model.converter.dto.to.bo

import com.ldz.converter.container.ConverterContainer
import com.ldz.converter.container.annot.BeanConverter
import com.ldz.converter.container.inter.IConverter
import com.ldz.music.manager.model.UserMusicStatusDTO
import com.ldz.music.manager.model.bo.{MusicTypeBO, UserMusicStatusBO}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
  * Created by Loic on 20/06/2017.
  */

@Component
@BeanConverter(initialBeanClass = classOf[UserMusicStatusDTO], targetBeanClass = classOf[UserMusicStatusBO])
class UserMusicStatusDTOtoBO extends IConverter[UserMusicStatusDTO, UserMusicStatusBO]{

  @Autowired
  private val converterContainer: ConverterContainer = null

  override def apply(userMusicStatusDTO: UserMusicStatusDTO): UserMusicStatusBO = {
    userMusicStatusDTO match {
      case `userMusicStatusDTO` if userMusicStatusDTO != null => {
        val musics = userMusicStatusDTO.musicTypeDTO.map(a => converterContainer.convert(a, classOf[MusicTypeBO]))
        UserMusicStatusBO(userMusicStatusDTO.username, musics)
      }
      case _ => null
    }
  }
}
