package com.ldz.music.manager.repository;

import com.ldz.music.manager.model.MusicType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ldalzotto on 03/05/2017.
 */
public interface MusicTypeRepository extends JpaRepository<MusicType, Long> {
}
