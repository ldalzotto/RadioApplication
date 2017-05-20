package music.manager.model.converter.beans.user.music.status.bo.to.model;

import converter.container.ConverterContainer;
import converter.container.annot.BeanConverter;
import converter.container.inter.IConverter;
import music.manager.model.MusicType;
import music.manager.model.UserMusicStatus;
import music.manager.model.bo.UserMusicStatusBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by ldalzotto on 26/04/2017.
 */
@Component
@BeanConverter(initialBeanClass = UserMusicStatusBO.class, targetBeanClass = UserMusicStatus.class)
public class UserMusicStatusBOtoModel implements IConverter<UserMusicStatusBO, UserMusicStatus> {

    @Autowired ConverterContainer converterContainer;

    @Override public UserMusicStatus apply(UserMusicStatusBO userMusicStatusBO) {
        UserMusicStatus userMusicStatus = new UserMusicStatus();

        if (userMusicStatusBO.getUsername() != null) {
            userMusicStatus.setUsername(userMusicStatusBO.getUsername());
        }

        if (userMusicStatusBO.getMusicTypeBOs() != null) {
            Set<MusicType> musicTypes = userMusicStatusBO.getMusicTypeBOs()
                    .stream().map(musicTypeBO -> converterContainer.convert(musicTypeBO, MusicType.class))
                    .collect(Collectors.toSet());
            userMusicStatus.setMusicTypes(musicTypes);
        }

        return userMusicStatus;
    }
}
