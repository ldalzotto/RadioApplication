package com.ldz.project.model;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by ldalzotto on 15/04/2017.
 */
@Component
public class UserRegister {

    @NotNull
    @Size(min = 4)
    private String username;

    @NotNull
    @Size(min = 4)
    private String password;

    @NotNull
    private String ipaddress;

    @NotNull
    private String country;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
