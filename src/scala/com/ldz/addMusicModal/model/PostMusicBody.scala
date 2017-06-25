package com.ldz.addMusicModal.model

import scala.scalajs.js

/**
  * Created by Loic on 25/06/2017.
  */
@js.native
trait PostMusicBody extends js.Object {
  val url: String = js.native
}

object PostMusicBody {
  def apply(url: String): PostMusicBody = js.Dynamic.literal(url = url).asInstanceOf[PostMusicBody]
}
