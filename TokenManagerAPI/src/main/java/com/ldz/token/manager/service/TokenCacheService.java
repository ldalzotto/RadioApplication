package com.ldz.token.manager.service;

import com.ldz.token.manager.model.Token;
import com.ldz.token.manager.repository.TokenRepository;
import com.ldz.token.manager.service.inter.ITokenCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * Created by ldalzotto on 17/05/2017.
 */
@Transactional
@Service
public class TokenCacheService implements ITokenCacheService {

    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public void addCacheDataFromIpaddress(String ipaddress, Map<String, String> data) {

        final Map<String, String> bufferMap = new HashMap<>();

        //check if data is not already present
        Token token = tokenRepository.findOne(ipaddress);
        if(token != null){
            if(token.getIpaddress().equals(ipaddress)){
                token.getDataCached().forEach((key, value) -> {
                    bufferMap.put(key, value);
                });
                bufferMap.putAll(data);

                token.setDataCached(bufferMap);
                tokenRepository.save(token);
            }

            bufferMap.putAll(data);
            token.setDataCached(bufferMap);
            tokenRepository.save(token);

        } else {
            bufferMap.putAll(data);
            token = new Token();
            token.setIpaddress(ipaddress);
            token.setDataCached(bufferMap);
            tokenRepository.save(token);
        }



    }

    @Override
    public Map<String, Map<String, String>> getCacheDataFromIpAddress(String ipaddress) {

        Map<String, Map<String, String>> bufferMap = new HashMap<>();

        Token token = tokenRepository.findOne(ipaddress);

        if(token != null){
            if(token.getIpaddress().equals(ipaddress)){
                Map<String, String> dataMap = new HashMap<>();
                token.getDataCached().forEach((s, s2) -> {
                    dataMap.put(s, s2);
                });
                bufferMap.put(ipaddress, dataMap);
            }
        }

        return bufferMap;
    }

    @Override
    public void deleteTokenFromIpaddress(String ipaddress) {

        tokenRepository.delete(ipaddress);
    }
}
