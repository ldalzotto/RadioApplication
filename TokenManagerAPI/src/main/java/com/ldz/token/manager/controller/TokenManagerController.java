package com.ldz.token.manager.controller;

import com.ldz.token.manager.client.TokenManagerClient;
import com.ldz.token.manager.model.TokenDTO;
import com.ldz.token.manager.service.inter.ITokenCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * Created by ldalzotto on 17/05/2017.
 */
@Controller
public class TokenManagerController implements TokenManagerClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenManagerController.class.getName());

    @Autowired
    ITokenCacheService iTokenCacheService;

    public ResponseEntity<?> postTokenWithIpaddress(@PathVariable(name = "ipaddress") String ipaddress,
                                                    @RequestBody Map<String, String> genericInfo) {
        iTokenCacheService.addCacheDataFromIpaddress(ipaddress, genericInfo);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Map<String, Map<String, String>>> getTokenFromIpaddress(@PathVariable(name = "ipaddress") String ipaddress) {

        Map<String, Map<String, String>> listMapMap = iTokenCacheService.getCacheDataFromIpAddress(ipaddress);
        return ResponseEntity.ok(listMapMap);
    }

    public ResponseEntity<?> deleteTokenFromIpaddress(@PathVariable(name = "ipaddress") String ipaddress) {
        LOGGER.info("DELETE token called");
        iTokenCacheService.deleteTokenFromIpaddress(ipaddress);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<TokenDTO>> getAllTokenFromTs(@RequestParam("ts") String ts) {
        List<TokenDTO> tokenDTOS = iTokenCacheService.getAllTokenBeforeTs(ts);
        return ResponseEntity.ok(tokenDTOS);
    }
}
