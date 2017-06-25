package com.ldz.music.navigation.jquery

import com.ldz.music.navigation.Model.JsUserMusicStatus
import com.ldz.music.navigation.MusicNavigation
import com.ldz.treeMusic.TreeMusic
import org.scalajs.jquery.JQueryAjaxSettings

import scala.scalajs.js

/**
  * Created by Loic on 25/06/2017.
  */
object RefreshMusicJQuery {

  def apply(username: String): JQueryAjaxSettings = {
    js.Dynamic.literal(
      method = "GET",
      url = s"user/${username}/music/all",
      success = {(jsUserMusicStatus: JsUserMusicStatus) => {
        val userMusicStatus = JsUserMusicStatus.unapply(jsUserMusicStatus)

        MusicNavigation.musicPanelInfoTemplateElement.show()

        Option(userMusicStatus.musicTypeDTO.size) match {
          case Some(i) if i != 0 =>
            MusicNavigation.musicPanelListElement.empty()
            userMusicStatus.musicTypeDTO.foreach(musicType => {
              Option(MusicNavigation.musicPanelInfoTemplateElement.clone().removeClass("'out-of-bound'"))
                  match {
                      case Some(templateElement) =>
                        MusicNavigation.musicPanelListElement.append(templateElement.find("#music-iframe").attr("src", musicType.sourceUrl).show())
                      case None =>
              }
            })
            MusicNavigation.musicPanelListElement.show()
          case Some(i) if i == 0 =>
          case None =>
        }

        //refrach tree
        TreeMusic.refreshTree(userMusicStatus)

      }}
    ).asInstanceOf[JQueryAjaxSettings]
  }

}
