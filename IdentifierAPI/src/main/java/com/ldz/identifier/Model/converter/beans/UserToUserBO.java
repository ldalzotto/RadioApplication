package com.ldz.identifier.Model.converter.beans;

import com.ldz.identifier.Model.bo.UserBO;
import com.ldz.identifier.Model.bo.UserRoleBO;
import com.ldz.identifier.Model.model.User;
import com.ldz.identifier.Model.model.UserDetail;
import com.ldz.identifier.Model.model.UserRole;
import com.ldz.converter.container.annot.BeanConverter;
import com.ldz.converter.container.inter.IConverter;
import com.ldz.identifier.Model.bo.UserDetailBO;
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
