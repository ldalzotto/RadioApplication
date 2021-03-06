package com.ldz.identifier.model;

import com.ldz.identifier.constants.UserRoles;
import com.ldz.identifier.validation.EnumValidator;

import javax.validation.constraints.NotNull;

/**
 * Created by ldalzotto on 14/04/2017.
 */
public class UserRoleDTO {

    @NotNull
    @EnumValidator(enumClazz = UserRoles.class)
    private String role;

    @Override
    public String toString() {
        return "UserRoleDTO{" +
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
