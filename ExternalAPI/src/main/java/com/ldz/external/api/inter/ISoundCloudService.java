package com.ldz.external.api.inter;

import org.springframework.stereotype.Service;

/**
 * Created by ldalzotto on 02/05/2017.
 */
public interface ISoundCloudService {

    public String getMusicIdFromRessource(String ressource);

    public String getIframeRessourceFromMusicId(String musicId);

}
