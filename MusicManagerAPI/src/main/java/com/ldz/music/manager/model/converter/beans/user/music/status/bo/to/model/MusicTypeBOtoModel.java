package com.ldz.music.manager.model.converter.beans.user.music.status.bo.to.model;

import com.ldz.music.manager.model.bo.MusicTypeBO;
import com.ldz.converter.container.annot.BeanConverter;
import com.ldz.converter.container.inter.IConverter;
import com.ldz.music.manager.model.MusicType;
import org.springframework.stereotype.Component;

/**
 * Created by ldalzotto on 26/04/2017.
 */
@Component
@BeanConverter(initialBeanClass = MusicTypeBO.class, targetBeanClass = MusicType.class)
public class MusicTypeBOtoModel implements IConverter<MusicTypeBO, MusicType> {

    @Override
    public MusicType apply(MusicTypeBO musicTypeBO) {
        MusicType musicType = new MusicType();

        if (musicTypeBO.getMusicTypes() != null) {
            musicType.setType(musicTypeBO.getMusicTypes().name());
            musicType.setSourceUrl(musicTypeBO.getSourceUrl());
        }

        return musicType;
    }

}
