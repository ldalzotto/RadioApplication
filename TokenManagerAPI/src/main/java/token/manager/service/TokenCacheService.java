package token.manager.service;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import token.manager.service.inter.ITokenCacheService;

import java.util.*;

/**
 * Created by ldalzotto on 17/05/2017.
 */
@Service
public class TokenCacheService implements ITokenCacheService {

    private Map<String, Map<String, String>> tokenCache = new HashMap<>();

    @Override
    public void addCacheDataFromIpaddress(String ipaddress, Map<String, String> data) {

        Map<String, Map<String, String>> bufferMap = new HashMap<>();

        //check if data is not already present
        tokenCache.entrySet().stream().filter(listMapEntry -> {
            if (listMapEntry.getKey().equals(ipaddress)) {
                return true;
            }
            return false;
        }).forEach(listMapEntry -> {
            //contruction clé
            String tempIp = listMapEntry.getKey().toString();
            Map<String, String> tempData = new HashMap<>(listMapEntry.getValue());
            tempData.putAll(data);
            bufferMap.put(tempIp, tempData);
        });

        if (bufferMap.isEmpty()) {
            //new value
            String tempIp = ipaddress.toString();
            Map<String, String> tempData = new HashMap<>(data);
            bufferMap.put(tempIp, tempData);
        }

        tokenCache.putAll(bufferMap);
    }

    @Override
    public Map<String, Map<String, String>> getCacheDataFromIpAddress(String ipaddress) {

        Map<String, Map<String, String>> bufferMap = new HashMap<>();

        Map.Entry<String, Map<String, String>> entry = tokenCache.entrySet().stream().filter(listMapEntry -> {
            if (listMapEntry.getKey().equals(ipaddress)) {
                return true;
            }
            return false;
        }).findAny().orElse(null);

        if (entry != null) {
            if (entry.getKey() != null && entry.getValue() != null) {
                bufferMap.put(entry.getKey(), entry.getValue());
            }
        }


        return bufferMap;
    }

    @Override
    public void deleteTokenFromIpaddress(String ipaddress) {
        Map<String, Map<String, String>> dataFromIpAddress = getCacheDataFromIpAddress(ipaddress);
        Map.Entry<String, Map<String, String>> listMapEntry = dataFromIpAddress.entrySet().iterator().next();
        if (listMapEntry.getKey().equals(ipaddress)) {
            tokenCache.remove(listMapEntry.getKey());
        }
    }
}
