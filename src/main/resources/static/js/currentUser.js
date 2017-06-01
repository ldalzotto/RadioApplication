var CurrentUser = (function () {
  var cutomModal

  var loginModalElement
  var currentUserDetail

  var loginForm
  var loginSubmitButton
  var registerSubmitButton

  // check user status when document is ready
  $(document).ready(function () {
    // initiate login form
    loginModalElement = $('#login-modal')
    cutomModal = new CustomModal(loginModalElement, {
      closable: true,
      resizable: false,
      draggable: false
    })

    loginForm = loginModalElement.find('#login-form')
    loginSubmitButton = loginForm.find('#login-submit')
    registerSubmitButton = loginForm.find('#register-submit')

      // get current user
    CurrentUser.retrieveCurrentUser()

    loginSubmitButton.click(function () {
      // get form values
      var username = loginForm.find('#username').val()
      var password = loginForm.find('#password').val()
      CurrentUser.login(username, password)
    })

    registerSubmitButton.click(function () {
      // get form values
      var username = loginForm.find('#username').val()
      var password = loginForm.find('#password').val()
      CurrentUser.register(username, password)
    })
  })

  var isLogOutDispaly = function () {
    $('#logout-header-link').hide()
    $('#login-header-link').show()
    cutomModal.hideModal()
  }

  var isLogInDispaly = function () {
    $('#logout-header-link').show()
    $('#login-header-link').hide()
    cutomModal.hideModal()
  }

  var fromCurrentIp = function (successCallback, errorCallback) {
    $.ajax({
      method: 'GET',
      url: 'https://ipinfo.io',
      dataType: 'json',
      success: successCallback,
      error: errorCallback
    })
  }

  return {
    retrieveCurrentUser: function () {
      if(currentUserDetail == undefined){
        fromCurrentIp(function (userInfo) {
          $.ajax({
            method: 'GET',
            url: '/user/current/ipaddress/' + userInfo.ip + '/',
            success: function (userdetails) {
              currentUserDetail = userdetails
              if (userdetails != null) {
                if (userdetails.userName != null && userdetails.password != null) {
                  isLogOutDispaly()
                } else {
                  isLogInDispaly()
                }
              } else {
                isLogOutDispaly()
              }
            },
            error: function () {
              isLogOutDispaly()
            }
          })
        })
      }
      return currentUserDetail;
    },
    login: function (username, password) {
      fromCurrentIp(function (userInfo) {
        $.ajax({
          method: 'POST',
          url: '/login',
          data: {
            username: username,
            password: password,
            ipaddress: userInfo.ip,
            country: userInfo.country
          },
          success: function (userdetails) {
            currentUserDetail = userdetails
            isLogInDispaly()
            EventRoller.pushEvent("Login success !")
          },
          error: function (jqXHR, error, errorThrown) {
            console.error(jqXHR.responseText)
            if (jqXHR.responseText == 'LOGIN_UNKNOWN_IP') {
              // display new popup for adding
              cutomModal.errorPopUp('You are trying to login with an unknown IP.')
              //show modal
              CurrentUser.getIpList(username, AddIpModal.showModalWithListIp, function(){
                CurrentUser.addUser(username, password);
              });
            } else if (jqXHR.responseText == 'LOGIN_UNKNOWN_USER') {
              cutomModal.errorPopUp('Unknown user.')
            } else {
              cutomModal.errorPopUp('An error occured on login.')
            }
          }
        })
      })
    },
    register: function (username, password) {
      fromCurrentIp(function (userInfo) {
        $.ajax({
          method: 'POST',
          url: '/register/user',
          data: {
            username: username,
            password: password,
            ipaddress: userInfo.ip,
            country: userInfo.country
          },
          success: function (userdetails) {
            currentUserDetail = userdetails
            isLogInDispaly()
            EventRoller.pushEvent("Register success !")
          },
          error: function (jqXHR, error, errorThrown) {
            var errorMessage = 'An error occured on register.'
            console.error(jqXHR.responseText)
            if (jqXHR.responseText == 'ALREADY_REGISTERED') {
              errorMessage = 'The user ' + username + ' is already registered.'
            }
            cutomModal.errorPopUp(errorMessage)
            // isLogOutDispaly()
          }
        })
      })
    },
    addUser: function (username, password) {
      fromCurrentIp(function(userInfo){
        $.ajax({
          method: 'POST',
          url: '/register/add/user',
          data: {
            username: username,
            password: password,
            ipaddress: userInfo.ip,
            country: userInfo.country
          },
          success: function(){
            AddIpModal.resetAndHide();
            cutomModal.hideModal();
            //do login with the newly added IP
            CurrentUser.login(username, password);
            EventRoller.pushEvent("User successfully added !")
          },
          error: function(){
            //TODO gestion erreur
            console.log("add KO");
          }
        })
      })
    },
    logout: function () {
      fromCurrentIp(function (userInfo) {
        $.ajax({
          method: 'POST',
          url: '/logout',
          data: {
            username: currentUserDetail.username,
            password: currentUserDetail.password,
            ipaddress: userInfo.ip,
            country: userInfo.country
          },
          success: function () {
            currentUserDetail = undefined
            isLogOutDispaly()
            EventRoller.pushEvent("Logout success !")
          },
          error: function () {
            isLogInDispaly()
          }
        })
      })
    },
    isIdentified: function () {
      if (currentUserDetail == undefined) {
        return false
      } else {
        return true
      }
    },
    showModalOnEvent: function (event) {
      cutomModal.showModal(event)
    },
    getIpList: function(username, actionOnList, addingUser){
        var returlListIp;
        $.ajax({
          method: 'GET',
          url: ('/user/details/username/' + username ),
          success: function (listuserDetail) {
            //récupération de la liste des ip
            var listIp = []
            for (var i = 0; i < listuserDetail.length; i++) {
                listIp.push(listuserDetail[i].ipaddress);
            }
            returlListIp = listIp;

            if(actionOnList != undefined){
              fromCurrentIp(function(userInfo){
                actionOnList(returlListIp, userInfo.ip, addingUser);
              })
            }
          }
        })

        return returlListIp;
      }
  }
})()
