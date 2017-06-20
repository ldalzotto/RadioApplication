package com.ldz.music.manager.model.converter.model.to.bo

import com.ldz.converter.container.ConverterContainer
import com.ldz.converter.container.annot.BeanConverter
import com.ldz.converter.container.inter.IConverter
import com.ldz.music.manager.model.{MusicType, UserMusicStatus}
import com.ldz.music.manager.model.bo.{MusicTypeBO, UserMusicStatusBO}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import scala.collection.JavaConverters._

/**
  * Created by Loic on 20/06/2017.
  */
@Component
@BeanConverter(initialBeanClass = classOf[UserMusicStatus], targetBeanClass = classOf[UserMusicStatusBO])
class UserMusicStatusToBO extends IConverter[UserMusicStatus, UserMusicStatusBO]{

  @Autowired
  private val converterContainer: ConverterContainer = null

  override def apply(userMusicStatus: UserMusicStatus): UserMusicStatusBO = {
    userMusicStatus match {
      case `userMusicStatus` if userMusicStatus != null => {
        val musics = userMusicStatus.getMusicTypes.asScala.map(a => converterContainer.convert(a, classOf[MusicTypeBO]))
        UserMusicStatusBO(userMusicStatus.getUsername, musics)
      }
      case _ => null
    }
  }

}
