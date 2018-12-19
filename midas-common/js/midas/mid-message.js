/* Module for Registration form application */
var CauMessageForm = function () {

	var self = this;
	var pathArray = window.location.pathname.split('/');
	var secondLevelLocation = "";
	if (pathArray.length > 2) {
		secondLevelLocation = pathArray[1] + "/";
	}
	var baseURL = window.location.protocol + "//" + window.location.host + "/" + secondLevelLocation;
	
  /* add members here */
    /* the model */
    var cauMessage = {
    	id: 0,
    	subject: ko.observable(),
		message: ko.observable(),
		sender: ko.observable(),
		sendDate: "",
		status: "R"
	};

    
    var cauMessages = ko.observableArray();
    var unreadMessages = ko.observableArray();
    self.selectedItem = ko.observable("");

    
    
    /* method to clear the task */
    var clearCauMessage = function () {
      cauMessage.subject(null);
      cauMessage.message(null);
    };
    

    function addCauMessage(collection, _id, _subject, _sender, _message, _sendDateS, _status) {
    	collection.push({
        	id: _id,
        	subject: _subject,
        	sender: _sender,
        	message: _message,
        	sendDateS: _sendDateS,
        	status: _status
    	});  
    }	    
    
    
    var deleteMessage = function( msg ) {
  	  if (confirm("¿Desea borrar el mensaje?") ) { 
  	    msg.status = "D";
      	changeStatus( msg );
  	    cauMessages.remove(msg);
  	    selectedItem("");
    	}
    }    
        

    /* form submission */
  	var submitCauMessage = function () {
  		cauMessage.message(cauMessage.message().concat("\n").concat(document.referrer));
  		createCauMessage(cauMessage);
  		clearCauMessage();
  	    $('#msg-form').modal('hide')
  	};    
    
 

    

 
  	
  	var callbackCauMessages = function(response) {
        $.each(response, function(index, item) {
        	addCauMessage(cauMessages,
        		   item.id, 
     			   item.subject,
     			   item.sender,
     			   item.message,
     			   item.sendDateS,
     			   item.status);
        }); 
    };     
 	
  	var callbackUnreadMessages = function(response) {
        $.each(response, function(index, item) {
        	addCauMessage(unreadMessages,
        		   item.id, 
     			   item.subject,
     			   item.sender,
     			   item.message,
     			   item.sendDateS,
     			   item.status);
        }); 
    };  
    
  var getCauMessages = function() {
	  getCauMessagesAjax().done(callbackCauMessages);	  
  }      
  
   
  self.selectItem = function( data ) {
	    self.selectedItem( data );
	    if (selectedItem().status === "S") {
		    selectedItem().status = "R";
	    	changeStatus( selectedItem() );
	    }
  };
	
  


  
  function createCauMessage(message) {
  	  return $.ajax({
  			headers: { 
  			    'Accept': 'application/json',
  			    'Content-Type': 'application/json' 
  			},		  
  		  datatype:'json',
  		  type: 'POST',
  		  url: baseURL + 'rest/messages/cau',
  		  data: ko.toJSON(message)
  	  });
    }  
  
  function changeStatus(msg) {
	  return $.ajax({
			headers: { 
			    'Accept': 'text/plain; charset=utf-8',
			    'Content-Type': 'application/json' 
			},		  
		  datatype:'json',
		  type: 'POST',
		  url: baseURL + 'rest/messages/change',
		  data: ko.toJSON(msg)
	  });
  }  
  
  
  function getCauMessagesAjax() {
  	return $.ajax({
	    	dataType:'json',
	    	type: 'GET',
	    	url: baseURL + 'rest/messages/all'
  	});
  }  
	
  function getUnreadMessages() {
  	return $.ajax({
	    	dataType:'json',
	    	type: 'GET',
	    	url: baseURL + 'rest/messages/unread'
  	});
  }    
    
 
  
  
  /* observable to compute number of completed tasks */
  var numOfUnreadMsgs = ko.computed(function() {
    //var unreadMsgs = ko.utils.arrayFilter(cauMessages(), function(msg) {
    //  return msg.status === 'S';
    //});
    return unreadMessages().length;
  });
  

  var init = function () {
    $(".cau-bindable").each(function(){
        ko.applyBindings(CauMessageForm, this);
    });	  

    getUnreadMessages().done(callbackUnreadMessages);

  };

	
  /* execute the init function when the DOM is ready */
  $(init);

  
  return {
    /* add members that will be exposed publicly */
	submitCauMessage:submitCauMessage,
    cauMessage:cauMessage,
    cauMessages:cauMessages,
    unreadMessages:unreadMessages,
    getCauMessages:getCauMessages,
    selecItem:selectItem,
    selectedItem:selectedItem,
    deleteMessage:deleteMessage,
    numOfUnreadMsgs:numOfUnreadMsgs
  };
}();







