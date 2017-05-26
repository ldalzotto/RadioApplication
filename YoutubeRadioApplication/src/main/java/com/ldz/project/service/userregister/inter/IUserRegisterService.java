package com.ldz.project.service.userregister.inter;


import com.ldz.project.model.UserRegister;

import java.util.List;

/**
 * Created by ldalzotto on 15/04/2017.
 */
public interface IUserRegisterService {

    public boolean registerUserFromUserDetails(String username, String password, String ipAddress,
                                               String country);

    public List<UserRegister> getDetailsFromusername(String username);

    public UserRegister getCurrentUserFromIpaddress(String ipaddress);

    public UserRegister addUserFromexisting(UserRegister userRegister);

    public void logoutUserFromIpaddress(String ipaddress);

    public UserRegister loginUserFromUsernameAndPasswordAndIpaddress(String username, String password,
                                                                     String ipaddress);
}
