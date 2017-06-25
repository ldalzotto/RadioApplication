package com.ldz.treeMusic


import scala.scalajs.js.annotation.JSExportTopLevel
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

}