/* Module for Notification application */
var Notification = function () {

	var pathArray = window.location.pathname.split('/');
	var secondLevelLocation = pathArray[1];
	var baseURL = window.location.protocol + "//" + window.location.host + "/" + secondLevelLocation + "/";
	
	
	/* add members here */
    /* the model */
    var notification = {
    	id: ko.observable(),
    	receiver: ko.observable(),
		message: ko.observable(),
		sendDate: ko.observable(),
		readDate: ko.observable(),
		status: ko.observable(),
		style: ko.observable()
	};

    
    /* array of notifications */
    var notifications = ko.observableArray();
	
    
    /* method to clear the task */
    var clearNotification = function () {
    	notification.id(null),
		notification.message(null),
		notification.sendDate(null),
		notification.readDate(null),
		notification.status(null),
		notification.style(null)
    };    
    

    var deleteNotification = function (notification) {
    	notifications.remove(notification);
    }
    
    
    /* method to complete a task */
    var readNotification = function (notification) {
      //set status of task to complete
      notification.status('R');
    };
    
    function getUnreadNotifications() {
    	return $.ajax({
	    	dataType:'json',
	    	type: 'GET',
	    	url: baseURL + 'rest/notifications/unread'
    	});
    }  
    
    
    
    function sendReadNotification(not) {
    	  console.log(ko.toJSON(not));
    	  
    	  return $.ajax({
    			headers: { 
    			    'Accept': 'text/plain; charset=utf-8',
    			    'Content-Type': 'application/json' 
    			},		  
    		  datatype:'json',
    		  type: 'POST',
    		  url: baseURL + 'rest/notifications/read',
    		  data: ko.toJSON(not)
    	  });
      }      
    
    
    
    var markAsRead = function (data) {
        data.status('R');
        notification.sendDate(data.sendDate);
        notification.message(data.message);
        $('#not-form').modal('show');
        sendReadNotification(data)
    }

       
    
    var allCallbackSuccess = function(response) {
    	
        $.each(response, function(index, item) {
			clearNotification();
			notification.id(item.id);
			notification.message(item.message);
			//console.log(item.message);
			notification.sendDate(item.sendDate);
			notification.readDate(item.readDate);
			notification.status(item.status);
			notification.style(item.style);
			addNotification(notification);
        });    	
    	
    	ko.applyBindings(Notification, document.getElementById('not-display'));
    	ko.applyBindings(Notification, document.getElementById('not-form'));
    };    
    

    /* observable to compute number of completed tasks */
    var numOfUnreadNots = ko.computed(function() {
      var unreadNots = ko.utils.arrayFilter(notifications(), function(not) {
        return not.status() == 'S';
      });
      return unreadNots.length;
    });
    
    
    
    var init = function () {
        /* add code to initialize this module */
    	getUnreadNotifications().done(allCallbackSuccess);
    };

    	
      /* execute the init function when the DOM is ready */
      $(init);

      
      return {
        notification:notification,
        notifications:notifications,
        deleteNotification: deleteNotification,
        readNotification: readNotification,
        markAsRead: markAsRead,
        numOfUnreadNots: numOfUnreadNots
      };
      
}();



