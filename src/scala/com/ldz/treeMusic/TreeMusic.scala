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

  var musicSearchinput: JQuery = null
  var musicSearchButton: JQuery = null

  var treeElement: JQuery = null
  var rootTreeElement: JQuery = null
  var artistsTreeElement: JQuery = null

  jQuery(document).ready(() => {
    treeElement = jQuery(".tree")
    rootTreeElement = jQuery("#root-tree-element")
    artistsTreeElement = rootTreeElement.find("#artists-tree-elements")

    musicSearchinput = jQuery("#music-search-input")
    musicSearchButton = jQuery("#music-search-button")

    musicSearchButton.click(() => {
      val researchRequest = musicSearchinput.get(0).innerHTML.replaceAll("\\s", "")
      getAllDisplayedArtistsName().filter(artist => !artist.matches(researchRequest))
          .map(artistName => jQuery(s"#li-${artistName}"))
            .foreach(liNode => liNode.css("display", "none"))
    })

  })

  private def getTemplateFromArtist(artist: String): Array[Any] = {
    jQuery.parseHTML(   s"""<li id="li-${artist}">
                           | <span> ${artist}</span>
                           |</li>"""
      .stripMargin)
  }

  private def getAllDisplayedArtistsName(): Seq[String] = {
    for(spanElem <- rootTreeElement.find("li").find("span").get())
        yield spanElem.innerHTML.replaceAll("\\s", "")
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