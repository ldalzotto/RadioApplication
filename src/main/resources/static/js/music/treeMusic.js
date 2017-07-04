//Tree music
var TreeMusic = (function(){

  var treeElement
  var rootTreeElement
  var artistsTreeElements

  var researchArtistInput
  var timeoutResearch

  var templateTreeElement = "<li id=artist-{elementId}><span>{arstistName}</span></li>"

  $(document).ready(function() {
    treeElement = $(".tree")
    rootTreeElement = $("#root-tree-element")
    artistsTreeElements = rootTreeElement.find("#artists-tree-elements")

    researchArtistInput = $("#music-search-input")

    //gestion de la recherche
    researchArtistInput.keyup(function(event) {
      //récupère la donnée de l'input & appel ajax
      if(timeoutResearch != undefined) {
        clearTimeout(timeoutResearch)
      }
      timeoutResearch =
          setTimeout(function(){
            var artistInput = researchArtistInput.val()
            if(artistInput != "") {
              $.ajax({
                method: "GET",
                url: "user/" + CurrentUser.getUserName() + "/music/artists/search/" + artistInput,
                success: function(artistList){
                  console.log("Success call");
                  artistsTreeElements.find("li").remove()
                  artistList.forEach(function(artist){
                    artistsTreeElements.append(getElementTemplateFromArtist(artist))
                  })

                }
              })
            } else {
              refreshTreeMusic()
            }
          }, 500)

    })

  })

  var getElementTemplateFromArtist = function(artistName){
    return $.parseHTML(templateTreeElement.replace("{elementId}", artistName.replace(/\s/g, '')).replace("{arstistName}", artistName))
  }

  var removeTreeElement = function(artistName){
      $("id-" + artistName.replace(/\s/g, '')).remove()
  }

  var refreshTreeMusic = function(getUserName){
      artistsTreeElements.find("li").remove()
      $.ajax({
        method: "GET",
        url: "user/" + CurrentUser.getUserName() + "/music/artists/all-distinct",
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
