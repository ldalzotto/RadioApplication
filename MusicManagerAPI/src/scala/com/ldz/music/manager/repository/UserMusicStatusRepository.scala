package com.ldz.music.manager.repository

import com.ldz.music.manager.model.UserMusicStatus
import org.springframework.data.jpa.repository.JpaRepository

/**
  * Created by Loic on 20/06/2017.
  */
trait UserMusicStatusRepository extends JpaRepository[UserMusicStatus, java.lang.Long]{

  def deleteByUsername(username: String): Unit
  def findByUsername(username: String): UserMusicStatus
}
