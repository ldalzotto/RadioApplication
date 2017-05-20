package music.manager.model.converter.beans.user.music.status.bo.to.model;

import converter.container.annot.BeanConverter;
import converter.container.inter.IConverter;
import music.manager.model.MusicType;
import music.manager.model.bo.MusicTypeBO;
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
