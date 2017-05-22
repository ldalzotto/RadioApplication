package com.ldz.identifier.Model.converter.beans.user.detail;

import com.ldz.converter.container.annot.BeanConverter;
import com.ldz.converter.container.inter.IConverter;
import com.ldz.identifier.Model.bo.UserDetailBO;
import com.ldz.identifier.Model.model.UserDetail;
import org.springframework.stereotype.Component;

/**
 * Created by ldalzotto on 15/05/2017.
 */
@BeanConverter(initialBeanClass = UserDetailBO.class, targetBeanClass = UserDetail.class)
@Component
public class UserDetailBOtoModel implements IConverter<UserDetailBO, UserDetail> {

    @Override
    public UserDetail apply(UserDetailBO userDetailBO) {

        UserDetail userDetail = new UserDetail();

        if (userDetailBO != null) {
            userDetail.setCountry(userDetailBO.getCountry());
            userDetail.setIpaddress(userDetailBO.getIpaddress());
        }

        return userDetail;
    }
}
