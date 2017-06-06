package com.ldz.identifier.service;

import com.ldz.identifier.Model.bo.UserBO;
import com.ldz.identifier.Model.bo.UserDetailBO;
import com.ldz.identifier.model.UserDTO;

/**
 * Created by ldalzotto on 14/04/2017.
 */
public interface IIdentifierService {

    UserDTO getUserByEmailAndPassWord(String email, String password);

    UserDTO getUserByUsername(String username);

    UserDTO getUserByEmail(String email);

    Boolean addUser(UserBO userBO);

    UserDTO addUserDetailFromUsername(String username, UserDetailBO userDetailBO);

    void deleteUserByUsername(String username);
}
