package music.manager.model.converter.beans.user.music.status.bo.to.dto;

import converter.container.ConverterContainer;
import converter.container.annot.BeanConverter;
import converter.container.inter.IConverter;
import music.manager.model.MusicTypeDTO;
import music.manager.model.UserMusicStatusDTO;
import music.manager.model.bo.UserMusicStatusBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by ldalzotto on 26/04/2017.
 */
@Component
@BeanConverter(initialBeanClass = UserMusicStatusBO.class, targetBeanClass = UserMusicStatusDTO.class)
public class UserMusicStatusBOtoDTO implements IConverter<UserMusicStatusBO, UserMusicStatusDTO> {

    @Autowired
    ConverterContainer converterContainer;

    @Override
    public UserMusicStatusDTO apply(UserMusicStatusBO userMusicStatusBO) {
        UserMusicStatusDTO userMusicStatus = new UserMusicStatusDTO();

        if (userMusicStatusBO.getUsername() != null) {
            userMusicStatus.setUsername(userMusicStatusBO.getUsername());
        }

        if (userMusicStatusBO.getMusicTypeBOs() != null) {
            List<MusicTypeDTO> musicTypes = userMusicStatusBO.getMusicTypeBOs()
                    .stream().map(musicTypeBO -> converterContainer.convert(musicTypeBO, MusicTypeDTO.class))
                    .collect(Collectors.toList());
            userMusicStatus.setMusicTypeDTO(musicTypes);
        }

        return userMusicStatus;
    }
}
