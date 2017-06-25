package com.ldz.treeMusic


import com.ldz.music.navigation.Model.UserMusicStatus

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import scala.scalajs.js._
import org.scalajs.jquery.{JQuery, jQuery}
import org.scalajs.dom.document

/**
  * Created by Loic on 25/06/2017.
  */
@JSExportTopLevel("TreeMusic")
object TreeMusic {

  var treeElement: JQuery = null
  var rootTreeElement: JQuery = null
  var artistsTreeElement: JQuery = null

  jQuery(document).ready(() => {
    treeElement = jQuery(".tree")
    rootTreeElement = jQuery("#root-tree-element")
    artistsTreeElement = rootTreeElement.find("#artists-tree-elements")
  })

  private def getTemplateFromArtist(artist: String): Array[Any] = {
    jQuery.parseHTML(   s"""<li>
                           | <span> ${artist}</span>
                           |</li>"""
      .stripMargin)
  }

  @JSExport
  def refreshTree(userMusicStatus: UserMusicStatus):Unit = {
    artistsTreeElement.empty()

    //get the list of artists
    val oartists =
        Option(userMusicStatus) match {
          case Some(UserMusicStatus(username, musicTypeDTO)) =>
            for(musicType <- musicTypeDTO;
                musicParameters = musicType.musicParameters
                if musicParameters != null)
              yield musicParameters.get("AUTHOR")
          case None => List()
        }

    val artists =
        oartists.map(oArtist => {
          oArtist.getOrElse("")
        }).filter(s => s.equals(""))

    //for each artist we add a branch
    artists.foreach(artist => {
      artistsTreeElement.append(getTemplateFromArtist(artist))
    })

  }

}