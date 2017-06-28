package com.ldz.project.controller

import java.util.concurrent.Executors
import javax.validation.Valid

import com.ldz.project.exception.{AlreadyRegistered, LoginWithUnknownIPException, LoginWithUnkownUser}
import com.ldz.project.model.{UserLogin, UserRegister}
import com.ldz.project.service.userregister.IUserRegisterService
import org.apache.camel.{CamelExecutionException, ProducerTemplate}
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpStatus, MediaType, ResponseEntity}
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{ExceptionHandler, PathVariable, RequestMapping, RequestMethod}

import scala.concurrent.ExecutionContext

/**
  * Created by Loic on 28/06/2017.
  */
@Controller
class GreetingControllerCamel {

  private val LOGGER = LoggerFactory.getLogger(classOf[GreetingController].getSimpleName)

  @Autowired
  private val producerTemplate: ProducerTemplate = null

  @RequestMapping(value = Array("/"), method = Array(RequestMethod.GET)) def homepage = "homepage"

  @RequestMapping(value = Array("/login"), method = Array(RequestMethod.POST), consumes = Array(MediaType.APPLICATION_FORM_URLENCODED_VALUE), produces = Array(MediaType.APPLICATION_JSON_VALUE))
  def login(@Valid userLogin: UserLogin): ResponseEntity[_] = {
    producerTemplate.requestBody("direct:greeting/login", userLogin).asInstanceOf[ResponseEntity[_]]
  }

  @RequestMapping(value = Array("/logout"), method = Array(RequestMethod.POST), consumes = Array(MediaType.APPLICATION_FORM_URLENCODED_VALUE), produces = Array(MediaType.APPLICATION_JSON_VALUE))
  def logout(@Valid userRegister: UserRegister): ResponseEntity[_] = {
    producerTemplate.requestBody("direct:greeting/logout", userRegister).asInstanceOf[ResponseEntity[_]]
  }

  @RequestMapping(value = Array("/user/current/ipaddress/{ipaddress}"), method = Array(RequestMethod.GET))
  def getCurrentUserFromIpaddress(@PathVariable(name = "ipaddress") ipaddress: String): ResponseEntity[_] = {
      producerTemplate.requestBody("direct:greeting/userfromcurrentipaddress", ipaddress).asInstanceOf[ResponseEntity[_]]
  }

  @RequestMapping(value = Array("/register/user"), method = Array(RequestMethod.POST), consumes = Array(MediaType.APPLICATION_FORM_URLENCODED_VALUE), produces = Array(MediaType.APPLICATION_JSON_VALUE))
  def register(@Valid userRegister: UserRegister): ResponseEntity[_] = {

    producerTemplate.requestBody("direct:register/user", userRegister).asInstanceOf[ResponseEntity[_]]

  }

  @RequestMapping(value = Array("/register/add/user"), method = Array(RequestMethod.POST), consumes = Array(MediaType.APPLICATION_FORM_URLENCODED_VALUE), produces = Array(MediaType.APPLICATION_JSON_VALUE))
  def addUser(@Valid userRegister: UserRegister): ResponseEntity[_] = {
    producerTemplate.requestBody("direct:register/add/user").asInstanceOf[ResponseEntity[_]]
  }

  @RequestMapping(value = Array("/user/details/email/{email}"), method = Array(RequestMethod.GET))
  def getUserdetailsFromUsername(@PathVariable("email") email: String): ResponseEntity[_] = {
    producerTemplate.requestBody("direct:user/details/email").asInstanceOf[ResponseEntity[_]]
  }

  @ExceptionHandler(Array{classOf[CamelExecutionException]})
  def camelExecutionError(camelExeption: CamelExecutionException) = {
    camelExeption.getCause match {
      case e: LoginWithUnknownIPException =>
        val errorCode = "LOGIN_UNKNOWN_IP"
        ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorCode)
      case e: AlreadyRegistered =>
        val errorCode = "ALREADY_REGISTERED"
        ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorCode)
      case e: LoginWithUnkownUser =>
        val errorCode = "LOGIN_UNKNOWN_USER"
        ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorCode)
      case e: Exception =>
        val errorCode = "AN_ERROR_OCCURED"
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorCode)
    }
  }

}
