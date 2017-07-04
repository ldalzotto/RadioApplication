package com.ldz.project.config

import org.apache.camel.impl.DefaultExecutorServiceManager
import org.apache.camel.spi.{ExecutorServiceManager, ThreadPoolProfile}
import org.apache.camel.spring.SpringCamelContext
import org.springframework.context.annotation.{Bean, Configuration}

/**
  * Created by Loic on 28/06/2017.
  */
@Configuration
class CamelConfig {

  import org.apache.camel.CamelContext
  import org.apache.camel.spring.boot.CamelContextConfiguration

  @Bean def contextConfiguration: CamelContextConfiguration = {

    new CamelContextConfiguration {override def afterApplicationStart(camelContext: CamelContext): Unit = {}

      override def beforeApplicationStart(camelContext: CamelContext): Unit = {

        val executor = new DefaultExecutorServiceManager(camelContext)

        val threadProfile =  new ThreadPoolProfile()
        threadProfile.setMaxPoolSize(20)
        threadProfile.setMaxQueueSize(100)
        threadProfile.setPoolSize(10)
        threadProfile.setId("threadPool")

        executor.setDefaultThreadPoolProfile(threadProfile)

        camelContext.setExecutorServiceManager(executor)
      }
    }

  }

}
