package com.ldz.reinitializer.controller;

import com.ldz.reinitializer.ReInitializerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Loic on 23/05/2017.
 */
@Controller
public class TokenReInitializerController {

    @Autowired
    ReInitializerService reInitializerService;

    @RequestMapping(method = RequestMethod.PUT, path = "/token/reinitialize/manual-trigger")
    public ResponseEntity<?> tokenReinitializerManualTrigger(){
        reInitializerService.manualTrigger();
        return ResponseEntity.ok().build();
    }

}
