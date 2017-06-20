package com.ldz.music.manager.model

import javax.validation.constraints.Pattern
import javax.validation.constraints.NotNull

import com.ldz.music.manager.constants.MusicTypes

import scala.beans.BeanProperty

/**
  * Created by Loic on 20/06/2017.
  */
case class MusicTypeDTO(@NotNull @BeanProperty musicType: MusicTypes.Value,
                        @NotNull @Pattern(regexp = "(^(https:\\/\\/)|(http:\\/\\/)).+") @BeanProperty sourceUrl: String) {

}
