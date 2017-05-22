package com.ldz.music.manager.model.bo;

import com.ldz.music.manager.constants.MusicTypes;

/**
 * Created by ldalzotto on 26/04/2017.
 */
public class MusicTypeBO {

    private MusicTypes musicTypes;

    private String sourceUrl;

    public MusicTypes getMusicTypes() {
        return musicTypes;
    }

    public void setMusicTypes(MusicTypes musicTypes) {
        this.musicTypes = musicTypes;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }
}
