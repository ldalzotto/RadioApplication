package com.ldz.external.inter

import java.util
import java.util.{ArrayList, List}
import java.util.regex.{Matcher, Pattern}

import org.springframework.stereotype.Service

import scala.collection.mutable.ListBuffer
import scala.util.matching.Regex.Match

/**
  * Created by Loic on 14/06/2017.
  */
@Service
class SoundCloudService extends ISoundCloundService {

  private val SOUNDCLOUD_MUSIC_FINDER_TOKEN = "soundcloud://sounds:"
  private val SOUNDCLOUD_IFRAME_SRC_URL_FORMAT = "https://w.soundcloud.com/player/?url=https%3A//api.soundcloud.com/tracks/{IDENTIFIANT}&amp;auto_play=false&amp;hide_related=false&amp;show_comments=true&amp;show_user=true&amp;show_reposts=false&amp;visual=true"


  override def getMusicIdFromRessource(ressource: String): String = {

    val pattern = SOUNDCLOUD_MUSIC_FINDER_TOKEN.r

    val listIndexes = pattern.findAllMatchIn(ressource)
                    .map(regMatch => regMatch.end)
                    .toList


    def checkIfDelimiterAtIndex(initialIndex: Int):Option[Int] = {
      ressource.charAt(initialIndex) match {
        case '"' => Some(initialIndex)
        case _ => checkIfDelimiterAtIndex(initialIndex + 1)
      }
    }

    val musicId = listIndexes.map(index => (index, checkIfDelimiterAtIndex(index)))
                  .filter(_._2.isDefined)
                    .map{case (e1, e2) => ressource.substring(e1, e2.get)}
                        .head

    musicId
  }

  override def getIframeRessourceFromMusicId(musicId: String): String = {
    SOUNDCLOUD_IFRAME_SRC_URL_FORMAT.replace("{IDENTIFIANT}", musicId)
  }


}
