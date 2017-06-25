package com.ldz.javascript.face

import com.ldz.javascript.face.model.CustomModalConfig
import org.scalajs.dom.Event
import org.scalajs.jquery.JQuery

import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobal

/**
  * Created by Loic on 25/06/2017.
  */
@js.native
@JSGlobal("CustomModal")
class CustomModal(modalElementToCustom: JQuery, config: CustomModalConfig) extends js.Any{

  def errorPopUp(message: String): Unit = js.native
  def showModal(event: Event): Unit = js.native
  def showModalFromAbsoluteCoordinates (coord: Any): Unit = js.native
  def hideModal(): Unit = js.native

}
