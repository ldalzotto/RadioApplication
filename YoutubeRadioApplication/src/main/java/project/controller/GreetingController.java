package project.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import project.model.UserRegister;
import project.service.userregister.inter.IUserRegisterService;

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
        return ResponseEntity.ok(registeredUser);
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
        iUserRegisterService.loginUserFromUsernameAndPasswordAndIpaddress(userRegister.getUsername(),
                userRegister.getPassword(), userRegister.getIpaddress());

        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUri();
        return ResponseEntity.created(location).body(userRegister);
    }

}
