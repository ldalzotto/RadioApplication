package com.ldz.music.navigation

import com.ldz.addMusicModal.AddMusicPopup
import com.ldz.javascript.face.CurrentUser
import com.ldz.music.navigation.jquery.RefreshMusicJQuery
import org.scalajs.dom.document
import org.scalajs.jquery.{JQuery, jQuery}

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

/**
  * Created by Loic on 25/06/2017.
  */
@JSExportTopLevel("MusicView")
object MusicNavigation {

  var musicPanelListElement: JQuery = null
  var musicPanelInfoTemplateElement: JQuery = null
  var addMusicButton: JQuery = null

  jQuery(document).ready(() => {
    musicPanelListElement = jQuery("#music-navbar-content").find("#music-panel-list")
    musicPanelInfoTemplateElement = jQuery("#music-panel-info")
    musicPanelInfoTemplateElement.show()
    addMusicButton = jQuery("#add-music-button")
    addMusicButton.click(() => {
      AddMusicPopup.showModal()
    })
  })

  private def refreshMusicListPanel(): Unit = {
    val username = CurrentUser.retrieveCurrentUser().username
    jQuery.ajax(s"user/${username}/music/all", RefreshMusicJQuery.apply(username))
  }

  @JSExport
  def displayMusicPanel(): Unit = {
    musicPanelListElement.show()
    AddMusicPopup.hideModal()
    refreshMusicListPanel()
  }

}