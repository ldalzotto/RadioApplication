package com.ldz.identifier.controller;

import com.ldz.converter.container.ConverterContainer;
import com.ldz.identifier.Model.bo.UserBO;
import com.ldz.identifier.Model.bo.UserDetailBO;
import com.ldz.identifier.clients.IdentifierClient;
import com.ldz.identifier.model.UserDTO;
import com.ldz.identifier.model.UserDetailDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

/**
 * Created by ldalzotto on 14/04/2017.
 */
@Controller
public class IdentifierController implements IdentifierClient {

    private final static Logger LOGGER = LoggerFactory.getLogger(IdentifierController.class.getSimpleName());

    @Autowired
    com.ldz.identifier.service.IIdentifierService IIdentifierService;

    @Autowired
    ConverterContainer converterContainer;

    @Override
    public ResponseEntity<UserDTO> getPersonFromUsername(@PathVariable("username") String username) {
        UserDTO userDTO = IIdentifierService.getUserByUsername(username);

        if (userDTO != null) {
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<UserDTO> getPersonFromUsernameAndPassword(@PathVariable("username") String username,
                                                                    @PathVariable("password") String password) {
        UserDTO userDTO = IIdentifierService.getUserByUsernemeAndPassword(username, password);
        if (userDTO != null) {
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<UserDTO> addUserIpaddressFromUsernaem(@PathVariable("username") String username,
                                                                @RequestBody @Valid UserDetailDTO userDetailDTO) {
        UserDetailBO userDetailBO = converterContainer.convert(userDetailDTO, UserDetailBO.class);
        UserDTO userDTO = IIdentifierService.addUserDetailFromUsername(username, userDetailBO);

        if(userDTO != null){
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @Override
    public ResponseEntity<Object> postUser(@RequestBody @Valid UserDTO userDTO) {
        IIdentifierService.addUser(converterContainer.convert(userDTO, UserBO.class));
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUri();
        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<?> deleteUser(@PathVariable("username") @Valid String username) {
        IIdentifierService.deleteUserByUsername(username);
        return ResponseEntity.ok().build();
    }

}
