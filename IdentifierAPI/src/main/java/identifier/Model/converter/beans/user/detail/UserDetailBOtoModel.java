package identifier.Model.converter.beans.user.detail;

import converter.container.annot.BeanConverter;
import converter.container.inter.IConverter;
import identifier.Model.bo.UserDetailBO;
import identifier.Model.model.UserDetail;
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
