package identifier.Model.converter.beans.user.detail;

import converter.container.annot.BeanConverter;
import converter.container.inter.IConverter;
import identifier.Model.bo.UserDetailBO;
import identifier.Model.model.UserDetail;
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
