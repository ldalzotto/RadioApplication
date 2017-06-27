package com.ldz.project.controller

import java.net.URI
import java.util.concurrent.Executors
import javax.ws.rs.QueryParam

import com.ldz.converter.container.ConverterContainer
import com.ldz.music.manager.model.UserMusicStatusDTO
import com.ldz.project.model.RawMusicStatus
import com.ldz.project.service.music.IMusicService
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{PathVariable, RequestMapping, RequestMethod}
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

/**
  * Created by ldalzotto on 24/06/2017.
  */
@Controller
class MusicController {
  private val LOGGER = LoggerFactory.getLogger(classOf[MusicController].getName)

  @Autowired
  private val iMusicService: IMusicService = null

  @Autowired
  private val converterContainer: ConverterContainer = null

  implicit val executionContext = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(10))

  @RequestMapping(value = Array("user/{username}/music/musicplatform/{musicplatform}"), method = Array(RequestMethod.POST))
  def addMusicFromRessourceUrl(@PathVariable("username") username: String, @PathVariable("musicplatform") musicPlatform: String, @QueryParam("url") url: String): ResponseEntity[_] = {
    Await.result(Future{
      iMusicService.addMusicFromUrlAndMusicPlatform(username, url, musicPlatform)
    }, Duration.Inf)
     match {
      case true =>
        val location = ServletUriComponentsBuilder.fromCurrentRequestUri.build.toUri
        ResponseEntity.created(location).build()
      case false =>
        ResponseEntity.unprocessableEntity().build()
    }
  }

  @RequestMapping(value = Array("user/{username}/music/all"), method = Array(RequestMethod.GET))
  def getAllMusicUrls(@PathVariable("username") username: String): ResponseEntity[RawMusicStatus] = {
    Await.result(Future{
      ResponseEntity.ok(converterContainer.convert(iMusicService.getMusicStatusOfCurrentClient(username), classOf[RawMusicStatus]))
    }, Duration.Inf)
  }

}
