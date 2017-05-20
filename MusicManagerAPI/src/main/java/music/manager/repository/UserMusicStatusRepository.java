package music.manager.repository;

import music.manager.model.UserMusicStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by ldalzotto on 26/04/2017.
 */
public interface UserMusicStatusRepository extends JpaRepository<UserMusicStatus, Long> {

    public void deleteByUsername(String username);

    public UserMusicStatus findByUsername(String username);

}
