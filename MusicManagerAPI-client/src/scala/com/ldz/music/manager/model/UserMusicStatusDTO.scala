package com.ldz.music.manager.model

import javax.validation.Valid
import javax.validation.constraints.{NotNull, Size}
import java.util.List

import scala.annotation.meta.field
import scala.beans.BeanProperty

/**
  * Created by Loic on 20/06/2017.
  */
case class UserMusicStatusDTO(@(NotNull@field) @BeanProperty username: String,
                              @(Size@field)(min = 0) @Valid @BeanProperty musicTypeDTO: List[MusicTypeDTO]) {

  def this() {
    this(null, null)
  }

}
