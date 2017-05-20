package music.manager.model.converter.beans.user.music.status.bo.to.dto;

import converter.container.annot.BeanConverter;
import converter.container.inter.IConverter;
import music.manager.model.MusicTypeDTO;
import music.manager.model.bo.MusicTypeBO;
import org.springframework.stereotype.Component;

/**
 * Created by ldalzotto on 26/04/2017.
 */
@Component
@BeanConverter(initialBeanClass = MusicTypeBO.class, targetBeanClass = MusicTypeDTO.class)
public class MusicTypeBOtoDTO implements IConverter<MusicTypeBO, MusicTypeDTO> {

    @Override
    public MusicTypeDTO apply(MusicTypeBO musicTypeBO) {
        MusicTypeDTO musicTypeDTO = new MusicTypeDTO();

        if (musicTypeBO != null) {
            musicTypeDTO.setSourceUrl(musicTypeBO.getSourceUrl());
            if (musicTypeBO.getMusicTypes() != null) {
                musicTypeDTO.setMusicType(musicTypeBO.getMusicTypes().name());
            }
        }

        return musicTypeDTO;
    }
}
