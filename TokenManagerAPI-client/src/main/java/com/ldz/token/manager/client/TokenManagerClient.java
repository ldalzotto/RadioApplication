package com.ldz.token.manager.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * Created by ldalzotto on 17/05/2017.
 */
@FeignClient("token-manager-api")
public interface TokenManagerClient {

    @RequestMapping(method = RequestMethod.POST, path = "/token/ipaddress/{ipaddress}")
    public ResponseEntity<?> postTokenWithIpaddress(@PathVariable(name = "ipaddress") String ipaddress,
                                                    @RequestBody Map<String, String> genericInfo);

    @RequestMapping(method = RequestMethod.GET, path = "/token/ipaddress/{ipaddress}")
    public ResponseEntity<Map<String, Map<String, String>>> getTokenFromIpaddress(@PathVariable(name = "ipaddress") String ipaddress);

    @RequestMapping(method = RequestMethod.DELETE, path = "/token/ipaddress/{ipaddress}")
    public ResponseEntity<?> deleteTokenFromIpaddress(@PathVariable(name = "ipaddress") String ipaddress);

}
