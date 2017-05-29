package com.ldz.identifier.service.impl;

import com.ldz.converter.container.ConverterContainer;
import com.ldz.identifier.Model.bo.UserBO;
import com.ldz.identifier.Model.bo.UserDetailBO;
import com.ldz.identifier.Model.model.User;
import com.ldz.identifier.Model.model.UserDetail;
import com.ldz.identifier.model.UserDTO;
import com.ldz.identifier.repository.UserRepository;
import com.ldz.identifier.service.IIdentifierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ldalzotto on 14/04/2017.
 */
@Service
public class IdentifierServiceImpl implements IIdentifierService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ConverterContainer converterContainer;

    @Override
    public UserDTO getUserByUsernemeAndPassword(String username, String password) {
        User user = userRepository.findUserByUsernameAndPassword(username, password);

        if (user == null)
            return null;

        return converterContainer.convert(converterContainer.convert(user, UserBO.class),
                UserDTO.class);
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findUserByUsername(username);

        if (user == null)
            return null;

        return converterContainer.convert(converterContainer.convert(user, UserBO.class),
                UserDTO.class);
    }

    @Override
    public Boolean addUser(UserBO userBO) {
        User userToSave = converterContainer.convert(userBO, User.class);
        //set hibernatedependency
        userToSave.getUserDetails().forEach(userDetail -> userDetail.setUser(userToSave));


        User user = userRepository.save(userToSave);
        if (user == null) {
            return false;
        }
        return true;
    }

    @Override
    public UserDTO addUserDetailFromUsername(String username, UserDetailBO userDetailBO) {
        User user = userRepository.findUserByUsername(username);

        //add the user detail
       UserDetail userDetail = converterContainer.convert(userDetailBO, UserDetail.class);
       userDetail.setUser(user);

       if(user != null){
           if(user.getUserDetails() != null){
               user.getUserDetails().add(userDetail);
           } else {
               Set<UserDetail> userDetails = new HashSet<>();
               userDetails.add(userDetail);
               user.setUserDetails(userDetails);
           }
       }

       //save
        User userSaved = userRepository.save(user);
        if(userSaved != null){
            UserDTO userDTO = converterContainer.convert(converterContainer.convert(userSaved, UserBO.class), UserDTO.class);
        }
        return userDTO;
    }

    @Override
    public void deleteUserByUsername(String username) {
        userRepository.deleteByUsername(username);
    }
}
