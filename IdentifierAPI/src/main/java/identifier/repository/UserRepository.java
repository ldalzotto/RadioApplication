package identifier.repository;

import identifier.Model.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ldalzotto on 14/04/2017.
 */
public interface UserRepository extends CrudRepository<User, Long> {

    User findUserByUsernameAndPassword(String username, String password);

    User findUserByUsername(String username);

    Integer deleteByUsername(String username);
}
