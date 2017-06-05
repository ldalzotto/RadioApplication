
var HostName = (function () {
  var host
  var port
  var protocol

  $(document).ready(function () {
    protocol = window.location.protocol
    host = window.location.hostname
    port = window.location.port
  })

  return {
    getSourceUrl: function () {
      return protocol + host + port
    }
  }
})()

// header navigation

var NavBar = (function () {
  var tabsElement

  var maximumNavBarLenght

  // determine navbar max horizontal length
  var determineMaximumNavBarLength = function () {
    var length = 0
    var navbarElements = tabsElement.find('a').toArray()
    for (i = 0; i < navbarElements.length; i++) {
      length = length + $(navbarElements[i]).width()
    }
    return length * 2
  }

  var hideAllNavBarContentBut = function (nothidedNavbar) {
    var navbarElements = tabsElement.find('a').toArray()
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
    tabsElement = $('#tabs')
    maximumNavBarLenght = determineMaximumNavBarLength()

    // resize event
    $(window).resize(function () {
      if ($(window).width() <= maximumNavBarLenght) {
        console.log('minimize')
      } else {
        console.log('maximize')
      }
    })

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
      CurrentUser.showModalOnEvent(event)
    })
  })
})()

// END Music View
jQuery(function ($) {
  var panelList = $('#music-panel-list')
  panelList.sortable()
})
