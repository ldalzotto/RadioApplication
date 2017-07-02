package com.ldz.project.controller

import javax.ws.rs.QueryParam

import com.ldz.converter.container.ConverterContainer
import com.ldz.project.exception.{AlreadyRegistered, LoginWithUnknownIPException, LoginWithUnkownUser}
import com.ldz.project.model.RawMusicStatus
import com.ldz.project.service.music.IMusicService
import org.apache.camel.{CamelExecutionException, ProducerTemplate}
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{ExceptionHandler, PathVariable, RequestMapping, RequestMethod}
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

/**
  * Created by Loic on 28/06/2017.
  */
class MusicControllerCamel {

  private val LOGGER = LoggerFactory.getLogger(classOf[MusicController].getName)

  @Autowired
  private val producerTemplate: ProducerTemplate = null

  @RequestMapping(value = Array("user/{username}/music/musicplatform/{musicplatform}"), method = Array(RequestMethod.POST))
  def addMusicFromRessourceUrl(@PathVariable("username") username: String, @PathVariable("musicplatform") musicPlatform: String, @QueryParam("url") url: String): ResponseEntity[_] = {
      producerTemplate.sendBody("direct:user/music/musicplatform", Array(username, musicPlatform, url)).asInstanceOf[ResponseEntity[_]]
  }

  @RequestMapping(value = Array("user/{username}/music/all"), method = Array(RequestMethod.GET))
  def getAllMusicUrls(@PathVariable("username") username: String): ResponseEntity[RawMusicStatus] = {
      producerTemplate.sendBody("direct:user/music/all", username).asInstanceOf[ResponseEntity[RawMusicStatus]]
  }

  @RequestMapping(value = Array("user/{username}/music/artists/all-distinct"), method = Array(RequestMethod.GET))
  def getAllDistictMusicArtists(@PathVariable("username") username: String): ResponseEntity[java.util.List[String]] = {
    producerTemplate.sendBody("direct:user/music/artists/all-distinct", username).asInstanceOf[ResponseEntity[java.util.List[String]]]
  }


  @ExceptionHandler(Array{classOf[CamelExecutionException]})
  def camelExecutionError(camelExeption: CamelExecutionException) = {
    camelExeption.getCause match {
      case e: Exception =>
        val errorCode = "AN_ERROR_OCCURED"
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorCode)
    }
  }

}
