package com.ldz.music.manager.model.converter.model.to.bo

import com.ldz.converter.container.annot.BeanConverter
import com.ldz.converter.container.inter.IConverter
import com.ldz.music.manager.constants.MusicTypes
import com.ldz.music.manager.model.MusicType
import com.ldz.music.manager.model.bo.MusicTypeBO
import org.springframework.stereotype.Component
import collection.JavaConverters._


/**
  * Created by Loic on 20/06/2017.
  */
@Component
@BeanConverter(initialBeanClass = classOf[MusicType], targetBeanClass = classOf[MusicTypeBO])
class MusicTypeModelToBO extends IConverter[MusicType, MusicTypeBO]{
  override def apply(musicType: MusicType): MusicTypeBO = {

    Option(musicType) match {
      case Some(mt) => MusicTypeBO(MusicTypes.withName(mt.getType), mt.getSourceUrl,
        mt.getMusicParameters.asScala.toMap)
      case None => null
    }

  }
}
