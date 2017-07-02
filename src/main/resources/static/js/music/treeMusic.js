//Tree music
var TreeMusic = (function(){

  var treeElement
  var rootTreeElement
  var artistsTreeElements

  var templateTreeElement = "<li id=artist-{elementId}><span>{arstistName}</span></li>"

  $(document).ready(function() {
    treeElement = $(".tree")
    rootTreeElement = $("#root-tree-element")
    artistsTreeElements = rootTreeElement.find("#artists-tree-elements")
  })

  var getElementTemplateFromArtist = function(artistName){
    return $.parseHTML(templateTreeElement.replace("{elementId}", artistName.replace(/\s/g, '')).replace("{arstistName}", artistName))
  }

  var refreshTreeMusic = function(getUserName){
      //artistsTreeElements.remove()
      $.ajax({
        method: "GET",
        url: "user/" + getUserName() + "/music/artists/all-distinct",
        success: function(artistList){
          artistsTreeElements.find("li").remove()
          artistList.forEach(function(artist){
              artistsTreeElements.append(getElementTemplateFromArtist(artist))
          })
        }
      })
  }

  return {
      refreshTree: function(){
        refreshTreeMusic(CurrentUser.getUserName)
      },
      displayTree: function(){
        treeElement.show()
      }
  }

})()
