package project.controller;

import music.manager.model.UserMusicStatusDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import project.service.music.inter.IMusicService;

import javax.ws.rs.QueryParam;
import java.net.URI;

/**
 * Created by ldalzotto on 03/05/2017.
 */
@Controller
public class MusicController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MusicController.class.getName());

    @Autowired
    IMusicService iMusicService;

    @RequestMapping(value = "user/{username}/music/musicplatform/{musicplatform}", method = RequestMethod.POST)
    public ResponseEntity<?> addMusicFromRessourceUrl(@PathVariable("username") String username,
                                                      @PathVariable("musicplatform") String musicPlatform, @QueryParam("url") String url) {
        iMusicService.addMusicFromUrlAndMusicPlatform(username, url, musicPlatform);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUri();
        return ResponseEntity.created(location).build();
    }

    @RequestMapping(value = "user/{username}/music/all", method = RequestMethod.GET)
    public ResponseEntity<UserMusicStatusDTO> getAllMusicUrls(@PathVariable("username") String username) {
        return ResponseEntity.ok(iMusicService.getMusicStatusOfCurrentClient(username));
    }


}
