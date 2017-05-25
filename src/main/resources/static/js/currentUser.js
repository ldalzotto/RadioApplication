var CurrentUser = (function () {

  var cutomModal = undefined;


  var loginModalElement = undefined;
  var currentUserDetail

  var loginForm = undefined;
  var loginSubmitButton = undefined;
  var registerSubmitButton = undefined;

  // check user status when document is ready
  $(document).ready(function () {
    // initiate login form
     loginModalElement = $('#login-modal');
     cutomModal = new CustomModal(loginModalElement);

     loginForm = loginModalElement.find('#login-form');
     loginSubmitButton = loginForm.find('#login-submit');
     registerSubmitButton = loginForm.find('#register-submit');

      // get current user
    CurrentUser.retrieveCurrentUser();

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
      fromCurrentIp(function (userInfo) {
        $.ajax({
          method: 'GET',
          url: '/user/current/ipaddress/' +  userInfo.ip + '/',
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
          },
          error: function (jqXHR, error, errorThrown) {
            console.error(jqXHR.responseText);
            if(jqXHR.responseText == "LOGIN_UNKNOWN_IP"){
              //display new popup for adding
              cutomModal.errorPopUp("You are trying to login with an unknown IP.");
            } else {
              cutomModal.errorPopUp("An error occured on login.");
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
          },
          error: function (jqXHR, error, errorThrown) {
            var errorMessage = "An error occured on register.";
            console.error(jqXHR.responseText)
            if(jqXHR.responseText == "IPADDRESS_ALREADY_EXIST"){
              errorMessage = "Your ip address is already registered.";
            } else if(jqXHR.responseText == "USERNAME_ALREADY_EXIST"){
              errorMessage = "The username already exists";
            }
            cutomModal.errorPopUp(errorMessage);
            //isLogOutDispaly()
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
    showModalOnEvent: function(event) {
      cutomModal.showModal(event);
    }
  }
})();
