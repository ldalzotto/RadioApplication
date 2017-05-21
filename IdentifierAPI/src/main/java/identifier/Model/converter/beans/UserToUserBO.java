package identifier.Model.converter.beans;

import converter.container.annot.BeanConverter;
import converter.container.inter.IConverter;
import identifier.Model.bo.UserBO;
import identifier.Model.bo.UserDetailBO;
import identifier.Model.bo.UserRoleBO;
import identifier.Model.model.User;
import identifier.Model.model.UserDetail;
import identifier.Model.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ldalzotto on 14/04/2017.
 */
@BeanConverter(targetBeanClass = UserBO.class, initialBeanClass = User.class)
@Component
public class UserToUserBO implements IConverter<User, UserBO> {

    @Autowired
    IConverter<UserDetail, UserDetailBO> userDetailUserDetailBOIConverter;

    @Override
    public UserBO apply(User user) {
        UserBO userBO = new UserBO();

        UserRole userRole = user.getUserRole();

        if (userRole != null) {
            UserRoleBO userRoleBO = new UserRoleBO();
            userRoleBO.setRole(userRole.getRole());
            userBO.setUserRoleBO(userRoleBO);
        }

        List<UserDetailBO> userDetailBOS = user.getUserDetails().stream().map(userDetail -> {
            return userDetailUserDetailBOIConverter.apply(userDetail);
        }).collect(Collectors.toList());

        userBO.setPassword(user.getPassword());
        userBO.setUserName(user.getUsername());
        userBO.setUserDetailBOS(userDetailBOS);

        return userBO;
    }
}
