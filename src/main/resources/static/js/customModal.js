
var CustomModal = function (modalElementToCustom, config) {
  var modalElement = modalElementToCustom
  var errorMessageElement = $('<div></div>').addClass('ui-widget-bottom-error').addClass('ui-state-error')

  errorMessageElement.appendTo(modalElement)
  errorMessageElement.hide()

  var setDefaultConfigValue = function(currentConfig){
    var returnConfig;
    if (currentConfig == undefined) {
      returnConfig = {
        closable: true,
        resizable: true
      }
    } else {
      if(currentConfig.closable == undefined){
        returnConfig.closable = true;
      }
      if(currentConfig.resizable == undefined){
        returnConfig.resizable = true;
      }
    }

    if(returnConfig == undefined){
      returnConfig = currentConfig;
    }

    return returnConfig;
  }

  // default values
  config = setDefaultConfigValue(config);

  if (config.closable == true) {
    var closeButton = $('<span></span>').addClass('ui-widget-close-button').addClass('ui-icon').addClass('ui-icon-closethick')
    closeButton.appendTo(modalElement)

    closeButton.click(function () {
      modalElement.hide()
    })
    closeButton.hover(function () {
      closeButton.animate({
        opacity: 1
      })
    }, function () {
      closeButton.animate({
        opacity: 0.6
      })
    })
  }

  if (config.resizable == true) {
    modalElement.resizable()
  }

  var modalHeightWithoutErrorMessage = modalElement.height()

  modalElement.draggable()

  var errorBottomPopUp = function (message) {
    errorMessageElement.text(message)

    if (!errorMessageElement.is(':visible')) {
       // adjust size of loginModal
      modalHeightWithoutErrorMessage = modalElement.height()
      var errorMessageHeight = errorMessageElement.height()
       // loginModal.css("height", currentLoginModalheight + errorMessageHeight);
      modalElement.animate({
        height: modalHeightWithoutErrorMessage + errorMessageHeight
      }, 100, 'swing', function () {
        errorMessageElement.show('shake', '', 400, function () {
          setTimeout(function () {
            errorBottomPopOut()
          }, 2000)
        })
      })
    } else {
      errorMessageElement.effect('shake')
    }
  }

  var errorBottomPopOut = function () {
    errorMessageElement.hide('fade', '', 400)

    // retrieve original loginModal height
    modalElement.animate({
      height: modalHeightWithoutErrorMessage
    }, 100, 'swing')
  }

  var displayModal = function (event) {
    modalElement.show('puff', 300)
    errorMessageElement.hide()
    modalElement.position({
      my: 'left+3 bottom-3',
      of: event
    })
  }

return{
  errorPopUp : function (message) {
      errorBottomPopUp(message)
  },
  showModal : function (event) {
    displayModal(event)
  },
  hideModal : function () {
    modalElement.hide()
  }
}
}
