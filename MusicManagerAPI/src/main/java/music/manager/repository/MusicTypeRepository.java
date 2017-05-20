package music.manager.repository;

import music.manager.model.MusicType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ldalzotto on 03/05/2017.
 */
public interface MusicTypeRepository extends JpaRepository<MusicType, Long> {
}
