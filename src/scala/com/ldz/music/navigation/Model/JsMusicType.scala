package com.ldz.music.navigation.Model

import com.ldz.enumeration.ExternalMusicKey
import com.ldz.project.model.RawMusicType

import scala.scalajs.js
import scala.scalajs.js.annotation.ScalaJSDefined
import collection.JavaConverters._


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

  def unapply(arg: JsMusicType): RawMusicType = {
    val musicParameters = arg.musicParameters.toOption.get.toMap.map(value =>
        (ExternalMusicKey.withName(value._1), value._2))
    RawMusicType(arg.musicType.toOption.get, arg.sourceUrl.toOption.get, musicParameters.asJava)
  }
}
