package com.ldz.addMusicModal

import com.ldz.addMusicModal.AddMusicPopup.genericPopUpModal
import com.ldz.addMusicModal.jquery.AddMusicJQueryAjax
import com.ldz.addMusicModal.model.PostMusicBody
import com.ldz.javascript.face.model.CustomModalConfig
import com.ldz.javascript.face.{CurrentUser, CustomModal, EventRoller}
import org.scalajs.dom.document
import org.scalajs.jquery.{JQuery, jQuery}

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel, JSGlobal}

/**
  * Created by Loic on 24/06/2017.
  */
@JSExportTopLevel(name = "AddMusicModal")
object AddMusicPopup {

  var addModalElement: JQuery = null
  var genericPopUpModal: CustomModal = null
  var submitButton: JQuery = null

  //on document ready
  jQuery(document).ready{() =>
      addModalElement = jQuery("#music-navbar-content").find("#add-music-modal")
      genericPopUpModal = new CustomModal(addModalElement, CustomModalConfig(true, false, false))
      submitButton = addModalElement.find("#add-music-submit")
    submitButton.click({() => {
      val url = jQuery("#music-navbar-content").find("#siteURL").`val`().toString
      val platform = jQuery("#music-navbar-content").find("#musicplatform").`val`().toString
      addMusicPlatform(platform, url)
    }})

  }

  private def addMusicPlatform(platform: String, sourceurl: String):Unit = {
    val currentUser = CurrentUser.retrieveCurrentUser()

    jQuery.ajax(s"user/${currentUser.username}/music/musicplatform/${platform}",
      AddMusicJQueryAjax(sourceurl, currentUser, platform, genericPopUpModal)
    )

  }

  @JSExport
  def showModal():Unit = {
    genericPopUpModal.showModal(null)
  }

  @JSExport
  def hideModal():Unit = {
    genericPopUpModal.hideModal()
  }

}