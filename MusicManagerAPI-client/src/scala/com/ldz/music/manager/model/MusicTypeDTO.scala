package com.ldz.music.manager.model

import javax.validation.constraints.Pattern
import javax.validation.constraints.NotNull

import com.ldz.music.manager.constants.MusicTypes

import scala.annotation.meta.field
import scala.beans.BeanProperty

/**
  * Created by Loic on 20/06/2017.
  */
case class MusicTypeDTO(@(NotNull@field) @BeanProperty musicType: MusicTypes.Value,
                        @(NotNull@field) @(Pattern@field)(regexp = "(^(https:\\/\\/)|(http:\\/\\/)).+") @BeanProperty sourceUrl: String) {

}
