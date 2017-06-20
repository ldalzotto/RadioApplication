package com.ldz.music.manager.model

import javax.persistence._

/**
  * Created by Loic on 20/06/2017.
  */
@Entity
@Table(name = "user_music_status", uniqueConstraints = Array(new UniqueConstraint(columnNames = Array("username"))))
class UserMusicStatus {


  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id") private var id: Long = 0L
  @Column(name = "username") private var username: String = _
  @OneToMany(cascade = Array(CascadeType.ALL), mappedBy = "userMusicStatus")
  private var musicTypes: java.util.List[MusicType] = _

  def getId: Long = id

  def setId(id: Long): Unit = {
    this.id = id
  }

  def getUsername: String = username

  def setUsername(username: String): Unit = {
    this.username = username
  }

  def getMusicTypes: java.util.List[MusicType] = musicTypes

  def setMusicTypes(musicTypes: java.util.List[MusicType]): Unit = {
    this.musicTypes = musicTypes
  }
}
