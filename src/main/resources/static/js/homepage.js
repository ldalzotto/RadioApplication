
var HostName = (function(){
    var host = undefined;
    var port = undefined;
    var protocol = undefined;

    $(document).ready(function(){
        protocol = window.location.protocol;
        host = window.location.hostname;
        port = window.location.port;
    })

    return {
        getSourceUrl: function(){
            return protocol + host + port;
        }
    }

})()

// header navigation

var NavBar = (function () {
  var hideAllNavBarContentBut = function (nothidedNavbar) {
    var navbarElements = $('#tabs').find('a').toArray()
    for (i = 0; i < navbarElements.length; i++) {
      if (nothidedNavbar == 'ALL') {
        var elementToHideId = navbarElements[i].id + '-content'
        $('#' + elementToHideId).hide()
      } else {
        if (navbarElements[i].id != nothidedNavbar) {
          var elementToHideId = navbarElements[i].id + '-content'
          $('#' + elementToHideId).hide()
        } else {
          var elementToHideId = navbarElements[i].id + '-content'
          $('#' + elementToHideId).show()
        }
      }
    }
  }

  $(document).ready(function () {
    $('#tabs').find('#dashboard-navbar').click(function () {
      if (CurrentUser.isIdentified()) {
        hideAllNavBarContentBut('dashboard-navbar')
      } else {
        hideAllNavBarContentBut('ALL')
      }
    })
    $('#tabs').find('#music-navbar').click(function () {
      if (CurrentUser.isIdentified()) {
        hideAllNavBarContentBut('music-navbar')
        MusicView.refreshMusicPanel()
      } else {
        hideAllNavBarContentBut('ALL')
      }
    })
    $('#tabs').find('#platforms-navbar').click(function () {
      if (CurrentUser.isIdentified()) {
        hideAllNavBarContentBut('platforms-navbar')
      } else {
        hideAllNavBarContentBut('ALL')
      }
    })

    $('#logout-header-link').click(function () {
      CurrentUser.logout()
    })

    $('#music-navbar-content').hide()

    $('#login-header-link').click(function (event) {
      CurrentUser.showModalOnEvent(event);
    })
  })
})()


// Music View
var MusicView = (function () {
  var refreshMusicListPanel = function () {
    $.ajax({
      method: 'GET',
      url: '/music/all',
      success: function (musicManagerType) {
        $('#music-panel-list').empty()
        var listMusicType = musicManagerType.musicTypeDTO
        for (i = 0; i < listMusicType.length; i++) {
          var musicPanelInfo = $('#music-panel-info').clone()
          musicPanelInfo.find('#music-iframe').attr('src', listMusicType[i].sourceUrl)
          $('#music-panel-list').append(musicPanelInfo)
        }
      }
    })
  }

  var addMusicFromPlatform = function (url, platform) {
    $.ajax({
      method: 'POST',
      url: '/music/musicplatform/' + platform,
      data: {
        url: url
      },
      success: function (iframeUrl) {
        $('#music-imported-modal').modal()
        $('#music-navbar').trigger('refreshMusic')
      }
    })
  }

  $(document).ready(function () {
    $('#music-navbar-content').find('#add-music-submit').click(function () {
      // get form values
      var url = $('#music-navbar-content').find('#siteURL').val()
      var platform = $('#music-navbar-content').find('#musicplatform').val()
      addMusicFromPlatform(url, platform)
    })
  })

  return {
    refreshMusicPanel: function () {
      refreshMusicListPanel()
    }
  }
})()

// END Music View
jQuery(function ($) {
  var panelList = $('#music-panel-list')
  panelList.sortable()
})
