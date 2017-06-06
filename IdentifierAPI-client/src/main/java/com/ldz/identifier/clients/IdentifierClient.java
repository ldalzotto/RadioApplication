package com.ldz.identifier.clients;

import com.ldz.identifier.model.UserDTO;
import com.ldz.identifier.model.UserDetailDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by ldalzotto on 18/04/2017.
 */
@FeignClient(name = "identifier-api")
public interface IdentifierClient {

    @RequestMapping(method = RequestMethod.GET, path = "/user/username/{username}")
    ResponseEntity<UserDTO> getPersonFromUsername(@PathVariable("username") String username);

    @RequestMapping(method = RequestMethod.GET, path = "/user/email/{email}/password/{password}")
    ResponseEntity<UserDTO> getPersonFromEmailAndPassword(@PathVariable("email") String email, @PathVariable("password") String password);

    @RequestMapping(method = RequestMethod.GET, path = "/user/email/{email}")
    ResponseEntity<UserDTO> getPersonFromEmail(@PathVariable("email") String email);

    @RequestMapping(method = RequestMethod.POST, path = "/user")
    ResponseEntity<Object> postUser(@RequestBody UserDTO userDTO);

    @RequestMapping(method = RequestMethod.POST, path = "/user/add/username/{username}")
    ResponseEntity<UserDTO> addUserIpaddressFromUsernaem(@PathVariable("username") String username,
                                                         @RequestBody UserDetailDTO userDetailDTO);

    @RequestMapping(method = RequestMethod.DELETE, path = "/user/username/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable("username") String username);

}