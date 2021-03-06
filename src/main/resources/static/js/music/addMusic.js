var AddMusicModal = (function(){

  var genericPopUpModal;

  var addModalElement;

  var submitButton;

  var fromUsername = function(actionToDo){
    var currentUser = CurrentUser.retrieveCurrentUser()
    actionToDo(currentUser.username)
  }

  var addMusicFromPlatform = function (url, platform) {
    fromUsername(function(username){
      $.ajax({
        method: 'POST',
        url: 'user/' + username + '/music/musicplatform/' + platform,
        data: {
          url: url
        },
        success: function (iframeUrl) {
          EventRoller.pushEvent('Music successfully imported !')
          MusicView.displayMusicPanel()
          TreeMusic.refreshTree()
          genericPopUpModal.hideModal()
        }
      })
    })
  }

  $(document).ready(function(){
    addModalElement = $('#music-navbar-content').find('#add-music-modal')
    genericPopUpModal = new CustomModal(addModalElement, {
      closable: true,
      resizable: false,
      draggable: false
    })
    submitButton = addModalElement.find('#add-music-submit')
    //click event
    submitButton.click(function () {
      // get form values
      var url = $('#music-navbar-content').find('#siteURL').val()
      var platform = $('#music-navbar-content').find('#musicplatform').val()
      addMusicFromPlatform(url, platform)
    })
  })

  return {
    showModal: function(){
      genericPopUpModal.showModal()
    },
    hideModal: function(){
      genericPopUpModal.hideModal()
    }
  }
})()
