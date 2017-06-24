package com.ldz.project.service.userregister

import java.util

import com.ldz.identifier.clients.IdentifierClient
import com.ldz.identifier.constants.UserRoles
import com.ldz.identifier.model.{UserDTO, UserDetailDTO, UserRoleDTO}
import com.ldz.project.constants._
import com.ldz.music.manager.MusicTypeClient
import com.ldz.music.manager.model.UserMusicStatusDTO
import com.ldz.project.exception.{AlreadyRegistered, LoginWithUnknownIPException, LoginWithUnkownUser}
import com.ldz.project.model.UserRegister
import com.ldz.token.manager.client.TokenManagerClient
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service

import collection.JavaConverters._

/**
  * Created by ldalzotto on 23/06/2017.
  */
@Service
class UserRegisterService extends IUserRegisterService {

  private val LOGGER = LoggerFactory.getLogger(classOf[UserRegisterService].getName)

  @Autowired private val identifierAPIClient: IdentifierClient = null

  @Autowired private val tokenManagerAPIClient: TokenManagerClient = null

  @Autowired private val musicManagerClient: MusicTypeClient = null


  override def registerUserFromUserDetails(userRegister: UserRegister): Boolean = {

    userRegister match {
      case UserRegister(username, password, email, ipaddress, country) =>
        //récupération des détails du client
        getDetailsFromusername(username) match {
          case userRegisters if userRegisters != null && userRegisters.isEmpty => userRegisters
          case _ => throw new AlreadyRegistered("The couple username : " + username + ", ipaddress : " + ipaddress +
            " is already registered", null);
        }

        try {
          registerIdentifier(username, password, ipaddress, email, country)
          registerMusic(username)
        } catch {
          case e: DataIntegrityViolationException => throw e
          case e: Throwable =>
            deleteIdentifierApi(username)
            deleteMusicManagerClient(username)
            LOGGER.info("End executing error handling.")
            throw e
        }
        true

      case _ => throw new RuntimeException("An error occured")
    }
  }

  override def getDetailsFromusername(username: String): List[UserRegister] = {

    Option(identifierAPIClient.getPersonFromUsername(username).getBody()) match {
      case Some(userDTO) =>
        val password = userDTO.getPassword

        Option(userDTO.getUserDetailDTOS.asScala) match {
          case Some(userdetailDTO) => userdetailDTO.map(ud =>
            UserRegister(username, password, null, ud.getIpaddress, ud.getCountry)
          ).toList
          case None => List[UserRegister]()
        }

      case None => List[UserRegister]()
    }
  }

  override def getDetailsFromEmail(email: String): List[UserRegister] = {

    Option(identifierAPIClient.getPersonFromEmail(email).getBody) match {
      case Some(userDTO) =>
        Option(userDTO.getUserDetailDTOS) match {
          case Some(userDetailDToes) => userDetailDToes.asScala.map(ud =>
            UserRegister(userDTO.getUserName, userDTO.getPassword,
              null, ud.getIpaddress, ud.getCountry)
          ).toList
          case None => List[UserRegister]()
        }
      case None => List[UserRegister]()
    }

  }

  override def getCurrentUserFromIpaddress(ipaddress: String): UserRegister = {

    val genericToken = tokenManagerAPIClient.getTokenFromIpaddress(ipaddress).getBody.asScala

    val genericTokenData =
      Option(genericToken.head) match {
        case None => throw new RuntimeException("Cannot fin token !")
        case Some(g) => g._2.asScala
      }

    UserRegister(genericTokenData.getOrElse(TokenManagerMapKeys.USERNAME.toString, null),
      genericTokenData.getOrElse(TokenManagerMapKeys.PASSWORD.toString, null)
      , null
      , genericTokenData.getOrElse(TokenManagerMapKeys.IPADDRESS.toString, null),
      genericTokenData.getOrElse(TokenManagerMapKeys.COUNTRY.toString, null))

  }

