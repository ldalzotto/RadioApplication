package com.ldz.music.manager.model

import javax.persistence.{_}

/**
  * Created by Loic on 20/06/2017.
  */
@Entity
@Table(name = "music_type")
class MusicType {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id") private var id = 0L

  @ManyToOne private var userMusicStatus: UserMusicStatus = _

  @Column(name = "type") private var `type`: String = _

  @Column(name = "source_url") private var sourceUrl : String = _

  def getId: Long = id

  def setId(id: Long): Unit = {
    this.id = id
  }

  def getType: String = `type`

  def setType(`type`: String): Unit = {
    this.`type` = `type`
  }

  def getSourceUrl: String = sourceUrl

  def setSourceUrl(sourceUrl: String): Unit = {
    this.sourceUrl = sourceUrl
  }

  def getUserMusicStatus: UserMusicStatus = userMusicStatus

  def setUserMusicStatus(userMusicStatus: UserMusicStatus): Unit = {
    this.userMusicStatus = userMusicStatus
  }



}
