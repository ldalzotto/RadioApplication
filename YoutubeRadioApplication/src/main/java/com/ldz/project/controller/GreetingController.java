package com.ldz.project.controller;

import com.ldz.project.exception.AlreadyRegistered;
import com.ldz.project.exception.LoginWithUnknownIPException;
import com.ldz.project.exception.LoginWithUnkownUser;
import com.ldz.project.model.UserRegister;
import com.ldz.project.service.userregister.inter.IUserRegisterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

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
        UserRegister registeredUser = iUserRegisterService.loginUserFromEmailAndPasswordAndIpaddress(userRegister.getEmail(),
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
                    .registerUserFromUserDetails(userRegister);
        //login after register
        UserRegister userRegister1 = iUserRegisterService.loginUserFromEmailAndPasswordAndIpaddress(userRegister.getEmail(),
                userRegister.getPassword(), userRegister.getIpaddress());

        if(userRegister1 == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUri();
        return ResponseEntity.created(location).body(userRegister);
    }

    @RequestMapping(value = "/register/add/user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserRegister> addUser(@Valid UserRegister userRegister){
        UserRegister userRegister1 = iUserRegisterService.addUserFromexisting(userRegister);

        if(userRegister1 != null) {
            return ResponseEntity.ok(userRegister1);
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }

    }

    @RequestMapping(value = "/user/details/username/{username}", method = RequestMethod.GET)
    public ResponseEntity<List<UserRegister>> getUserdetailsFromUsername(@PathVariable("username") String username){
        List<UserRegister> userRegisters = iUserRegisterService.getDetailsFromusername(username);
        if(userRegisters != null){
            return ResponseEntity.ok(userRegisters);
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    @ExceptionHandler(LoginWithUnknownIPException.class)
    public ResponseEntity handleLoginWithUnknownIp(LoginWithUnknownIPException e){
        e.printStackTrace();

        String errorCode = "LOGIN_UNKNOWN_IP";
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorCode);
    }

    @ExceptionHandler(AlreadyRegistered.class)
    public ResponseEntity handleAlreadyRegistered(AlreadyRegistered alreadyRegistered){
        alreadyRegistered.printStackTrace();

        String errorCode = "ALREADY_REGISTERED";
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorCode);
    }

    @ExceptionHandler(LoginWithUnkownUser.class)
    public ResponseEntity handleLoginUnknown(LoginWithUnkownUser loginWithUnkownUser){
        loginWithUnkownUser.printStackTrace();

        String errorCode = "LOGIN_UNKNOWN_USER";
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorCode);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleOtherExeption(Exception e){
        e.printStackTrace();

        String errorCode = "AN_ERROR_OCCURED";
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorCode);
    }

}
