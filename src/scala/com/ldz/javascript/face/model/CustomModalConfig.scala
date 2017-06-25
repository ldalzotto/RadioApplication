package com.ldz.javascript.face.model

import scala.annotation.meta.field
import scala.scalajs.js.annotation.{JSExport, JSExportAll}

/**
  * Created by Loic on 25/06/2017.
  */
@JSExportAll
case class CustomModalConfig(
                              @(JSExport@field) closable: Boolean,
                              @(JSExport@field) resizable: Boolean,
                              @(JSExport@field) draggable: Boolean)
