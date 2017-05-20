package music.manager.model.converter.beans.user.music.status.model.to.bo;

import converter.container.annot.BeanConverter;
import converter.container.inter.IConverter;
import music.manager.constants.MusicTypes;
import music.manager.model.MusicType;
import music.manager.model.bo.MusicTypeBO;
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


