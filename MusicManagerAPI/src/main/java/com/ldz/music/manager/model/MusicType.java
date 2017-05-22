package com.ldz.music.manager.model;

import javax.persistence.*;

/**
 * Created by ldalzotto on 25/04/2017.
 */
@Entity
@Table(name = "music_type")
public class MusicType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    private UserMusicStatus userMusicStatus;

    @Column(name = "type")
    private String type;

    @Column(name = "source_url")
    private String sourceUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public UserMusicStatus getUserMusicStatus() {
        return userMusicStatus;
    }

    public void setUserMusicStatus(UserMusicStatus userMusicStatus) {
        this.userMusicStatus = userMusicStatus;
    }
}
