var LoginUser = (function(){
  var cutomModal

  var loginModalElement
  var registerModalElement

  var loginForm
  var loginSubmitButton

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

    loginSubmitButton.click(function () {
      // get form values
      var username = loginForm.find('#username').val()
      var password = loginForm.find('#password').val()
      var email = loginForm.find('#email').val()
      CurrentUser.login(username, email, password)
    })

    registerSubmitButton.click(function () {
      // get form values
      var username = loginForm.find('#username').val()
      var password = loginForm.find('#password').val()
      var email = loginForm.find('#email').val()
      CurrentUser.register(username, email, password)
    })
  })

})()
