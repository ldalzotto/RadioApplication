package com.ldz.identifier.Model.bo;

/**
 * Created by ldalzotto on 14/04/2017.
 */
public class UserRoleBO {

    private String role;

    @Override
    public String toString() {
        return "UserRoleBO{" +
                "role='" + role + '\'' +
                '}';
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
