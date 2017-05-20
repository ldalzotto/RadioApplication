package music.manager.model;

import music.manager.constants.MusicTypes;
import org.springframework.context.annotation.Configuration;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Created by ldalzotto on 25/04/2017.
 */
@Entity
@Table(name = "user_music_status", uniqueConstraints = @UniqueConstraint(columnNames = {"username"}))
public class UserMusicStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userMusicStatus")
    private Set<MusicType> musicTypes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<MusicType> getMusicTypes() {
        return musicTypes;
    }

    public void setMusicTypes(Set<MusicType> musicTypes) {
        this.musicTypes = musicTypes;
    }
}
