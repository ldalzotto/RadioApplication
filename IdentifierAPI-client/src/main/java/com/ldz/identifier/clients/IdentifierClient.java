package com.ldz.identifier.clients;

import com.ldz.identifier.model.UserDTO;
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

    @RequestMapping(method = RequestMethod.GET, path = "/user/username/{username}/password/{password}")
    ResponseEntity<UserDTO> getPersonFromUsernameAndPassword(@PathVariable("username") String username, @PathVariable("password") String password);

    @RequestMapping(method = RequestMethod.POST, path = "/user")
    ResponseEntity<Object> postUser(@RequestBody UserDTO userDTO);

    @RequestMapping(method = RequestMethod.DELETE, path = "/user/username/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable("username") String username);

}