package com.ldz.project.controller;

import com.ldz.identifier.constants.IdentifierColumnNames;
import com.ldz.identifier.constants.IdentifierTableNames;
import com.ldz.project.model.UserRegister;
import com.ldz.project.service.userregister.inter.IUserRegisterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

/**
 * Created by ldalzotto on 12/04/2017.
 */
@Controller
public class GreetingController {

    private Logger LOGGER = LoggerFactory.getLogger(GreetingController.class.getSimpleName());

    @Autowired
    IUserRegisterService iUserRegisterService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homepage() {
        return "homepage";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@Valid UserRegister userRegister) {
        UserRegister registeredUser = iUserRegisterService.loginUserFromUsernameAndPasswordAndIpaddress(userRegister.getUsername(),
                userRegister.getPassword(), userRegister.getIpaddress());

        if(registeredUser != null){
            return ResponseEntity.ok(registeredUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> logout(@Valid UserRegister userRegister) {
        iUserRegisterService.logoutUserFromIpaddress(userRegister.getIpaddress());
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/user/current/ipaddress/{ipaddress}", method = RequestMethod.GET)
    public ResponseEntity<UserRegister> getCurrentUserFromIpaddress(@PathVariable(name = "ipaddress") String ipaddress) {
        UserRegister userRegister = iUserRegisterService.getCurrentUserFromIpaddress(ipaddress);
        if (userRegister != null) {
            return ResponseEntity.ok(userRegister);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @RequestMapping(value = "/register/user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserRegister> register(@Valid UserRegister userRegister) {
            iUserRegisterService
                    .registerUserFromUserDetails(userRegister.getUsername(), userRegister.getPassword(),
                            userRegister.getIpaddress(), userRegister.getCountry());
        //login after register
        UserRegister userRegister1 = iUserRegisterService.loginUserFromUsernameAndPasswordAndIpaddress(userRegister.getUsername(),
                userRegister.getPassword(), userRegister.getIpaddress());

        if(userRegister1 == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUri();
        return ResponseEntity.created(location).body(userRegister);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity handleDataIntegrity(DataIntegrityViolationException e){
        Throwable throwable = e.getMostSpecificCause();
        String errorMessage = throwable.getMessage();

        String errorCode = "AN_ERROR_OCCURED";

        //build error regex
        String ipaddressRegex = IdentifierTableNames.USER_DETAIL + "(" + IdentifierColumnNames.IPADDRESS + ")";
        String usernameRegex = IdentifierTableNames.USERS + "(" + IdentifierColumnNames.USERNAME + ")";

        if(errorMessage.contains(ipaddressRegex)){
            errorCode = "IPADDRESS_ALREADY_EXIST";
        } else if(errorMessage.contains(usernameRegex)){
            errorCode = "USERNAME_ALREADY_EXIST";
        }

        return ResponseEntity.status(HttpStatus.LOCKED).body(errorCode);
    }

}
