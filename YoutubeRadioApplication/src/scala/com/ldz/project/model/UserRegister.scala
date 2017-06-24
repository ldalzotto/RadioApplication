package com.ldz.project.model

import javax.validation.constraints.{NotNull, Size}

import org.hibernate.validator.constraints.Email

import scala.annotation.meta.field
import scala.beans.BeanProperty

/**
  * Created by ldalzotto on 23/06/2017.
  */
case class UserRegister(@BeanProperty @(NotNull@field) @(Size@field)(min = 4) var username: String,
                        @BeanProperty @(NotNull@field) @(Size@field)(min = 4) var password: String,
                        @BeanProperty @(NotNull@field) @(Email@field) var email: String,
                        @BeanProperty @(NotNull@field) var ipaddress: String,
                        @BeanProperty @(NotNull@field) var country: String) {


  def this() {
    this(null, null, null, null, null)
  }

}
