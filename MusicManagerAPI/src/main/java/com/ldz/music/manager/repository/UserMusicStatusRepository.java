package com.ldz.music.manager.repository;

import com.ldz.music.manager.model.UserMusicStatus;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ldalzotto on 26/04/2017.
 */
public interface UserMusicStatusRepository extends JpaRepository<UserMusicStatus, Long> {

    public void deleteByUsername(String username);

    public UserMusicStatus findByUsername(String username);

}
