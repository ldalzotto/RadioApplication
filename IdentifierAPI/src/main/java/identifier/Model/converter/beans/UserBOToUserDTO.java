package identifier.Model.converter.beans;

import converter.container.annot.BeanConverter;
import converter.container.inter.IConverter;
import identifier.Model.bo.UserBO;
import identifier.Model.bo.UserDetailBO;
import identifier.Model.bo.UserRoleBO;
import identifier.model.UserDTO;
import identifier.model.UserDetailDTO;
import identifier.model.UserRoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ldalzotto on 14/04/2017.
 */
@BeanConverter(initialBeanClass = UserBO.class, targetBeanClass = UserDTO.class)
@Component
public class UserBOToUserDTO implements IConverter<UserBO, UserDTO> {

    @Autowired
    IConverter<UserDetailBO, UserDetailDTO> userDetailBOUserDetailDTOIConverter;

    @Override
    public UserDTO apply(UserBO userBO) {
        UserDTO userDTO = new UserDTO();

        UserRoleBO userRoleBO = userBO.getUserRoleBO();
        if (userRoleBO != null) {
            UserRoleDTO userRoleDTO = new UserRoleDTO();
            userRoleDTO.setRole(userRoleBO.getRole());
            userDTO.setUserRoleDTO(userRoleDTO);
        }

        List<UserDetailDTO> userDetailDTOS = userBO.getUserDetailBOS().stream().map(userDetailBO -> {
            return userDetailBOUserDetailDTOIConverter.apply(userDetailBO);
        }).collect(Collectors.toList());

        userDTO.setPassword(userBO.getPassword());
        userDTO.setUserName(userBO.getUserName());
        userDTO.setUserDetailDTOS(userDetailDTOS);
        return userDTO;
    }
}
