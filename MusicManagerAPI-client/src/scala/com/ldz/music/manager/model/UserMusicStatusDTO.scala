package com.ldz.music.manager.model

import javax.validation.Valid
import javax.validation.constraints.{NotNull, Size}

import scala.beans.BeanProperty

/**
  * Created by Loic on 20/06/2017.
  */
case class UserMusicStatusDTO(@NotNull @BeanProperty username: String,
                              @Size(min = 0) @Valid @BeanProperty musicTypeDTO: Seq[MusicTypeDTO]) {

}
