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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by ldalzotto on 14/04/2017.
 */
@BeanConverter(targetBeanClass = User.class, initialBeanClass = UserBO.class)
@Component
public class UserBOToUser implements IConverter<UserBO, User> {

    @Autowired
    IConverter<UserDetailBO, UserDetail> userDetailBOUserDetailIConverter;

    @Override
    public User apply(UserBO userBO) {
        User user = new User();

        UserRoleBO userRoleBO = userBO.getUserRoleBO();
        if (userRoleBO != null) {
            UserRole userRole = new UserRole();
            userRole.setRole(userRoleBO.getRole());
            user.setUserRole(userRole);
        }

        Set<UserDetail> userDetails = userBO.getUserDetailBOS().stream().map(userDetailBO -> {
            return userDetailBOUserDetailIConverter.apply(userDetailBO);
        }).collect(Collectors.toSet());

        user.setPassword(userBO.getPassword());
        user.setUsername(userBO.getUserName());
        user.setUserDetails(userDetails);

        return user;
    }
}
