package com.ldz.music.manager

import com.ldz.music.manager.model.{MusicTypeDTO, UserMusicStatusDTO}
import com.ldz.enumeration.ExternalMusicKey
import org.springframework.cloud.netflix.feign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation._

/**
  * Created by Loic on 20/06/2017.
  */
@FeignClient("music-manager-api")
trait MusicTypeClient {
  @RequestMapping(method = Array(RequestMethod.POST), path = Array("/music/user"))
  def postUser(@RequestBody userMusicStatusDTO: UserMusicStatusDTO): ResponseEntity[_]

  @RequestMapping(method = Array(RequestMethod.GET), path = Array("/music/user/username/{username}"))
  def getUserFromUsername(@PathVariable("username") username: String): ResponseEntity[UserMusicStatusDTO]

  @RequestMapping(method = Array(RequestMethod.DELETE), path = Array("/music/user/username/{username}"))
  def deleteUser(@PathVariable("username") username: String): ResponseEntity[_]

  @RequestMapping(method = Array(RequestMethod.GET), path = Array("/music/musicplatform/{musicplatform}/ressource-url"))
  def getMusicParametersFromUrlAndMusicplatform(@RequestParam("url") url: String, @PathVariable("musicplatform") musicplatform: String): ResponseEntity[MusicTypeDTO]

  @RequestMapping(method = Array(RequestMethod.PUT), path = Array("/music/user/username/{username}"))
  def postMusicFromSiteurl(@PathVariable("username") username: String, @RequestBody musicType: MusicTypeDTO): ResponseEntity[MusicTypeDTO]
}
