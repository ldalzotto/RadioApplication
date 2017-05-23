package com.ldz.token.manager.client;

import com.ldz.token.manager.model.TokenDTO;
import org.joda.time.DateTime;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
import java.util.List;
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

    @RequestMapping(method = RequestMethod.GET, path = "/token/all-from-ts")
    public ResponseEntity<List<TokenDTO>> getAllTokenFromTs(@RequestParam("ts") String ts);

}
