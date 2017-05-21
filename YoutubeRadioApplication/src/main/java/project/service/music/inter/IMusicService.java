package project.service.music.inter;

import music.manager.model.UserMusicStatusDTO;

/**
 * Created by ldalzotto on 03/05/2017.
 */
public interface IMusicService {

    public Boolean addMusicFromUrlAndMusicPlatform(String username, String url, String musicPlatform);

    public UserMusicStatusDTO getMusicStatusOfCurrentClient(String username);
}
