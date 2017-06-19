package com.ldz.external.controllers

import com.ldz.external.api.ExternalAPIClient
import com.ldz.external.api.enumeration.ExternalMusicKey
import com.ldz.external.api.model.ExternalMusic
import com.ldz.external.inter.ISoundCloundService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, RequestParam}
import org.springframework.web.client.RestTemplate

/**
  * Created by Loic on 14/06/2017.
  */
@Controller
class SoundCloudController extends ExternalAPIClient{

  @Autowired
  val soundcloudService: ISoundCloundService = null

  @RequestMapping(method = Array(RequestMethod.GET), path = Array("/soundcloud/sourceurl"))
  override def getSoundcloudSourceurlFromRessource(@RequestParam("ressource") ressource: String): ResponseEntity[ExternalMusic] = {
    val restTemplate = new RestTemplate()
    val xmlRessource = restTemplate.getForObject(ressource, classOf[String])

    //extractId
    val musicid = soundcloudService.getMusicIdFromRessource(xmlRessource)
    val iframe = soundcloudService.getIframeRessourceFromMusicId(musicid)

    val outpuSeq = soundcloudService.getMusicparametersFromRessource(xmlRessource)

    def outputMapAccumulator(sequence: Seq[Tuple2[String, ExternalMusicKey.Value]],
                             mapAccumulator: Map[ExternalMusicKey.Value, String]): Map[ExternalMusicKey.Value, String] = {
      sequence match {
        case s if !s.isEmpty =>
          val mapAccumulatorInline = mapAccumulator + (s.head._2 -> s.head._1)
          outputMapAccumulator(s.tail, mapAccumulatorInline)
        case _ => mapAccumulator
      }

    }

    val outputMap = outputMapAccumulator(outpuSeq, Map())

    ResponseEntity.ok(ExternalMusic(musicid,iframe,outputMap))
  }
}
