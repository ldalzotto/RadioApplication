package com.ldz.identifier.Model.bo;

/**
 * Created by ldalzotto on 15/05/2017.
 */
public class UserDetailBO {

    private String ipaddress;

    private String country;

    @Override
    public String toString() {
        return "UserDetailBO{" +
                "ipaddress='" + ipaddress + '\'' +
                ", country='" + country + '\'' +
                '}';
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
