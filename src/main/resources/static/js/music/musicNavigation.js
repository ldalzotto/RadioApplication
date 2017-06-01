// Music View
var MusicView = (function () {

  var musicPanelListElement

  var musicPanelInfoTemplateElement;

  $(document).ready(function(){
    musicPanelListElement = $('#music-navbar-content').find('#music-panel-list');
    musicPanelInfoTemplateElement = $('#music-panel-info');
    musicPanelInfoTemplateElement.hide();
  })

  var fromUsername = function(actionToDo){
    var currentUser = CurrentUser.retrieveCurrentUser()
    actionToDo(currentUser.username)
  }

  var refreshMusicListPanel = function () {
    fromUsername(function(username){
      $.ajax({
        method: 'GET',
        url: 'user/' + username + '/music/all',
        success: function (musicManagerType) {
          var numberOfMusic = musicManagerType.musicTypeDTO.length

          if(numberOfMusic != 0) {
            musicPanelListElement.empty()
            var listMusicType = musicManagerType.musicTypeDTO
            for (i = 0; i < listMusicType.length; i++) {
              var musicPanelInfo = musicPanelInfoTemplateElement.clone()
              musicPanelInfo.find('#music-iframe').attr('src', listMusicType[i].sourceUrl)
              musicPanelListElement.append(musicPanelInfo)
            }
            musicPanelListElement.show()
          } else {
            AddMusicModal.showModal();
            //you have no music !
          }

        }
      })
    })
  }

  return {
    displayMusicPanel: function () {
      musicPanelListElement.hide()
      AddMusicModal.hideModal()
      refreshMusicListPanel()
    }
  }
})()
