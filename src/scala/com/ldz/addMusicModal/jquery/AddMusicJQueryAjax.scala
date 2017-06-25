package com.ldz.addMusicModal.jquery

import com.ldz.addMusicModal.model.PostMusicBody
import com.ldz.addMusicModal.AddMusicPopup
import com.ldz.javascript.face.{CurrentUserObject, CustomModal, EventRoller}
import org.scalajs.jquery.{JQueryAjaxSettings, JQueryXHR}

import scala.scalajs.js

/**
  * Created by Loic on 25/06/2017.
  */
object AddMusicJQueryAjax {

  def apply(sourceurl: String, currentUser: CurrentUserObject, platform: String, genericPopUpModal: CustomModal): JQueryAjaxSettings = {
    js.Dynamic.literal(
      method = "POST",
      data = PostMusicBody(sourceurl),
      url = s"user/${currentUser.username}/music/musicplatform/${platform}",
      error = {(jqXHR: JQueryXHR, textStatus: String, errorThrow: String) => genericPopUpModal.errorPopUp(textStatus)},
      success = {(iframeUrl: String) => {
        EventRoller.pushEvent("Music successfully imported !")
        AddMusicPopup.hideModal()
      }}
    ).asInstanceOf[JQueryAjaxSettings]
  }

}
