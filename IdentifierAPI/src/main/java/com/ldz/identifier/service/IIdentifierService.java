package com.ldz.identifier.service;

import com.ldz.identifier.Model.bo.UserBO;
import com.ldz.identifier.Model.bo.UserDetailBO;
import com.ldz.identifier.model.UserDTO;

/**
 * Created by ldalzotto on 14/04/2017.
 */
public interface IIdentifierService {

    UserDTO getUserByUsernemeAndPassword(String username, String password);

    UserDTO getUserByUsername(String username);

    Boolean addUser(UserBO userBO);

    UserDTO addUserDetailFromUsername(String username, UserDetailBO userDetailBO);

    void deleteUserByUsername(String username);
}
