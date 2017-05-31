var EventRoller = (function(){

  var eventRollerElement
  var eventRollerMaxWidth;

  var uiEventTemplateElement = $('<div><div>').addClass('ui-event');

  $(document).ready(function(){
    eventRollerElement = $('.ui-event-roller');
    eventRollerMaxWidth = parseInt(eventRollerElement.css('max-width').replace('px', ''));
    uiEventTemplateElement.css('width', eventRollerMaxWidth)
  })

  var popuEvent = function(message){
    uiEventTemplateElement.css('left', -eventRollerMaxWidth)
    uiEventTemplateElement.text(message)
    eventRollerElement.append(uiEventTemplateElement);
    uiEventTemplateElement.animate({
      left: 0
    })
    setTimeout(function(){
      popOutEvent(uiEventTemplateElement)
    }, 2000)
  }

  var popOutEvent = function(uiEvent){
    uiEvent.animate({
      left: -eventRollerMaxWidth
    }, 400, 'swing', function(){

    })
  }

  return {
    pushEvent: function(message){
      popuEvent(message);
    }
  }

})()
