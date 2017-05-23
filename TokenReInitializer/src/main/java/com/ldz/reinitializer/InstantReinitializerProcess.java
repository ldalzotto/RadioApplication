package com.ldz.reinitializer;

import com.ldz.token.manager.client.TokenManagerClient;
import com.ldz.token.manager.model.TokenDTO;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Created by Loic on 23/05/2017.
 */
@Component
public class InstantReinitializerProcess implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReInitializerProcess.class.getName());

    @Autowired
    TokenManagerClient tokenManagerClient;

    @Value("${token.reinitializer.intervaltime}")
    private String intervalTimeInMs;

    @Value("${token.reinitializer.core-pool-size}")
    private String corePoolSize;

    @Value("${parser.date-time-format}")
    private String parserFormat;

    @Override
    public void run() {

        DateTime now = DateTime.now();
        List<TokenDTO> tokenDTOS = tokenManagerClient.getAllTokenFromTs(DateTimeFormat.forPattern(parserFormat).print(now)).getBody();

        //creation of process
        List<DeletionProcess> deletionProcesses = tokenDTOS.stream().map(tokenDTO -> new DeletionProcess(tokenManagerClient, tokenDTO.getId())).collect(Collectors.toList());

        if(deletionProcesses.size() > 0){
            ExecutorService executorService = new ThreadPoolExecutor(Integer.valueOf(corePoolSize), Integer.valueOf(corePoolSize), 1000, TimeUnit.MILLISECONDS,
                    new ArrayBlockingQueue<Runnable>(deletionProcesses.size()));

            //get future
            List<Future<?>> futures = new ArrayList<>();

            deletionProcesses.stream().forEach(deletionProcess -> {
                futures.add(executorService.submit(deletionProcess));
            });

            while (futures.size() > 0){
                if(futures.get(0).isDone()){
                    futures.remove(0);
                }
            }
        }


    }

}
