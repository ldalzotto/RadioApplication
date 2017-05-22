package com.ldz.music.manager.model.converter.beans.user.music.status.dto.to.bo;

import com.ldz.converter.container.ConverterContainer;
import com.ldz.converter.container.annot.BeanConverter;
import com.ldz.converter.container.inter.IConverter;
import com.ldz.music.manager.model.UserMusicStatusDTO;
import com.ldz.music.manager.model.bo.MusicTypeBO;
import com.ldz.music.manager.model.bo.UserMusicStatusBO;
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
