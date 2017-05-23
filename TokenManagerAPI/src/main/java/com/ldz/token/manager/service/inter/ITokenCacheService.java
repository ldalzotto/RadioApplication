package com.ldz.token.manager.service.inter;

import com.ldz.token.manager.model.TokenDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by ldalzotto on 17/05/2017.
 */
public interface ITokenCacheService {

    public void addCacheDataFromIpaddress(String ipaddress, Map<String, String> data);

    public Map<String, Map<String, String>> getCacheDataFromIpAddress(String ipaddress);

    public void deleteTokenFromIpaddress(String ipaddress);

    public List<TokenDTO> getAllTokenBeforeTs(String ts);

}
