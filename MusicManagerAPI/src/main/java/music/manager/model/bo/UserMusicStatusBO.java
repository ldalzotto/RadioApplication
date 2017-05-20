package music.manager.model.bo;

import java.util.List;

/**
 * Created by ldalzotto on 26/04/2017.
 */
public class UserMusicStatusBO {

    private String username;

    private List<MusicTypeBO> musicTypeBOs;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<MusicTypeBO> getMusicTypeBOs() {
        return musicTypeBOs;
    }

    public void setMusicTypeBOs(List<MusicTypeBO> musicTypeBOs) {
        this.musicTypeBOs = musicTypeBOs;
    }
}
