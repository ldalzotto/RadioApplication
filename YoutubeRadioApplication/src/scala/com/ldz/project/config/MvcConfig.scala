package com.ldz.project.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.{ViewControllerRegistry, WebMvcConfigurerAdapter}

/**
  * Created by Loic on 24/06/2017.
  */
@Configuration
class MvcConfig extends WebMvcConfigurerAdapter{

  override def addViewControllers(registry: ViewControllerRegistry): Unit = {
    registry.addViewController("/").setViewName("homepage")
  }

}
