package com.ldz.token.manager.model;


import javax.persistence.*;
import java.util.Map;

/**
 * Created by loicd on 22/05/2017.
 */
@Entity
@Table( name = "token_manager")
public class Token {

    @Id
    @Column(name = "ipaddress")
    private String ipaddress;

    @ElementCollection(targetClass = String.class)
    @MapKeyColumn(name = "dataCached")
    @Column(name = "dataValue")
    private Map<String, String> dataCached;

    @Override
    public String toString() {
        return "Token{" +
                "ipaddress='" + ipaddress + '\'' +
                ", dataCached=" + dataCached +
                '}';
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public Map<String, String> getDataCached() {
        return dataCached;
    }

    public void setDataCached(Map<String, String> dataCached) {
        this.dataCached = dataCached;
    }
}
