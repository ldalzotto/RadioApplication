package com.ldz.project.model

import scala.beans.BeanProperty

/**
  * Created by Loic on 25/06/2017.
  */
case class RawMusicStatus(@BeanProperty username: String, @BeanProperty musicTypeDTO: java.util.List[RawMusicType]) {


    def this() {
      this(null, null)
    }


  }
