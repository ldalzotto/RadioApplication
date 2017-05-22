package com.ldz.music.manager.model.converter.beans.user.music.status.model.to.bo;

import com.ldz.converter.container.annot.BeanConverter;
import com.ldz.converter.container.inter.IConverter;
import com.ldz.music.manager.constants.MusicTypes;
import com.ldz.music.manager.model.MusicType;
import com.ldz.music.manager.model.bo.MusicTypeBO;
import org.springframework.stereotype.Component;

/**
 * Created by ldalzotto on 03/05/2017.
 */
@Component
@BeanConverter(initialBeanClass = MusicType.class, targetBeanClass = MusicTypeBO.class)
public class MusicTypeModelToBO implements IConverter<MusicType, MusicTypeBO> {

    @Override
    public MusicTypeBO apply(MusicType musicType) {
        MusicTypeBO musicTypeBO = new MusicTypeBO();

        if (musicType != null) {
            musicTypeBO.setSourceUrl(musicType.getSourceUrl());
            musicTypeBO.setMusicTypes(MusicTypes.valueOf(musicType.getType()));
        }

        return musicTypeBO;
    }
}


