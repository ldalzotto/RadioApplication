
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
        MusicView.displayMusicPanel()
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


// END Music View
jQuery(function ($) {
  var panelList = $('#music-panel-list')
  panelList.sortable()
})
