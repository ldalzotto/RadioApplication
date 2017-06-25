package com.ldz.music.manager.model.converter.bo.to.model

import com.ldz.converter.container.annot.BeanConverter
import com.ldz.converter.container.inter.IConverter
import com.ldz.music.manager.model.MusicType
import com.ldz.music.manager.model.bo.MusicTypeBO
import org.springframework.stereotype.Component
import collection.JavaConverters._


/**
  * Created by Loic on 20/06/2017.
  */
@Component
@BeanConverter(initialBeanClass = classOf[MusicTypeBO], targetBeanClass = classOf[MusicType])
class MusicTypeBOtoModel extends IConverter[MusicTypeBO, MusicType]{

  override def apply(musicTypeBo: MusicTypeBO): MusicType = {

    Option(musicTypeBo) match {
      case Some(mtBo) =>
        val musicType = new MusicType
        musicType.setSourceUrl(mtBo.getSourceUrl)

        Option(mtBo.getMusicTypes) match {
          case Some(musicTypeEnum) => musicType.setType(musicTypeEnum.toString)
          case None =>
        }
        musicType.setMusicParameters(musicTypeBo.getMusicParameters.asJava)
        musicType
      case None => null
    }

  }

}
