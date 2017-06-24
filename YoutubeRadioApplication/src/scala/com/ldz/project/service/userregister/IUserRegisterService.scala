package com.ldz.project.service.userregister


import com.ldz.project.model.UserRegister

/**
  * Created by ldalzotto on 23/06/2017.
  */
trait IUserRegisterService {
  def registerUserFromUserDetails(userRegister: UserRegister): Boolean

  def getDetailsFromusername(username: String): List[UserRegister]

  def getDetailsFromEmail(email: String): List[UserRegister]

  def getCurrentUserFromIpaddress(ipaddress: String): UserRegister

  def addUserFromexisting(userRegister: UserRegister): UserRegister

  def logoutUserFromIpaddress(ipaddress: String): Unit

  def loginUserFromEmailAndPasswordAndIpaddress(email: String, password: String, ipaddress: String): UserRegister
}
