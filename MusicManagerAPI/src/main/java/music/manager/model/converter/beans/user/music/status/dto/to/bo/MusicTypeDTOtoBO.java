package music.manager.model.converter.beans.user.music.status.dto.to.bo;

import converter.container.annot.BeanConverter;
import converter.container.inter.IConverter;
import music.manager.constants.MusicTypes;
import music.manager.model.MusicTypeDTO;
import music.manager.model.bo.MusicTypeBO;
import org.springframework.stereotype.Component;

/**
 * Created by ldalzotto on 26/04/2017.
 */
@Component
@BeanConverter(initialBeanClass = MusicTypeDTO.class, targetBeanClass = MusicTypeBO.class)
public class MusicTypeDTOtoBO implements IConverter<MusicTypeDTO, MusicTypeBO> {

    @Override
    public MusicTypeBO apply(MusicTypeDTO musicTypeDTO) {
        MusicTypeBO musicTypeBO = new MusicTypeBO();

        if (musicTypeDTO != null && musicTypeDTO.getMusicType() != null) {
            String musicType = musicTypeDTO.getMusicType();
            String sourceUrl = musicTypeDTO.getSourceUrl();
            musicTypeBO.setMusicTypes(MusicTypes.valueOf(musicType));
            musicTypeBO.setSourceUrl(sourceUrl);
        }

        return musicTypeBO;
    }

}
