package com.ldz.identifier.Model.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by ldalzotto on 17/05/2017.
 */
public class UserId implements Serializable {

    private String username;
    private String password;
    private String email;

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

    @Override
    public String toString() {
        return "UserId{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserId userId = (UserId) o;
        return Objects.equals(username, userId.username) &&
                Objects.equals(password, userId.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
