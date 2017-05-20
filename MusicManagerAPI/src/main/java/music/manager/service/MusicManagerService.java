package music.manager.service;

import converter.container.ConverterContainer;
import external.api.ExternalAPIClient;
import music.manager.constants.MusicTypes;
import music.manager.model.MusicType;
import music.manager.model.UserMusicStatus;
import music.manager.model.bo.MusicTypeBO;
import music.manager.model.bo.UserMusicStatusBO;
import music.manager.repository.MusicTypeRepository;
import music.manager.repository.UserMusicStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ldalzotto on 26/04/2017.
 */
@Transactional
@Service
public class MusicManagerService implements IMusicManagerService {

    @Autowired
    ExternalAPIClient externalAPIClient;

    @Autowired
    MusicTypeRepository musicTypeRepository;

    @Autowired
    UserMusicStatusRepository userMusicStatusRepository;

    @Autowired
    ConverterContainer converterContainer;

    @Override
    public void addUserMusicStatus(UserMusicStatusBO userMusicStatusBO) {
        UserMusicStatus userMusicStatus = converterContainer.convert(userMusicStatusBO, UserMusicStatus.class);

        if (userMusicStatus.getMusicTypes() != null) {
            userMusicStatus.getMusicTypes().iterator().forEachRemaining(musicType -> {
                musicType.setUserMusicStatus(userMusicStatus);
                musicTypeRepository.save(musicType);
            });
        }

        userMusicStatusRepository.save(userMusicStatus);
    }

    @Override
    public void deleteUserMusicStatusFromUsername(String username) {
        userMusicStatusRepository.deleteByUsername(username);
    }

    @Override
    public UserMusicStatusBO getMusicStatusFromUsername(String username) {
        UserMusicStatus userMusicStatus = userMusicStatusRepository.findByUsername(username);
        return converterContainer.convert(userMusicStatus, UserMusicStatusBO.class);
    }

    @Override
    public String getSourceurlFromUrlAndMusicType(String url, MusicTypes musicType) {
        ResponseEntity<String> sourceUrl;

        switch (musicType) {
            case SOUNDCLOUD:
                sourceUrl = externalAPIClient.getSoundcloudSourceurlFromRessource(url);
                return sourceUrl.getBody();
        }

        return null;
    }

    @Override
    public boolean postMusicFromUsernameAndSourceurl(String username, String sourceUrl) {
        //retrieve user
        UserMusicStatus userMusicStatus = userMusicStatusRepository.findByUsername(username);

        if (userMusicStatus == null) {
            return false;
        }

        //add url
        MusicTypeBO newMusic = new MusicTypeBO();
        newMusic.setMusicTypes(MusicTypes.SOUNDCLOUD);
        newMusic.setSourceUrl(sourceUrl);

        MusicType musicTypeToSave = converterContainer.convert(newMusic, MusicType.class);
        musicTypeToSave.setUserMusicStatus(userMusicStatus);

        musicTypeRepository.save(musicTypeToSave);

        return true;
    }

}
