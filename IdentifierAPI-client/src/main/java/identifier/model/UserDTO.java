package identifier.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by ldalzotto on 14/04/2017.
 */
public class UserDTO {

    @NotNull
    private String userName;

    @NotNull
    @Size(min = 4, message = "The size of password must be > 4")
    private String password;

    @NotNull
    @Size(max = 1, message = "The number of details to instert must not be > 1")
    @Valid
    private List<UserDetailDTO> userDetailDTOS;

    @NotNull
    @Valid
    private UserRoleDTO userRoleDTO;

    @Override
    public String toString() {
        return "UserDTO{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", userDetailDTOS=" + userDetailDTOS +
                ", userRoleDTO=" + userRoleDTO +
                '}';
    }

    public UserRoleDTO getUserRoleDTO() {
        return userRoleDTO;
    }

    public void setUserRoleDTO(UserRoleDTO userRoleDTO) {
        this.userRoleDTO = userRoleDTO;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UserDetailDTO> getUserDetailDTOS() {
        return userDetailDTOS;
    }

    public void setUserDetailDTOS(List<UserDetailDTO> userDetailDTOS) {
        this.userDetailDTOS = userDetailDTOS;
    }
}
