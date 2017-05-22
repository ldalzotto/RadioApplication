package com.ldz.identifier.Model.converter.beans.user.detail;

import com.ldz.identifier.model.UserDetailDTO;
import com.ldz.converter.container.annot.BeanConverter;
import com.ldz.converter.container.inter.IConverter;
import com.ldz.identifier.Model.bo.UserDetailBO;
import org.springframework.stereotype.Component;

/**
 * Created by ldalzotto on 15/05/2017.
 */
@BeanConverter(initialBeanClass = UserDetailDTO.class, targetBeanClass = UserDetailBO.class)
@Component
public class UserDetailDTOtoBO implements IConverter<UserDetailDTO, UserDetailBO> {

    @Override
    public UserDetailBO apply(UserDetailDTO userDetailDTO) {
        UserDetailBO userDetailBO = new UserDetailBO();

        if (userDetailDTO != null) {
            userDetailBO.setCountry(userDetailDTO.getCountry());
            userDetailBO.setIpaddress(userDetailDTO.getIpaddress());
        }

        return userDetailBO;
    }
}
