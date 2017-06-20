package com.ldz.music.manager.model.converter.bo.to.model

import com.ldz.converter.container.annot.BeanConverter
import com.ldz.converter.container.inter.IConverter
import com.ldz.music.manager.model.MusicType
import com.ldz.music.manager.model.bo.MusicTypeBO
import org.springframework.stereotype.Component

/**
  * Created by Loic on 20/06/2017.
  */
@Component
@BeanConverter(initialBeanClass = classOf[MusicTypeBO], targetBeanClass = classOf[MusicType])
class MusicTypeBOtoModel extends IConverter[MusicTypeBO, MusicType]{

  override def apply(musicTypeBo: MusicTypeBO): MusicType = {
    musicTypeBo match {
      case `musicTypeBo` if musicTypeBo != null => {
        var musicType = new MusicType
        musicType.setSourceUrl(musicTypeBo.getSourceUrl)
        musicType.setType(musicTypeBo.getMusicTypes.toString)
        musicType
      }
      case _ => null
    }
  }

}
