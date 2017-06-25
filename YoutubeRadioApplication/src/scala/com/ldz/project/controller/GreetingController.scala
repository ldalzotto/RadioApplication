package com.ldz.project.controller

import java.net.URI
import java.util
import java.util.List
import javax.validation.Valid

import com.ldz.project.exception.{AlreadyRegistered, LoginWithUnknownIPException, LoginWithUnkownUser}
import com.ldz.project.model.{UserLogin, UserRegister}
import com.ldz.project.service.userregister.IUserRegisterService
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpStatus, MediaType, ResponseEntity}
import org.springframework.stereotype.Controller
import org.springframework.validation.Validator
import org.springframework.web.bind.annotation.{ExceptionHandler, PathVariable, RequestMapping, RequestMethod}
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

/**
  * Created by ldalzotto on 24/06/2017.
  */
@Controller
class GreetingController {

  private val LOGGER = LoggerFactory.getLogger(classOf[GreetingController].getSimpleName)

  @Autowired
  private val iUserRegisterService: IUserRegisterService = null

  @RequestMapping(value = Array("/"), method = Array(RequestMethod.GET)) def homepage = "homepage"

  @RequestMapping(value = Array("/login"), method = Array(RequestMethod.POST), consumes = Array(MediaType.APPLICATION_FORM_URLENCODED_VALUE), produces = Array(MediaType.APPLICATION_JSON_VALUE))
  def login(@Valid userLogin: UserLogin): ResponseEntity[_] = {
    Option(iUserRegisterService.loginUserFromEmailAndPasswordAndIpaddress(userLogin.getEmail, userLogin.getPassword, userLogin.getIpaddress))
        match {
      case Some(userRegister) => ResponseEntity.ok(userRegister)
      case None => ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
    }
  }

  @RequestMapping(value = Array("/logout"), method = Array(RequestMethod.POST), consumes = Array(MediaType.APPLICATION_FORM_URLENCODED_VALUE), produces = Array(MediaType.APPLICATION_JSON_VALUE))
  def logout(@Valid userRegister: UserRegister): ResponseEntity[_] = {
    iUserRegisterService.logoutUserFromIpaddress(userRegister.getIpaddress)
    ResponseEntity.noContent.build()
  }

  @RequestMapping(value = Array("/user/current/ipaddress/{ipaddress}"), method = Array(RequestMethod.GET))
  def getCurrentUserFromIpaddress(@PathVariable(name = "ipaddress") ipaddress: String): ResponseEntity[_] = {
    Option(iUserRegisterService.getCurrentUserFromIpaddress(ipaddress)) match {
      case Some(userRegister) => ResponseEntity.ok(userRegister)
      case None => ResponseEntity.status(HttpStatus.FORBIDDEN).build()
    }
  }

  @RequestMapping(value = Array("/register/user"), method = Array(RequestMethod.POST), consumes = Array(MediaType.APPLICATION_FORM_URLENCODED_VALUE), produces = Array(MediaType.APPLICATION_JSON_VALUE))
  def register(@Valid userRegister: UserRegister): ResponseEntity[_] = {
    iUserRegisterService.registerUserFromUserDetails(userRegister)
    //login after register
    Option(iUserRegisterService.loginUserFromEmailAndPasswordAndIpaddress(userRegister.getEmail, userRegister.getPassword, userRegister.getIpaddress))
        match {
      case Some(`userRegister`) =>
        val location = ServletUriComponentsBuilder.fromCurrentRequestUri.build.toUri
        ResponseEntity.created(location).body(`userRegister`)
      case None => ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
    }
  }

  @RequestMapping(value = Array("/register/add/user"), method = Array(RequestMethod.POST), consumes = Array(MediaType.APPLICATION_FORM_URLENCODED_VALUE), produces = Array(MediaType.APPLICATION_JSON_VALUE))
  def addUser(@Valid userRegister: UserRegister): ResponseEntity[_] = {
    Option(iUserRegisterService.addUserFromexisting(userRegister)) match {
      case Some(`userRegister`) => ResponseEntity.ok(`userRegister`)
      case None => ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build()
    }
  }

  @RequestMapping(value = Array("/user/details/email/{email}"), method = Array(RequestMethod.GET))
  def getUserdetailsFromUsername(@PathVariable("email") email: String): ResponseEntity[_] = {
    Option(iUserRegisterService.getDetailsFromEmail(email)) match {
      case Some(userRegisters) => ResponseEntity.ok(userRegisters)
      case None => ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build()
    }
  }

  @ExceptionHandler(Array(classOf[LoginWithUnknownIPException]))
  def handleLoginWithUnknownIp(e: LoginWithUnknownIPException): ResponseEntity[_] = {
    e.printStackTrace()
    val errorCode = "LOGIN_UNKNOWN_IP"
    ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorCode)
  }

  @ExceptionHandler(Array(classOf[AlreadyRegistered]))
  def handleAlreadyRegistered(alreadyRegistered: AlreadyRegistered): ResponseEntity[_] = {
    alreadyRegistered.printStackTrace()
    val errorCode = "ALREADY_REGISTERED"
    ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorCode)
  }

  @ExceptionHandler(Array(classOf[LoginWithUnkownUser]))
  def handleLoginUnknown(loginWithUnkownUser: LoginWithUnkownUser): ResponseEntity[_] = {
    loginWithUnkownUser.printStackTrace()
    val errorCode = "LOGIN_UNKNOWN_USER"
    ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorCode)
  }

  @ExceptionHandler(Array(classOf[Exception]))
  def handleOtherExeption(e: Exception): ResponseEntity[_] = {
    e.printStackTrace()
    val errorCode = "AN_ERROR_OCCURED"
    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorCode)
  }


}