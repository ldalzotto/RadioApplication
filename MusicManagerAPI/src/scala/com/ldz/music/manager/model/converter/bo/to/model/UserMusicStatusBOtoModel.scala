package com.ldz.music.manager.model.converter.bo.to.model

import com.ldz.converter.container.ConverterContainer
import com.ldz.converter.container.annot.BeanConverter
import com.ldz.converter.container.inter.IConverter
import com.ldz.music.manager.model.{MusicType, MusicTypeDTO, UserMusicStatus}
import com.ldz.music.manager.model.bo.{MusicTypeBO, UserMusicStatusBO}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import scala.collection.JavaConverters._

/**
  * Created by Loic on 20/06/2017.
  */
@Component
@BeanConverter(initialBeanClass = classOf[UserMusicStatusBO], targetBeanClass = classOf[UserMusicStatus])
class UserMusicStatusBOtoModel extends IConverter[UserMusicStatusBO, UserMusicStatus]{

  @Autowired
  private val converterContainer: ConverterContainer = null

  override def apply(userMusicStatusBO: UserMusicStatusBO): UserMusicStatus = {
    userMusicStatusBO match {
      case `userMusicStatusBO` if userMusicStatusBO != null => {
        var userMusicStatus = new UserMusicStatus

        //musictypes
        val musics = userMusicStatusBO.getMusicTypeBOs.map(a => converterContainer.convert(a, classOf[MusicType]))
        userMusicStatus.setMusicTypes(musics.asJava)
        userMusicStatus.setUsername(userMusicStatusBO.getUsername)
        userMusicStatus
      }
      case _ => null
    }
  }
}
