(function() {
  var app = angular.module('methodMadness', []);

  app.controller('appController', function(){
    //this.products = gems;
	this.methods = methodList;
	
	this.navigationClick = navigationClick;
  });

  var navigationClick = function ($button) {
	alert("Button " + $button + " clicked");
  }

  var navigationButtons = [
	"touchesbutton", "methodsbutton", "homebutton"
  ]
  
 
  
     
})();