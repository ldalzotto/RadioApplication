package music.manager.service;

import music.manager.constants.MusicTypes;
import music.manager.model.bo.UserMusicStatusBO;

/**
 * Created by ldalzotto on 26/04/2017.
 */
public interface IMusicManagerService {

    public void addUserMusicStatus(UserMusicStatusBO userMusicStatusBO);

    public void deleteUserMusicStatusFromUsername(String username);

    public UserMusicStatusBO getMusicStatusFromUsername(String username);

    public String getSourceurlFromUrlAndMusicType(String url, MusicTypes musicType);

    public boolean postMusicFromUsernameAndSourceurl(String username, String sourceUrl);

}
