package com.ldz.project.service.userregister;

import com.ldz.project.constants.TokenManagerMapKeys;
import com.ldz.project.model.UserRegister;
import com.ldz.project.service.userregister.inter.IUserRegisterService;
import feign.FeignException;
import com.ldz.identifier.clients.IdentifierClient;
import com.ldz.identifier.constants.UserRoles;
import com.ldz.identifier.model.UserDTO;
import com.ldz.identifier.model.UserDetailDTO;
import com.ldz.identifier.model.UserRoleDTO;
import com.ldz.music.manager.MusicTypeClient;
import com.ldz.music.manager.model.UserMusicStatusDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.ldz.token.manager.client.TokenManagerClient;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ldalzotto on 15/04/2017.
 */
@Service
public class UserRegisterService implements IUserRegisterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRegisterService.class.getName());

    @Autowired
    IdentifierClient identifierAPIClient;

    @Autowired
    TokenManagerClient tokenManagerAPIClient;

    @Autowired
    MusicTypeClient musicManagerClient;

    public UserRegisterService() {

    }

    @Override
    public UserRegister loginUserFromUsernameAndPasswordAndIpaddress(String username, String password, String ipaddress) {
        UserDTO identifierAPIUser = identifierAPIClient.getPersonFromUsernameAndPassword(username, password).getBody();

        //assert that input ip correspond
        UserDetailDTO identifierAPIUserDetailFound = null;
        if (identifierAPIUser.getUserDetailDTOS() != null) {
            identifierAPIUserDetailFound = identifierAPIUser.getUserDetailDTOS().stream().filter(identifierAPIUserDetail -> {
                return identifierAPIUserDetail.getIpaddress().equals(ipaddress);
            }).findAny().orElse(null);
        }

        if (identifierAPIUserDetailFound == null) {
            throw new RuntimeException("User IP not recognized !");
        }

        //set login token
        Map<String, String> genericInfoToPut = new HashMap<>();
        genericInfoToPut.put(TokenManagerMapKeys.USERNAME.name(), username);
        genericInfoToPut.put(TokenManagerMapKeys.PASSWORD.name(), password);
        genericInfoToPut.put(TokenManagerMapKeys.IPADDRESS.name(), identifierAPIUserDetailFound.getIpaddress());
        genericInfoToPut.put(TokenManagerMapKeys.COUNTRY.name(), identifierAPIUserDetailFound.getCountry());
        tokenManagerAPIClient.postTokenWithIpaddress(identifierAPIUserDetailFound.getIpaddress(), genericInfoToPut);

        UserRegister userRegister = new UserRegister();
        userRegister.setUsername(identifierAPIUser.getUserName());
        userRegister.setPassword(identifierAPIUser.getPassword());
        userRegister.setIpaddress(ipaddress);

        return userRegister;
    }

    @Override
    public UserRegister getCurrentUserFromIpaddress(String ipaddress) {
        Map<String, Map<String, String>> genericToken = tokenManagerAPIClient.getTokenFromIpaddress(ipaddress).getBody();
        Map<String, String> genericTokenData = genericToken.entrySet().iterator().next().getValue();

        UserRegister userRegister = new UserRegister();
        userRegister.setIpaddress(genericTokenData.get(TokenManagerMapKeys.IPADDRESS.name()));
        userRegister.setPassword(genericTokenData.get(TokenManagerMapKeys.PASSWORD.name()));
        userRegister.setUsername(genericTokenData.get(TokenManagerMapKeys.USERNAME.name()));
        userRegister.setCountry(genericTokenData.get(TokenManagerMapKeys.COUNTRY.name()));

        return userRegister;
    }

    @Override
    public void logoutUserFromIpaddress(String ipaddress) {
        tokenManagerAPIClient.deleteTokenFromIpaddress(ipaddress);
    }

    @Override
    public boolean registerUserFromUserDetails(String username, String password, String ipaddress,
                                               String country) {

        try {
            registerIdentifier(username, password, ipaddress, country);
            registerMusic(username);
            return true;
        } catch (Exception e) {

            if ((e instanceof FeignException) && e.getMessage()
                    .contains(String.valueOf(HttpStatus.LOCKED.value())))
                throw e;

            //rollback
            deleteIdentifierApi(username);
            deleteMusicManagerClient(username);
            LOGGER.info("End executing error handling.");

            throw e;
        }

    }


    private boolean registerIdentifier(String username, String password, String ipaddress, String country) {
        LOGGER.info("Start executing identifier registering.");

        UserDTO identifierAPIUser = new UserDTO();
        identifierAPIUser.setPassword(password);
        identifierAPIUser.setUserName(username);

        UserRoleDTO identifierAPIUserRole = new UserRoleDTO();
        identifierAPIUserRole.setRole(UserRoles.ROLE_USER.getRole());

        identifierAPIUser.setUserRoleDTO(identifierAPIUserRole);

        //set userdetail
        UserDetailDTO identifierAPIUserDetail = new UserDetailDTO();
        identifierAPIUserDetail.setCountry(country);
        identifierAPIUserDetail.setIpaddress(ipaddress);

        identifierAPIUser.setUserDetailDTOS(Arrays.asList(identifierAPIUserDetail));

        identifierAPIClient.postUser(identifierAPIUser);

        LOGGER.info("End executing identifier registering.");
        return true;
    }

    private boolean registerMusic(String username) {
        LOGGER.info("Start executing music registering.");
        UserMusicStatusDTO musicManagerUserMusicStatus = new UserMusicStatusDTO();
        musicManagerUserMusicStatus.setUsername(username);

        musicManagerClient.postUser(musicManagerUserMusicStatus);
        LOGGER.info("End executing music registering.");
        return true;
    }

    private void deleteIdentifierApi(String username) {
        try {
            identifierAPIClient.deleteUser(username);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void deleteMusicManagerClient(String username) {
        try {
            musicManagerClient.deleteUser(username);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

}
