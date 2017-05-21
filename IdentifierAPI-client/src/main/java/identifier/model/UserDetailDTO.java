package identifier.model;

import javax.validation.constraints.NotNull;

/**
 * Created by ldalzotto on 15/05/2017.
 */
public class UserDetailDTO {

    @NotNull
    private String ipaddress;

    @NotNull
    private String country;

    @Override
    public String toString() {
        return "UserDetailDTO{" +
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
