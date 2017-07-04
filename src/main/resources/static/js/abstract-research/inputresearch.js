//actionToDoForResearchOnKeyUp is the action to do when timeout is over on key up
//function taking a String as a parameter
var InputResearch = function(elementToApply, actionToDoForResearchOnKeyUp, timeoutTime){

      var timeoutResearch
      elementToApply.keyup(function(event) {
        //récupère la donnée de l'input & appel ajax
        if(timeoutResearch != undefined) {
          clearTimeout(timeoutResearch)
        }

      timeoutResearch =
          setTimeout(actionToDoForResearchOnKeyUp(elementToApply.val()), timeoutTime)
      })

}
