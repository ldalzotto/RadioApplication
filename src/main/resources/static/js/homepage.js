
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

// header animation
$(document).ready(function () {
  $('.alert').addClass('in').fadeOut(4500)
    /* swap open/close side menu icons */
  $('[data-toggle=collapse]').click(function () {
        // toggle icon
    $(this).find('i').toggleClass('glyphicon-chevron-right glyphicon-chevron-down')
  })
})

// header navigation
$(document).ready(function () {
  function hideAllNavBarContentBut (nothidedNavbar) {
    var navbarElements = $('#tabs').find('.ui-button').toArray()
    for (i = 0; i < navbarElements.length; i++) {
      if (navbarElements[i].id != nothidedNavbar) {
        var elementToHideId = navbarElements[i].id + '-content'
        $('#' + elementToHideId).hide()
      } else {
        var elementToHideId = navbarElements[i].id + '-content'
        $('#' + elementToHideId).show()
      }
    }
  }

  $('#tabs').find('#dashboard-navbar').click(function () {
    hideAllNavBarContentBut('dashboard-navbar')
  })
  $('#tabs').find('#music-navbar').click(function () {
    hideAllNavBarContentBut('music-navbar')
  })
  $('#tabs').find('#platforms-navbar').click(function () {
    hideAllNavBarContentBut('platforms-navbar')
  })

  $('#tabs').find('#logout-header-link').click(function () {
    CurrentUser.logout()
  })

  $('#music-navbar-content').hide()

  $('#login-header-link').click(function (event) {
    $('#login-modal').show()
    $('#login-modal').position({
      my: 'left+3 bottom-3',
      of: event
    })
  })
})

var NavBar = (function () {
  var hideAllNavBarContentBut = function (nothidedNavbar) {
    var navbarElements = $('#tabs').find('.ui-button').toArray()
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

    $('#tabs').find('#logout-header-link').click(function () {
      CurrentUser.logout()
    })

    $('#music-navbar-content').hide()

    $('#login-header-link').click(function (event) {
      $('#login-modal').show('puff', 1000)
      $('#login-modal').position({
        my: 'left+3 bottom-3',
        of: event
      })
    })
  })
})()

// login status
var CurrentUser = (function () {
  var currentUserDetail

  var isLogOutDispaly = function () {
    $('#logout-header-link').hide()
    $('#login-header-link').show()
    $('#login-modal').hide()
  }

  var isLogInDispaly = function () {
    $('#logout-header-link').show()
    $('#login-header-link').hide()
    $('#login-modal').hide()
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
            console.error(jqXHR.responseText)
            isLogOutDispaly()
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
            console.error(jqXHR.responseText)
            isLogOutDispaly()
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
    }
  }
})()

// check user status when document is ready
$(document).ready(function () {
    // initiate login form
  $('#login-modal').draggable().resizable()

    // get current user
  CurrentUser.retrieveCurrentUser()

  $('#login-form').find('#login-submit').click(function () {
        // get form values
    var username = $('#login-form').find('#username').val()
    var password = $('#login-form').find('#password').val()

    CurrentUser.login(username, password)
  })

  $('#login-form').find('#register-submit').click(function () {
            // get form values
    var username = $('#login-form').find('#username').val()
    var password = $('#login-form').find('#password').val()

    CurrentUser.register(username, password)
  })
})

// END login form

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
