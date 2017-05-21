package external.api;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by loicd on 20/05/2017.
 */
@FeignClient("external-api")
public interface ExternalAPIClient {

    @RequestMapping(method = RequestMethod.GET, path = "/soundcloud/sourceurl")
    public ResponseEntity<String> getSoundcloudSourceurlFromRessource(@RequestParam("ressource") String ressource);

}
