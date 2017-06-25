package com.ldz.external.inter

import java.util
import java.util.{ArrayList, List}
import java.util.regex.{Matcher, Pattern}

import com.ldz.enumeration.ExternalMusicKey
import org.springframework.stereotype.Service

import scala.collection.mutable.ListBuffer
import scala.util.matching.Regex
import scala.util.matching.Regex.Match

/**
  * Created by Loic on 14/06/2017.
  */
@Service
class SoundCloudService extends ISoundCloundService {

  private val SOUNDCLOUD_MUSIC_FINDER_TOKEN = "soundcloud://sounds:".r
  private val SOUNDCLOUD_USERNAME_CLASS = "\"full_name\":\"".r
  private val SOUNDLOUC_TITLE_CLASS = "\"title\":\"".r

  private val SOUNDCLOUD_IFRAME_SRC_URL_FORMAT = "https://w.soundcloud.com/player/?url=https%3A//api.soundcloud.com/tracks/{IDENTIFIANT}&amp;auto_play=false&amp;hide_related=false&amp;show_comments=true&amp;show_user=true&amp;show_reposts=false&amp;visual=true"


  override def getMusicIdFromRessource(ressource: String): String = {
    getExtractedRessource(ressource, SOUNDCLOUD_MUSIC_FINDER_TOKEN, '"')
  }

  override def getMusicparametersFromRessource(ressource: String): Seq[Tuple2[ExternalMusicKey.Value, String]] = {
    val title = getExtractedRessource(ressource, SOUNDLOUC_TITLE_CLASS, '"')
    val artist = getExtractedRessource(ressource, SOUNDCLOUD_USERNAME_CLASS, '"')

      Seq((ExternalMusicKey.TITLE, title),
        (ExternalMusicKey.AUTHOR, artist))
  }

  private def getExtractedRessource(ressource: String, pattern: Regex, delimiter: Char) = {
    val listIndexes = pattern.findAllMatchIn(ressource)
      .map(regMatch => regMatch.end)
      .toList

    //get the first String data wich corresponds to musicId
    val musicIds =
      (for (index <- listIndexes;
            tuple = (index, checkIfDelimiterAtIndex(index, delimiter, ressource))
            if tuple._2.isDefined)
        yield ressource.substring(tuple._1, tuple._2.get)) (collection.breakOut)

    musicIds match {
      case s if s.isEmpty => ""
      case s => s.head
    }
  }


  override def getIframeRessourceFromMusicId(musicId: String): String = {
    SOUNDCLOUD_IFRAME_SRC_URL_FORMAT.replace("{IDENTIFIANT}", musicId)
  }

  private def checkIfDelimiterAtIndex(initialIndex: Int, delimiter: Char, ressource: String):Option[Int] = {
    ressource.charAt(initialIndex) match {
      case `delimiter` => Some(initialIndex)
      case _ => checkIfDelimiterAtIndex(initialIndex + 1, delimiter, ressource)
    }
  }


}
