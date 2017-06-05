package com.ldz.identifier.Model.model;

import com.ldz.identifier.constants.IdentifierColumnNames;
import com.ldz.identifier.constants.IdentifierTableNames;

import javax.persistence.*;

/**
 * Created by ldalzotto on 15/05/2017.
 */
@Entity
@Table(name = IdentifierTableNames.USER_DETAIL)
public class UserDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "detail_user_id")
    private long id;

    @Column(name = IdentifierColumnNames.IPADDRESS)
    private String ipaddress;

    @Column(name = "country")
    private String country;

    @ManyToOne
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
