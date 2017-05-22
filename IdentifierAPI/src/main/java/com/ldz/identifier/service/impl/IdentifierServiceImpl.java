package com.ldz.identifier.service.impl;

import com.ldz.identifier.Model.bo.UserBO;
import com.ldz.identifier.Model.model.User;
import com.ldz.identifier.model.UserDTO;
import com.ldz.identifier.repository.UserRepository;
import com.ldz.converter.container.ConverterContainer;
import com.ldz.identifier.service.IIdentifierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ldalzotto on 14/04/2017.
 */
@Service
@Transactional
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
    public void deleteUserByUsername(String username) {
        userRepository.deleteByUsername(username);
    }
}
