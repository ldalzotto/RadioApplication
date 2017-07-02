var CurrentUser = (function () {


  var loginModalElement
  var loginCutomModal

  var registerModalElement
  var registerCutomModal

  var currentUserDetail

  var loginForm
  var loginSubmitButton

  var registerForm
  var registerSubmitButton

  // check user status when document is ready
  $(document).ready(function () {
    // initiate login form
    loginModalElement = $('#login-modal')
    loginCutomModal = new CustomModal(loginModalElement, {
      closable: true,
      resizable: false,
      draggable: false
    })

    loginForm = loginModalElement.find('#login-form')
    loginSubmitButton = loginForm.find('#login-submit')

    //register
    registerModalElement = $('#register-modal')
    registerCutomModal = new CustomModal(registerModalElement, {
      closable: true,
      resizable: false,
      draggable: false
    })

    registerForm = registerModalElement.find('#register-form')
    registerSubmitButton = registerForm.find('#register-submit')

      // get current user
    CurrentUser.retrieveCurrentUser()

    loginSubmitButton.click(function () {
      // get form values
      var password = loginForm.find('#password').val()
      var email = loginForm.find('#email').val()
      CurrentUser.login(email, password)
    })

    registerSubmitButton.click(function () {
      // get form values
      var username = registerForm.find('#username').val()
      var password = registerForm.find('#password').val()
      var email = registerForm.find('#email').val()
      CurrentUser.register(username, email, password)
    })

  })

  var isLogOutDispaly = function () {
    $('#logout-header-link').hide()
    $('#login-header-link').show()
    $('#register-header-link').show()
    loginCutomModal.hideModal()
    registerCutomModal.hideModal()
  }

  var isLogInDispaly = function () {
    $('#logout-header-link').show()
    $('#login-header-link').hide()
    $('#register-header-link').hide()
    loginCutomModal.hideModal()
    registerCutomModal.hideModal()
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
    login: function (email, password) {
      fromCurrentIp(function (userInfo) {
        $.ajax({
          method: 'POST',
          url: '/login',
          data: {
            password: password,
            email: email,
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
              loginCutomModal.errorPopUp('You are trying to login with an unknown IP.')
              //show modal
              CurrentUser.getIpList(email, AddIpModal.showModalWithListIp);
            } else if (jqXHR.responseText == 'LOGIN_UNKNOWN_USER') {
              loginCutomModal.errorPopUp('Unknown user.')
            } else {
              loginCutomModal.errorPopUp('An error occured on login.')
            }
          }
        })
      })
    },
    register: function (username, email, password) {
      fromCurrentIp(function (userInfo) {
        $.ajax({
          method: 'POST',
          url: '/register/user',
          data: {
            username: username,
            password: password,
            email: email,
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
            registerCutomModal.errorPopUp(errorMessage)
          }
        })
      })
    },
    addUser: function (username, email, password) {
      fromCurrentIp(function(userInfo){
        $.ajax({
          method: 'POST',
          url: '/register/add/user',
          data: {
            username: username,
            password: password,
            email: email,
            ipaddress: userInfo.ip,
            country: userInfo.country
          },
          success: function(){
            AddIpModal.resetAndHide();
            loginCutomModal.hideModal();
            //do login with the newly added IP
            CurrentUser.login(email, password);
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
            email: currentUserDetail.email,
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
    showLoginModalOnEvent: function (event) {
      loginCutomModal.showModal(event)
    },
    showRegisterModalOnEvent: function (event) {
      registerCutomModal.showModal(event)
    },
    getIpList: function(email, actionOnList){
        var returlListIp;
        $.ajax({
          method: 'GET',
          url: ('/user/details/email/' + email + '/'),
          success: function (listuserDetail) {
            //récupération de la liste des ip
            var listIp = []
            var username
            var password
            for (var i = 0; i < listuserDetail.length; i++) {
                listIp.push(listuserDetail[i].ipaddress);
                if(username == undefined){
                  username = listuserDetail[i].username;
                }
                if(password == undefined){
                  password = listuserDetail[i].password;
                }
            }
            returlListIp = listIp;

            if(actionOnList != undefined){
              fromCurrentIp(function(userInfo){
                actionOnList(returlListIp, userInfo.ip, function(){
                  CurrentUser.addUser(username, email, password)
                });
              })
            }
          }
        })

        return returlListIp;
      },
      getUserName: function() {
        return CurrentUser.retrieveCurrentUser().username
      }
  }
})()
