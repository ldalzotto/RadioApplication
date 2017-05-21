package identifier.Model.converter.beans.user.detail;

import converter.container.annot.BeanConverter;
import converter.container.inter.IConverter;
import identifier.Model.bo.UserDetailBO;
import identifier.model.UserDetailDTO;
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
