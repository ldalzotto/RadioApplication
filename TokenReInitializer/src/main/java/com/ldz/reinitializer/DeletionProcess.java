package com.ldz.reinitializer;

import com.ldz.token.manager.client.TokenManagerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by loicd on 23/05/2017.
 */
public class DeletionProcess implements Runnable {

    private TokenManagerClient tokenManagerClient;
    private String id;

    public DeletionProcess(TokenManagerClient tokenManagerClient, String id){
        this.tokenManagerClient = tokenManagerClient;
        this.id = id;
    }

    @Override
    public void run() {
        tokenManagerClient.deleteTokenFromIpaddress(id);
    }
}
