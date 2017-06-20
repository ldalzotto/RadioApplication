package com.ldz.music.manager.controller


import java.net.URI
import javax.validation.Valid

import com.ldz.converter.container.ConverterContainer
import com.ldz.music.manager.MusicTypeClient
import com.ldz.music.manager.constants.MusicTypes
import com.ldz.music.manager.model.UserMusicStatusDTO
import com.ldz.music.manager.model.bo.UserMusicStatusBO
import com.ldz.music.manager.service.IMusicManagerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{PathVariable, RequestBody, RequestParam}
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

/**
  * Created by Loic on 20/06/2017.
  */
@Controller
class MusicManagerController extends MusicTypeClient{

  @Autowired
  private val iMusicManagerService: IMusicManagerService = null

  @Autowired
  private val converterContainer: ConverterContainer = null

  override def postUser(  @RequestBody  @Valid userMusicStatusDTO: UserMusicStatusDTO): ResponseEntity[_] = {

    //TODO delete that mapping
    val tempuserMusicStatus = UserMusicStatusDTO(userMusicStatusDTO.username, Seq())

    val userMusicStatusBO = converterContainer.convert(tempuserMusicStatus, classOf[UserMusicStatusBO])

    try {
      iMusicManagerService.addUserMusicStatus(userMusicStatusBO)
    } catch {
      case e: Exception if e.getCause != null => ResponseEntity.status(HttpStatus.LOCKED).build()
      case e: Throwable => throw e
    }


    val location = ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUri()
    ResponseEntity.created(location).build()
  }

  override def getUserFromUsername(@PathVariable(name = "username") username: String): ResponseEntity[UserMusicStatusDTO] = {

    val userMusicStatusBO = iMusicManagerService.getMusicStatusFromUsername(username)
    val userMusicStatusDTO = converterContainer.convert(userMusicStatusBO, classOf[UserMusicStatusDTO])
    ResponseEntity.ok(userMusicStatusDTO);
  }

  override def deleteUser(@PathVariable(name = "username") username: String): ResponseEntity[_] = {
    iMusicManagerService.deleteUserMusicStatusFromUsername(username)
    ResponseEntity.ok().build()
  }

  override def getRessourceUrlFromUrlAndMusicplatform(@RequestParam("url") url: String,
                                                      @PathVariable("musicplatform") musicplatform: String): ResponseEntity[String] = {
    val sourceUrl = iMusicManagerService.getSourceurlFromUrlAndMusicType(url, MusicTypes.withName(musicplatform))
    ResponseEntity.ok(sourceUrl)
  }

  override def postMusicFromSiteurl(@PathVariable("username") username: String, @RequestParam("sourceurl") sourceUrl: String): ResponseEntity[Boolean] = {
    val isCreated = iMusicManagerService.postMusicFromUsernameAndSourceurl(username, sourceUrl)

    isCreated match {
      case false => ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(false)
      case true => ResponseEntity.ok(isCreated)
    }

  }
}
