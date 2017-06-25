package com.ldz.project.model.converter

import com.ldz.converter.container.ConverterContainer
import com.ldz.converter.container.annot.BeanConverter
import com.ldz.converter.container.inter.IConverter
import com.ldz.music.manager.model.UserMusicStatusDTO
import com.ldz.project.model.{RawMusicStatus, RawMusicType}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import collection.JavaConverters._


/**
  * Created by Loic on 25/06/2017.
  */
@Component
@BeanConverter(initialBeanClass = classOf[UserMusicStatusDTO], targetBeanClass = classOf[RawMusicStatus])
class MusicStatusDTOtoRaw extends IConverter[UserMusicStatusDTO, RawMusicStatus]{

  @Autowired
  private val converterContainer: ConverterContainer = null

  override def apply(userMusicStatusDTO: UserMusicStatusDTO): RawMusicStatus = {

    Option(userMusicStatusDTO) match {
      case Some(umDTO) => {
        val rawMusicTypes = umDTO.getMusicTypeDTO.asScala.map(mtDTO => converterContainer.convert(mtDTO, classOf[RawMusicType])).toSeq
        RawMusicStatus(umDTO.getUsername, rawMusicTypes.asJava)
      }
      case None => null
    }

  }
}
