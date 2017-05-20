package music.manager.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

import static javax.swing.text.StyleConstants.Size;

/**
 * Created by ldalzotto on 25/04/2017.
 */
public class UserMusicStatusDTO {

    @NotNull
    private String username;

    @Valid
    @Size(min = 0)
    private List<MusicTypeDTO> musicTypeDTO;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<MusicTypeDTO> getMusicTypeDTO() {
        return musicTypeDTO;
    }

    public void setMusicTypeDTO(List<MusicTypeDTO> musicTypeDTO) {
        this.musicTypeDTO = musicTypeDTO;
    }
}
