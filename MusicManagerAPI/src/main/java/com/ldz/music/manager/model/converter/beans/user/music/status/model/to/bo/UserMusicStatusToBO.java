package com.ldz.music.manager.model.converter.beans.user.music.status.model.to.bo;

import com.ldz.music.manager.model.UserMusicStatus;
import com.ldz.converter.container.ConverterContainer;
import com.ldz.converter.container.annot.BeanConverter;
import com.ldz.converter.container.inter.IConverter;
import com.ldz.music.manager.model.bo.MusicTypeBO;
import com.ldz.music.manager.model.bo.UserMusicStatusBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Created by ldalzotto on 03/05/2017.
 */
@Component
@BeanConverter(initialBeanClass = UserMusicStatus.class, targetBeanClass = UserMusicStatusBO.class)
public class UserMusicStatusToBO implements IConverter<UserMusicStatus, UserMusicStatusBO> {

    @Autowired
    ConverterContainer converterContainer;

    @Override
    public UserMusicStatusBO apply(UserMusicStatus userMusicStatus) {
        UserMusicStatusBO userMusicStatusBO = new UserMusicStatusBO();

        if (userMusicStatus != null) {
            userMusicStatusBO.setUsername(userMusicStatus.getUsername());

            userMusicStatusBO.setMusicTypeBOs(
                    userMusicStatus.getMusicTypes().stream().map(musicType -> converterContainer.convert(musicType, MusicTypeBO.class))
                            .collect(Collectors.toList())
            );

        }

        return userMusicStatusBO;
    }
}
