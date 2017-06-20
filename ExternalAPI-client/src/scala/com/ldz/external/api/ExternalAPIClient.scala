package com.ldz.external.api

import com.ldz.external.api.model.ExternalMusicDTO
import org.springframework.cloud.netflix.feign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, RequestParam}

/**
  * Created by Loic on 14/06/2017.
  */
@FeignClient("external-api")
trait ExternalAPIClient {

  @RequestMapping(method = Array(RequestMethod.GET), path = Array("/soundcloud/sourceurl"))
  def getSoundcloudSourceurlFromRessource(@RequestParam("ressource") ressource: String): ResponseEntity[ExternalMusicDTO]

}
