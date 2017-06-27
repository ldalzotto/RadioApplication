package com.ldz.treeMusic


import com.ldz.enumeration.ExternalMusicKey
import com.ldz.project.model.RawMusicStatus

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import scala.scalajs.js._
import org.scalajs.jquery.{JQuery, jQuery}
import org.scalajs.dom.document

import collection.JavaConverters._


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
  def refreshTree(userMusicStatus: RawMusicStatus):Unit = {
    //clear element
    artistsTreeElement.empty()

    Option(userMusicStatus) match {
      case Some(RawMusicStatus(username, musicTypeDTO)) => {
        for (musicType <- musicTypeDTO.asScala;
             author = musicType.musicParameters.asScala.getOrElse(ExternalMusicKey.AUTHOR, "")
             if !author.equals("")) yield author
      }.distinct.foreach(artist => {
        //write to DOM
        artistsTreeElement.append(getTemplateFromArtist(artist))
      })
      case None =>
    }
  }

}