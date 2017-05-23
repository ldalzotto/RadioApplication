package com.ldz.reinitializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by loicd on 23/05/2017.
 */
@Service
public class ReInitializerService {

    @Value("${token.reinitializer.intervaltime}")
    private String intervalTimeInMs;

    @Autowired
    private ReInitializerProcess reInitializerProcess;

    private ScheduledExecutorService scheduler;

    @PostConstruct
    public void initializeScheduler(){
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(reInitializerProcess, Long.valueOf(intervalTimeInMs), Long.valueOf(intervalTimeInMs), TimeUnit.MILLISECONDS);
    }

}
