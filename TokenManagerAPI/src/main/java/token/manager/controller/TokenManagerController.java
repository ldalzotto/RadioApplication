package token.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import token.manager.client.TokenManagerClient;
import token.manager.service.inter.ITokenCacheService;

import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * Created by ldalzotto on 17/05/2017.
 */
@Controller
public class TokenManagerController implements TokenManagerClient {

    @Autowired
    ITokenCacheService iTokenCacheService;

    public ResponseEntity<?> postTokenWithIpaddress(@PathVariable(name = "ipaddress") String ipaddress,
                                                    @RequestBody Map<String, String> genericInfo) {
        iTokenCacheService.addCacheDataFromIpaddress(ipaddress, genericInfo);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUri();
        return ResponseEntity.created(location).build();
    }

    public ResponseEntity<Map<String, Map<String, String>>> getTokenFromIpaddress(@PathVariable(name = "ipaddress") String ipaddress) {

        Map<String, Map<String, String>> listMapMap = iTokenCacheService.getCacheDataFromIpAddress(ipaddress);
        return ResponseEntity.ok(listMapMap);
    }

    public ResponseEntity<?> deleteTokenFromIpaddress(@PathVariable(name = "ipaddress") String ipaddress) {
        iTokenCacheService.deleteTokenFromIpaddress(ipaddress);
        return ResponseEntity.ok().build();
    }
}
