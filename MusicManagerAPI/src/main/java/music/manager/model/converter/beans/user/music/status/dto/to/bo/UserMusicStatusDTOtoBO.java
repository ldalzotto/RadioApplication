package music.manager.model.converter.beans.user.music.status.dto.to.bo;

import converter.container.ConverterContainer;
import converter.container.annot.BeanConverter;
import converter.container.inter.IConverter;
import music.manager.model.UserMusicStatusDTO;
import music.manager.model.bo.MusicTypeBO;
import music.manager.model.bo.UserMusicStatusBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ldalzotto on 26/04/2017.
 */
@Component
@BeanConverter(initialBeanClass = UserMusicStatusDTO.class, targetBeanClass = UserMusicStatusBO.class)
public class UserMusicStatusDTOtoBO implements IConverter<UserMusicStatusDTO, UserMusicStatusBO> {

    @Autowired ConverterContainer converterContainer;

    @Override public UserMusicStatusBO apply(UserMusicStatusDTO userMusicStatusDTO) {
        UserMusicStatusBO userMusicStatusBO = new UserMusicStatusBO();

        if (userMusicStatusDTO != null) {
            userMusicStatusBO.setUsername(userMusicStatusDTO.getUsername());
        }

        if (userMusicStatusDTO.getMusicTypeDTO() != null) {
            List<MusicTypeBO> musicTypeBOS = userMusicStatusDTO.getMusicTypeDTO().stream()
                    .map(musicTypeDTO -> converterContainer.convert(musicTypeDTO, MusicTypeBO.class))
                    .collect(Collectors.toList());
            userMusicStatusBO.setMusicTypeBOs(musicTypeBOS);
        }

        return userMusicStatusBO;
    }

}
