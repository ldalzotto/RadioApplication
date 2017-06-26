package com.ldz.music.navigation.Model

import scala.scalajs.js
import scala.scalajs.js.annotation.ScalaJSDefined

/**
  * Created by Loic on 25/06/2017.
  */
@ScalaJSDefined
trait JsMusicType extends js.Object{
  val musicType: js.UndefOr[String]
  val sourceUrl: js.UndefOr[String]
  val musicParameters: js.UndefOr[js.Dictionary[String]]
}

object JsMusicType {
  def apply(musicType: String, sourceUrl: String, musicParameters: Map[String, String]): JsMusicType
      = js.Dynamic.literal(musicType = musicType, sourceUrl = sourceUrl, musicParameters = musicParameters).asInstanceOf[JsMusicType]

  def unapply(arg: JsMusicType): MusicType = {
    val musicParameters = arg.musicParameters.toOption.get.toMap.map(value =>
        (MusicParametersKey.withName(value._1), value._2))
    MusicType(arg.musicType.toOption.get, arg.sourceUrl.toOption.get, musicParameters)
  }
}


case class MusicType(  musicType: String,
                      sourceUrl: String,
                      musicParameters: Map[MusicParametersKey.Value, String]) {

}