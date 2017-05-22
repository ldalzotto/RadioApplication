package com.ldz.identifier.Model.converter.beans;

import com.ldz.identifier.model.UserDetailDTO;
import com.ldz.converter.container.annot.BeanConverter;
import com.ldz.converter.container.inter.IConverter;
import com.ldz.identifier.Model.bo.UserBO;
import com.ldz.identifier.Model.bo.UserDetailBO;
import com.ldz.identifier.Model.bo.UserRoleBO;
import com.ldz.identifier.model.UserDTO;
import com.ldz.identifier.model.UserRoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ldalzotto on 14/04/2017.
 */
@BeanConverter(initialBeanClass = UserDTO.class, targetBeanClass = UserBO.class)
@Component
public class UserDTOtoUserBO implements IConverter<UserDTO, UserBO> {

    @Autowired
    IConverter<UserDetailDTO, UserDetailBO> userDetailDTOUserDetailBOIConverter;

    @Override
    public UserBO apply(UserDTO userDTO) {
        UserBO userBO = new UserBO();

        UserRoleDTO userRoleDTO = userDTO.getUserRoleDTO();
        if (userRoleDTO != null) {
            UserRoleBO userRoleBO = new UserRoleBO();
            userRoleBO.setRole(userRoleDTO.getRole());
            userBO.setUserRoleBO(userRoleBO);
        }

        List<UserDetailBO> userDetailBOS = userDTO.getUserDetailDTOS().stream().map(userDetailDTO -> {
            return userDetailDTOUserDetailBOIConverter.apply(userDetailDTO);
        }).collect(Collectors.toList());

        userBO.setUserName(userDTO.getUserName());
        userBO.setPassword(userDTO.getPassword());
        userBO.setUserDetailBOS(userDetailBOS);
        return userBO;
    }
}
