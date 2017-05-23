package com.ldz.token.manager.service;

import com.ldz.converter.container.inter.IConverter;
import com.ldz.token.manager.model.Token;
import com.ldz.token.manager.model.TokenDTO;
import com.ldz.token.manager.repository.TokenRepository;
import com.ldz.token.manager.service.inter.ITokenCacheService;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by ldalzotto on 17/05/2017.
 */
@Transactional
@Service
public class TokenCacheService implements ITokenCacheService {

    @Value("${parser.date-time-format}")
    private String parserFormat;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private IConverter<Token, TokenDTO> tokenTokenDTOIConverter;

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

                token.setTs(DateTime.now());
                token.setDataCached(bufferMap);
                tokenRepository.save(token);
            }

            bufferMap.putAll(data);
            token.setTs(DateTime.now());
            token.setDataCached(bufferMap);
            tokenRepository.save(token);

        } else {
            bufferMap.putAll(data);
            token = new Token();
            token.setIpaddress(ipaddress);
            token.setTs(DateTime.now());
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

    @Override
    public List<TokenDTO> getAllTokenBeforeTs(String ts) {
        List<Token> tokens = tokenRepository.findAllBytsBefore(DateTime.parse(ts, DateTimeFormat.forPattern(parserFormat)));
        return tokens.stream().map(token -> tokenTokenDTOIConverter.apply(token)).collect(Collectors.toList());
    }
}
