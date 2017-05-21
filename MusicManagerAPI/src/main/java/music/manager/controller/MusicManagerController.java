package music.manager.controller;

import converter.container.ConverterContainer;
import music.manager.MusicTypeClient;
import music.manager.constants.MusicTypes;
import music.manager.model.UserMusicStatusDTO;
import music.manager.model.bo.UserMusicStatusBO;
import music.manager.service.IMusicManagerService;
import org.h2.jdbc.JdbcSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

/**
 * Created by ldalzotto on 25/04/2017.
 */
@Controller
public class MusicManagerController implements MusicTypeClient {

    @Autowired
    IMusicManagerService iMusicManagerService;

    @Autowired
    ConverterContainer converterContainer;

    public ResponseEntity<?> postUser(@RequestBody @Valid UserMusicStatusDTO userMusicStatusDTO) {
        UserMusicStatusBO userMusicStatusBO =
                converterContainer.convert(userMusicStatusDTO, UserMusicStatusBO.class);
        try {
            iMusicManagerService.addUserMusicStatus(userMusicStatusBO);
        } catch (Exception e) {
            //if client existe déjà
            if (e.getCause() != null && e.getCause().getCause() instanceof JdbcSQLException) {
                return ResponseEntity.status(HttpStatus.LOCKED).build();
            } else
                throw e;
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUri();
        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<UserMusicStatusDTO> getUserFromUsername(
            @PathVariable(name = "username") String username) {
        UserMusicStatusBO userMusicStatusBO = iMusicManagerService.getMusicStatusFromUsername(username);
        UserMusicStatusDTO userMusicStatusDTO =
                converterContainer.convert(userMusicStatusBO, UserMusicStatusDTO.class);
        return ResponseEntity.ok(userMusicStatusDTO);
    }

    @Override
    public ResponseEntity<?> deleteUser(@PathVariable(name = "username") String username) {
        iMusicManagerService.deleteUserMusicStatusFromUsername(username);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<String> getRessourceUrlFromUrlAndMusicplatform(@RequestParam("url") String url,
                                                                         @PathVariable("musicplatform") String musicplatform) {

        MusicTypes musicTypes = MusicTypes.valueOf(musicplatform);
        String sourceUrl = iMusicManagerService.getSourceurlFromUrlAndMusicType(url, musicTypes);

        return ResponseEntity.ok(sourceUrl);
    }

    @Override
    public ResponseEntity<Boolean> postMusicFromSiteurl(@PathVariable("username") String username,
                                                        @RequestParam("sourceurl") String sourceUrl) {

        boolean isCreated = iMusicManagerService.postMusicFromUsernameAndSourceurl(username, sourceUrl);

        if (!isCreated) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }

        return ResponseEntity.noContent().build();
    }
}
