package com.ldz.identifier.Model.converter.beans.user.detail;

import com.ldz.identifier.model.UserDetailDTO;
import com.ldz.converter.container.annot.BeanConverter;
import com.ldz.converter.container.inter.IConverter;
import com.ldz.identifier.Model.bo.UserDetailBO;
import org.springframework.stereotype.Component;

/**
 * Created by ldalzotto on 15/05/2017.
 */
@BeanConverter(initialBeanClass = UserDetailBO.class, targetBeanClass = UserDetailDTO.class)
@Component
public class UserDetailBOtoDTO implements IConverter<UserDetailBO, UserDetailDTO> {

    @Override
    public UserDetailDTO apply(UserDetailBO userDetailBO) {
        UserDetailDTO userDetailDTO = new UserDetailDTO();

        if (userDetailBO != null) {
            userDetailDTO.setCountry(userDetailBO.getCountry());
            userDetailDTO.setIpaddress(userDetailBO.getIpaddress());
        }

        return userDetailDTO;
    }
}
