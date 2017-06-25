package com.ldz.music.navigation.Model

import scala.scalajs.js
import scala.scalajs.js.annotation.ScalaJSDefined

/**
  * Created by Loic on 25/06/2017.
  */
@ScalaJSDefined
trait JsUserMusicStatus extends js.Object{
  val username: js.UndefOr[String]
  val musicTypeDTO: js.UndefOr[js.Array[JsMusicType]]

}

object JsUserMusicStatus {
  def apply(username: String, musicTypeDTO: List[JsMusicType]): JsUserMusicStatus =
    js.Dynamic.literal(username = username, musicTypeDTO = musicTypeDTO).asInstanceOf[JsUserMusicStatus]

  def unapply(arg: JsUserMusicStatus): UserMusicStatus = {
    UserMusicStatus(arg.username.toOption.get, arg.musicTypeDTO.toOption.get.toList.map(JsMusicType.unapply(_)))
  }
}

case class UserMusicStatus(username: String, musicTypeDTO: List[MusicType])