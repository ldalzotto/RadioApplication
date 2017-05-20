package music.manager.model;

import custom.validation.EnumValidator;
import music.manager.constants.MusicTypes;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by ldalzotto on 25/04/2017.
 */
public class MusicTypeDTO {

    @NotNull
    @EnumValidator(enumClazz = MusicTypes.class)
    private String musicType;

    @NotNull
    @Pattern(regexp = "(^(https:\\/\\/)|(http:\\/\\/)).+")
    private String sourceUrl;

    public String getMusicType() {
        return musicType;
    }

    public void setMusicType(String musicType) {
        this.musicType = musicType;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }
}
