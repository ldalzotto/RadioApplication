package identifier.Model.bo;

import java.util.List;

/**
 * Created by ldalzotto on 14/04/2017.
 */
public class UserBO {

    private String userName;
    private String password;
    private UserRoleBO userRoleBO;
    private List<UserDetailBO> userDetailBOS;

    public UserRoleBO getUserRoleBO() {
        return userRoleBO;
    }

    public void setUserRoleBO(UserRoleBO userRoleBO) {
        this.userRoleBO = userRoleBO;
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

    public List<UserDetailBO> getUserDetailBOS() {
        return userDetailBOS;
    }

    public void setUserDetailBOS(List<UserDetailBO> userDetailBOS) {
        this.userDetailBOS = userDetailBOS;
    }

    @Override
    public String toString() {
        return "UserBO{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", userRoleBO=" + userRoleBO +
                ", userDetailBOS=" + userDetailBOS +
                '}';
    }
}
