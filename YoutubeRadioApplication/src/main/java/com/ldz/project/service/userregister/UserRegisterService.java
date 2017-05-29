package com.ldz.project.service.userregister;

import com.ldz.identifier.clients.IdentifierClient;
import com.ldz.identifier.constants.UserRoles;
import com.ldz.identifier.model.UserDTO;
import com.ldz.identifier.model.UserDetailDTO;
import com.ldz.identifier.model.UserRoleDTO;
import com.ldz.music.manager.MusicTypeClient;
import com.ldz.music.manager.model.UserMusicStatusDTO;
import com.ldz.project.constants.TokenManagerMapKeys;
import com.ldz.project.exception.AlreadyRegistered;
import com.ldz.project.exception.LoginWithUnknownIPException;
import com.ldz.project.exception.LoginWithUnkownUser;
import com.ldz.project.model.UserRegister;
import com.ldz.project.service.userregister.inter.IUserRegisterService;
import com.ldz.token.manager.client.TokenManagerClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        if(identifierAPIUser != null){
            //assert that input ip correspond
            UserDetailDTO identifierAPIUserDetailFound = null;
            if (identifierAPIUser.getUserDetailDTOS() != null) {
                identifierAPIUserDetailFound = identifierAPIUser.getUserDetailDTOS().stream().filter(identifierAPIUserDetail -> {
                    return identifierAPIUserDetail.getIpaddress().equals(ipaddress);
                }).findAny().orElse(null);
            }

            if (identifierAPIUserDetailFound == null) {
                throw new LoginWithUnknownIPException("User IP not recognized !", null);
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
        } else {
            throw new LoginWithUnkownUser("User not recognized !", null);
        }

    }

    @Override
    public UserRegister addUserFromexisting(UserRegister userRegister) {

        UserDetailDTO userDetailDTO = new UserDetailDTO();
        userDetailDTO.setIpaddress(userRegister.getIpaddress());
        userDetailDTO.setCountry(userRegister.getCountry());

        identifierAPIClient.addUserIpaddressFromUsernaem(userRegister.getUsername(), userDetailDTO);

        //create token
        Map<String, String> genericData = new HashMap<>();
        genericData.put(TokenManagerMapKeys.USERNAME.name(), userRegister.getUsername());
        genericData.put(TokenManagerMapKeys.PASSWORD.name(), userRegister.getPassword());
        genericData.put(TokenManagerMapKeys.IPADDRESS.name(), userRegister.getIpaddress());
        genericData.put(TokenManagerMapKeys.COUNTRY.name(), userRegister.getCountry());

        tokenManagerAPIClient.postTokenWithIpaddress(userRegister.getIpaddress(), genericData);


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

        //récupération des détails du client
        List<UserRegister>  userRegisters = getDetailsFromusername(username);
        if(userRegisters != null && !userRegisters.isEmpty()){
            throw new AlreadyRegistered("The couple username : " + username + ", ipaddress : " + ipaddress +
                    " is already registered", null);
        }

        try {
            registerIdentifier(username, password, ipaddress, country);
            registerMusic(username);
        } catch (Exception e) {

            if ((e instanceof DataIntegrityViolationException))
                throw e;

            //rollback
            deleteIdentifierApi(username);
            deleteMusicManagerClient(username);
            LOGGER.info("End executing error handling.");

            throw e;
        }

        return true;
    }

    @Override
    public List<UserRegister> getDetailsFromusername(String username) {
        UserDTO userDTO = identifierAPIClient.getPersonFromUsername(username).getBody();
        List<UserRegister>  userRegisters = null;
        if(userDTO != null){
            String password = userDTO.getPassword();

            if(userDTO.getUserDetailDTOS() != null){
                userRegisters = userDTO.getUserDetailDTOS().stream().map(userDetailDTO -> {
                    UserRegister userRegister = new UserRegister();
                    userRegister.setUsername(username);
                    userRegister.setPassword(password);
                    userRegister.setIpaddress(userDetailDTO.getIpaddress());
                    userRegister.setCountry(userDetailDTO.getCountry());
                    return userRegister;
                }).collect(Collectors.toList());
            }
        }


        return userRegisters;
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
