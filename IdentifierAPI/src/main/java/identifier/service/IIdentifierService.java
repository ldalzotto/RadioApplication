package identifier.service;

import identifier.Model.bo.UserBO;
import identifier.model.UserDTO;

/**
 * Created by ldalzotto on 14/04/2017.
 */
public interface IIdentifierService {

    UserDTO getUserByUsernemeAndPassword(String username, String password);

    UserDTO getUserByUsername(String username);

    Boolean addUser(UserBO userBO);

    void deleteUserByUsername(String username);
}
