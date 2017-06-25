package com.ldz.javascript.face

import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobal

/**
  * Created by Loic on 25/06/2017.
  */
@js.native
@JSGlobal("CurrentUser")
object CurrentUser extends js.Any {
  def retrieveCurrentUser(): CurrentUserObject = js.native
}
