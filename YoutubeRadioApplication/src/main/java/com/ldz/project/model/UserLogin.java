package com.ldz.project.model;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Loic on 06/06/2017.
 */
public class UserLogin {

    @NotNull
    @Size(min = 4)
    private String password;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String ipaddress;

    @Override
    public String toString() {
        return "UserLogin{" +
                "password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", ipaddress='" + ipaddress + '\'' +
                '}';
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }
}
