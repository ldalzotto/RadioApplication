package music.manager;

import music.manager.model.UserMusicStatusDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ldalzotto on 25/04/2017.
 */
@FeignClient("music-manager-api")
public interface MusicTypeClient {

    @RequestMapping(method = RequestMethod.POST, path = "/user")
    public ResponseEntity<?> postUser(@RequestBody UserMusicStatusDTO userMusicStatusDTO);

    @RequestMapping(method = RequestMethod.GET, path = "/user/username/{username}")
    public ResponseEntity<UserMusicStatusDTO> getUserFromUsername(@PathVariable("username") String username);

    @RequestMapping(method = RequestMethod.DELETE, path = "/user/username/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable("username") String username);

    @RequestMapping(method = RequestMethod.GET, path = "/music/musicplatform/{musicplatform}/ressource-url")
    public ResponseEntity<String> getRessourceUrlFromUrlAndMusicplatform(@RequestParam("url") String url, @PathVariable("musicplatform") String musicplatform);

    @RequestMapping(method = RequestMethod.POST, path = "/music/username/{username}/from-source-url")
    public ResponseEntity<Boolean> postMusicFromSiteurl(@PathVariable("username") String username, @RequestParam("sourceurl") String sourceUrl);

}
