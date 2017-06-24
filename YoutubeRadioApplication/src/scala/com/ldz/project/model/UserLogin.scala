package com.ldz.project.model

import javax.validation.constraints.{NotNull, Size}

import org.hibernate.validator.constraints.Email

import scala.beans.BeanProperty

/**
  * Created by ldalzotto on 23/06/2017.
  */
case class UserLogin(@BeanProperty @NotNull @Size(min = 4) var password: String,
                     @BeanProperty @NotNull @Email var email: String,
                     @BeanProperty @NotNull var ipaddress: String) {

  def this() {
    this(null, null, null)
  }

}
