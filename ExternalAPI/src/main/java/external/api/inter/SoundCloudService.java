package external.api.inter;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by ldalzotto on 02/05/2017.
 */
@Service
public class SoundCloudService implements ISoundCloudService {

    private static final String SOUNDCLOUD_MUSIC_FINDER_TOKEN = "\"soundcloud://sounds:";
    private static final String SOUNDCLOUD_IFRAME_SRC_URL_FORMAT = "https://w.soundcloud.com/player/?url=https%3A//api.soundcloud.com/tracks/{IDENTIFIANT}&amp;auto_play=false&amp;hide_related=false&amp;show_comments=true&amp;show_user=true&amp;show_reposts=false&amp;visual=true";


    @Override
    public String getMusicIdFromRessource(String ressource) {
        Pattern pattern = Pattern.compile(SOUNDCLOUD_MUSIC_FINDER_TOKEN);

        StringBuilder ressourceBuilder = new StringBuilder(ressource);
        List<Integer> indexOfMatches = new ArrayList<>();

        Matcher matcher = pattern.matcher(ressource);
        while (matcher.find()) {
            indexOfMatches.add(matcher.end());
        }


        String musicId = indexOfMatches.stream().map(index -> {
            //find last index of numer from start
            int localIndex = index;
            char currentChar = ressource.charAt(localIndex);
            while (currentChar != '"') {
                localIndex++;
                currentChar = ressource.charAt(localIndex);
            }
            return ressourceBuilder.substring(index, localIndex);
        }).reduce((s, s2) -> {
            if (s.equals(s2)) {
                return s;
            }
            return null;
        }).get();

        return musicId;
    }

    @Override
    public String getIframeRessourceFromMusicId(String musicId) {
        return SOUNDCLOUD_IFRAME_SRC_URL_FORMAT.replace("{IDENTIFIANT}", musicId);
    }
}
