package com.ldz.project.config

import org.apache.camel.spi.ThreadPoolProfile
import org.springframework.context.annotation.{Bean, Configuration}

/**
  * Created by Loic on 28/06/2017.
  */
@Configuration
class CamelConfig {

  @Bean
  def threadPoolProfile: ThreadPoolProfile = {
    val threadProfile =  new ThreadPoolProfile()
    threadProfile.setMaxPoolSize(20)
    threadProfile.setMaxQueueSize(100)
    threadProfile.setPoolSize(10)
    threadProfile.setId("threadPool")
    threadProfile
  }

}
