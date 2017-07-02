package com.ldz.project.route

import com.ldz.project.controller.{GreetingController, MusicController}
import org.apache.camel.builder.RouteBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
  * Created by Loic on 28/06/2017.
  */
@Component
class GreetingControllerRoute extends RouteBuilder{

  @Autowired
  private val greetingController: GreetingController = null

  @Autowired
  private val musicController: MusicController = null

  override def configure(): Unit = {
    from("direct:greeting/login")
        .bean(greetingController, "login")

    from("direct:greeting/logout")
      .bean(greetingController, "logout")

    from("direct:greeting/userfromcurrentipaddress")
      .bean(greetingController, "getCurrentUserFromIpaddress")

    from("direct:register/user")
      .bean(greetingController, "register")

    from("direct:register/add/user")
      .bean(greetingController, "addUser")

    from("direct:user/details/email")
      .bean(greetingController, "getUserdetailsFromUsername")

    //music route
    from("direct:user/music/musicplatform")
        .bean(musicController, "addMusicFromRessourceUrl")

    from("direct:user/music/all")
          .bean(musicController, "getAllMusicUrls")
  }
}