  override def addUserFromexisting(userRegister: UserRegister): UserRegister = {

    val userDetailDto = new UserDetailDTO
    userDetailDto.setIpaddress(userRegister.getIpaddress)
    userDetailDto.setCountry(userRegister.getCountry)

    identifierAPIClient.addUserIpaddressFromUsernaem(userRegister.getUsername, userDetailDto)

    //create token
    val genericData = Map((TokenManagerMapKeys.USERNAME.toString, userRegister.getUsername),
      (TokenManagerMapKeys.PASSWORD.toString, userRegister.getPassword),
      (TokenManagerMapKeys.IPADDRESS.toString, userRegister.getIpaddress),
      (TokenManagerMapKeys.COUNTRY.toString, userRegister.getCountry))

    tokenManagerAPIClient.postTokenWithIpaddress(userRegister.getIpaddress, genericData.asJava)

    userRegister
  }

  override def logoutUserFromIpaddress(ipaddress: String): Unit = {
    tokenManagerAPIClient.deleteTokenFromIpaddress(ipaddress)
  }

  override def loginUserFromEmailAndPasswordAndIpaddress(email: String, password: String, ipaddress: String): UserRegister = {

    val identifierAPIUser = Option(identifierAPIClient.getPersonFromEmailAndPassword(email, password).getBody)

    val sIdentifierApi =
      identifierAPIUser match {
        case None => throw new LoginWithUnkownUser("User not recognized !", null);
        case Some(sIden) => sIden
      }

    val userDetails = Option(sIdentifierApi.getUserDetailDTOS)

    val filteredUserDetail =
      userDetails match {
        case None => throw new LoginWithUnknownIPException("User IP not recognized !", null)
        case Some(ud) => Option(ud.asScala.filter(ud => ud.getIpaddress.equals(ipaddress)).head)
      }

    val filteredUserDetailFound =
      filteredUserDetail match {
        case None => throw new LoginWithUnknownIPException("User IP not recognized !", null)
        case Some(fud) => fud
      }

    val genericInfoToPut = Map((TokenManagerMapKeys.USERNAME.toString, sIdentifierApi.getUserName),
      (TokenManagerMapKeys.PASSWORD.toString, password),
      (TokenManagerMapKeys.IPADDRESS.toString, filteredUserDetailFound.getIpaddress),
      (TokenManagerMapKeys.COUNTRY.toString, filteredUserDetailFound.getCountry))

    tokenManagerAPIClient.postTokenWithIpaddress(filteredUserDetailFound.getIpaddress, genericInfoToPut.asJava)

    UserRegister(sIdentifierApi.getUserName, sIdentifierApi.getPassword, sIdentifierApi.getEmail,
      ipaddress, filteredUserDetailFound.getCountry)
  }

  private def registerIdentifier(username: String, password: String, ipaddress: String, email: String, country: String): Boolean = {

    LOGGER.info("Start executing identifier registering.")

    val identifierAPIUser = new UserDTO()
    identifierAPIUser.setPassword(password)
    identifierAPIUser.setUserName(username)
    identifierAPIUser.setEmail(email)

    val identifierAPIUserRole = new UserRoleDTO()
    identifierAPIUserRole.setRole(UserRoles.ROLE_USER.getRole)

    identifierAPIUser.setUserRoleDTO(identifierAPIUserRole)

    //set userdetail
    val identifierAPIUserDetail = new UserDetailDTO()
    identifierAPIUserDetail.setCountry(country)
    identifierAPIUserDetail.setIpaddress(ipaddress)

    identifierAPIUser.setUserDetailDTOS(List(identifierAPIUserDetail).asJava)
    identifierAPIClient.postUser(identifierAPIUser)

    LOGGER.info("End executing identifier registering.")
    true
  }

  private def registerMusic(username: String): Boolean = {
    LOGGER.info("Start executing music registering.")
    musicManagerClient.postUser(UserMusicStatusDTO(username, null))
    LOGGER.info("End executing music registering.")
    true
  }


  private def deleteIdentifierApi(username: String): Unit = {
    try {
      identifierAPIClient.deleteUser(username)
    } catch {
      case e: Throwable => LOGGER.error(e.getMessage, e)
    }

  }

  private def deleteMusicManagerClient(username: String): Unit = {
    try {
      musicManagerClient.deleteUser(username);
    } catch {
      case e: Throwable => LOGGER.error(e.getMessage(), e)

    }
  }


}

