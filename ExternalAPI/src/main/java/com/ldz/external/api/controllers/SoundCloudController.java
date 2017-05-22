package com.ldz.external.api.controllers;

import com.ldz.external.api.ExternalAPIClient;
import com.ldz.external.api.inter.ISoundCloudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

/**
 * Created by ldalzotto on 02/05/2017.
 */
@Controller
public class SoundCloudController implements ExternalAPIClient {

    @Autowired
    ISoundCloudService iSoundCloudService;

    @RequestMapping(method = RequestMethod.GET, path = "/soundcloud/sourceurl")
    @Override
    public ResponseEntity<String> getSoundcloudSourceurlFromRessource(@RequestParam("ressource") String ressource) {
        RestTemplate restTemplate = new RestTemplate();
        String ressourceResult = restTemplate.getForObject(ressource, String.class);

        //extractId
        String musicId = iSoundCloudService.getMusicIdFromRessource(ressourceResult);
        String srcUrlResult = iSoundCloudService.getIframeRessourceFromMusicId(musicId);

        return ResponseEntity.ok(srcUrlResult);
    }
}
