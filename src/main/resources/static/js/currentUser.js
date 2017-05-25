var CurrentUser = (function () {
  var currentUserDetail

  var loginModal = undefined;
  var loginModalHeightWithoutErrorMessage = undefined;

  var loginForm = undefined;
  var loginSubmitButton = undefined;
  var registerSubmitButton = undefined;

  var errorBottomMessage = undefined;

  // check user status when document is ready
  $(document).ready(function () {
      // initiate login form
     loginModal = $('#login-modal');
     loginModalHeightWithoutErrorMessage = loginModal.height();
     loginForm = loginModal.find('#login-form');
     loginSubmitButton = loginForm.find('#login-submit');
     registerSubmitButton = loginForm.find('#register-submit');

     errorBottomMessage = loginModal.find('#login-error-box');

     loginModal.draggable().resizable();

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

  var errorBottomPopUp = function (message) {
     errorBottomMessage.text(message);

     if(!errorBottomMessage.is(":visible")){
       //adjust size of loginModal
       loginModalHeightWithoutErrorMessage = loginModal.height();
       var errorMessageHeight = errorBottomMessage.height();
       //loginModal.css("height", currentLoginModalheight + errorMessageHeight);
       loginModal.animate({
         height: loginModalHeightWithoutErrorMessage + errorMessageHeight
       }, 100, "swing", function(){
         errorBottomMessage.show("shake", "",400, function(){
           setTimeout(function(){
             errorBottomPopOut();
           }, 2000)
         });
       });
     } else {
       errorBottomMessage.effect("shake");
     }
  }

  var errorBottomPopOut = function () {
    errorBottomMessage.hide("fade", "", 400);

    //retrieve original loginModal height
    loginModal.animate({
      height: loginModalHeightWithoutErrorMessage
    }, 100, "swing");
  }

  var isLogOutDispaly = function () {
    $('#logout-header-link').hide()
    $('#login-header-link').show()
    loginModal.hide()
  }

  var isLogInDispaly = function () {
    $('#logout-header-link').show()
    $('#login-header-link').hide()
    loginModal.hide()
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
            errorBottomPopUp("An error occured on login.");
            //isLogOutDispaly()
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
            errorBottomPopUp(errorMessage);
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
      loginModal.show('puff', 300)
      errorBottomMessage.hide();
      loginModal.position({
        my: 'left+3 bottom-3',
        of: event
      })
    }
  }
})();
