package com.ldz.identifier.Model.converter.beans;

import com.ldz.identifier.Model.bo.UserBO;
import com.ldz.identifier.Model.bo.UserRoleBO;
import com.ldz.identifier.Model.model.User;
import com.ldz.identifier.Model.model.UserRole;
import com.ldz.converter.container.annot.BeanConverter;
import com.ldz.converter.container.inter.IConverter;
import com.ldz.identifier.Model.bo.UserDetailBO;
import com.ldz.identifier.Model.model.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
