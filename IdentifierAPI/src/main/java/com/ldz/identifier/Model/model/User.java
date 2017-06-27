package com.ldz.identifier.Model.model;

import com.ldz.identifier.constants.IdentifierColumnNames;
import com.ldz.identifier.constants.IdentifierTableNames;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by ldalzotto on 14/04/2017.
 */
@Entity
@IdClass(UserId.class)
@Table(name = IdentifierTableNames.USERS, uniqueConstraints = @UniqueConstraint(columnNames = {IdentifierColumnNames.USERNAME, IdentifierColumnNames.EMAIL}))
public class User {

    @Id
    @Column(name = IdentifierColumnNames.USERNAME)
    private String username;

    @Id
    @Column(name = IdentifierColumnNames.EMAIL)
    private String email;

    @Id
    @Column(name = "password")
    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
    private Set<UserDetail> userDetails;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_id")
    private UserRole userRole;

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userDetails=" + userDetails +
                ", userRole=" + userRole +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public String getPassword() {
        return password;
    }

    public Set<UserDetail> getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(Set<UserDetail> userDetails) {
        this.userDetails = userDetails;
    }
}
