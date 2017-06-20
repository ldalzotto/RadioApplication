package com.ldz.music.manager.repository

import com.ldz.music.manager.model.MusicType
import org.springframework.data.jpa.repository.JpaRepository

/**
  * Created by Loic on 20/06/2017.
  */
trait MusicTypeRepository extends JpaRepository[MusicType, java.lang.Long]{

}
