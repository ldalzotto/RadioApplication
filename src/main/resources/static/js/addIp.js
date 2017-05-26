var AddIpModal = (function () {
  var addIpModalElement
  var customModal

  var ipAddressText;
  var ipAddressList;
  var ipAddressButton;

  //initial elements
  var initialIpAddressText;
  var initialIpAddressList;
  var initialIpAddressButton;

  $(document).ready(function () {
    addIpModalElement = $('#add-ip-modal')
    //addIpModalElement.hide()
    var config = {
      closable: true,
      resizable: true
    }
    customModal = new CustomModal(addIpModalElement, config)
    ipAddressText = $('#ip-address-dialog')
    ipAddressList = $('#ip-address-list')
    ipAddressButton = $('#ip-address-button')

    initialIpAddressText = ipAddressText.html();
    initialIpAddressList = ipAddressList.html();
    initialIpAddressButton = ipAddressButton.html();

    addIpModalElement.hide();
  })

  return {
    showModalOnEvent: function (event) {
      customModal.showModal(event)
    },
    showModalWithListIp: function(listIp, currentIp, addingUser){
      AddIpModal.resetElements();
      ipAddressText.text(ipAddressText.text().replace("$!ipaddress", currentIp));

      for (var i = 0; i < listIp.length; i++){
        //add ip in list
        ipAddressList.append("<ul>" + listIp[i] + "</ul>");
      }
      //set the onclick of button
      ipAddressButton.text(ipAddressButton.text().replace("$!ipaddress", currentIp));
      ipAddressButton.click(function(){
        addingUser();
        console.log("register");
      })
      addIpModalElement.show();
        console.log(listIp);
    },
    resetAndHide: function(){
      AddIpModal.resetElements();
      addIpModalElement.hide();
    },
    resetElements: function(){
      ipAddressButton.unbind();
      ipAddressText.html(initialIpAddressText);
      ipAddressList.html(initialIpAddressList);
      ipAddressButton.html(initialIpAddressButton);
    },
    errorPopUp: function(message){
      customModal.errorPopUp(message);
    }
  }

})();
