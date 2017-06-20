package com.ldz.project.service.music;

import com.ldz.music.manager.MusicTypeClient;
import com.ldz.music.manager.model.UserMusicStatusDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ldz.project.service.music.inter.IMusicService;

/**
 * Created by ldalzotto on 03/05/2017.
 */
@Service
public class MusicService implements IMusicService {

    @Autowired
    MusicTypeClient musicManagerClient;

    @Override
    public Boolean addMusicFromUrlAndMusicPlatform(String username, String url, String musicPlatform) {
        String iframeUrl = musicManagerClient.getRessourceUrlFromUrlAndMusicplatform(url, musicPlatform).getBody();
        return (Boolean) musicManagerClient.postMusicFromSiteurl(username, iframeUrl).getBody();
    }

    @Override
    public UserMusicStatusDTO getMusicStatusOfCurrentClient(String username) {
        return musicManagerClient.getUserFromUsername(username).getBody();
    }
}
