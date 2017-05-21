package identifier.Model.converter.beans.user.detail;

import converter.container.annot.BeanConverter;
import converter.container.inter.IConverter;
import identifier.Model.bo.UserDetailBO;
import identifier.model.UserDetailDTO;
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
