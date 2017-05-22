package com.ldz.identifier.Model.converter.beans.user.detail;

import com.ldz.identifier.Model.model.UserDetail;
import com.ldz.converter.container.annot.BeanConverter;
import com.ldz.converter.container.inter.IConverter;
import com.ldz.identifier.Model.bo.UserDetailBO;
import org.springframework.stereotype.Component;

/**
 * Created by ldalzotto on 15/05/2017.
 */
@BeanConverter(initialBeanClass = UserDetail.class, targetBeanClass = UserDetailBO.class)
@Component
public class UserDetailModelToBO implements IConverter<UserDetail, UserDetailBO> {
    @Override
    public UserDetailBO apply(UserDetail userDetail) {
        UserDetailBO userDetailBO = new UserDetailBO();

        if (userDetail != null) {
            userDetailBO.setIpaddress(userDetail.getIpaddress());
            userDetailBO.setCountry(userDetail.getCountry());
        }

        return userDetailBO;
    }
}
