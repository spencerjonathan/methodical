// User clicks on one of the navigation buttons at the top of the page
var navigationClick = function(button) {

	pages.forEach(function(entry) {

		var navElement = document.getElementById(entry + "button");
		var pageElement = document.getElementById(entry + "page");
		if (entry == button) {
			navElement.setAttribute("class", "active");
			pageElement.removeAttribute("hidden");
			console.log("Setting " + entry + " to active" + " for button "
					+ navElement.text);
		} else {
			navElement.removeAttribute("class");
			pageElement.setAttribute("hidden", "true");
			console.log("Setting " + entry + " to inactive");
		}
	});

}

// Draw the list of methods on the Methods page
var drawMethodList = function() {
	drawList(methodList, document.getElementById("methodlist"),
			"handleMethodListClick");
}

var drawFavouriteList = function() {
	drawList(favouriteList, document.getElementById("favouritemethodlist"),
			"handleFavouriteListClick");
}

var drawList = function(list, htmlElement, clickHandleFunction) {
	var innerHTML = "";
	var recordNumber = 0;

	// methodList
	list.forEach(function(method) {

		innerHTML += '<span style="cursor:pointer"'

		innerHTML += ' onclick="' + clickHandleFunction + '(' + recordNumber
				+ ')" class="list-group-item';

		if (method.selected == true) {
			innerHTML += ' active'
		}

		/*
		 * if (selectedMethodNumber != null && recordNumber ==
		 * selectedItemNumber) { innerHTML += ' active' }
		 */

		innerHTML += '"> ' + method.name;

		if (method.favourite == true) {
			innerHTML += '  <span class="label label-success">Favouite</span>';
		}
		;
		innerHTML += "</span>";
		recordNumber++;
	});
	console.log(innerHTML);
	htmlElement.innerHTML = innerHTML;
}

// What happens if a user clicks on a method in the method list
var handleMethodListClick = function(itemPosition) {
	methodList.forEach(function(method) {
		method.selected = false;
	});
	methodList[itemPosition].selected = true;

	selectedMethodNumber = itemPosition;
	drawMethodList();
	document.getElementById("methodButtons").removeAttribute("hidden");
	if (methodList[itemPosition].favourite) {
		document.getElementById("methodFavouriteButton").setAttribute("value",
				"Remove From Favourites");
	} else {
		document.getElementById("methodFavouriteButton").setAttribute("value",
				"Add To Favourites");
	}
}

var handleMethodsFavouriteClick = function() {

	methodList[selectedMethodNumber].favourite = !(methodList[selectedMethodNumber].favourite);

	drawMethodList();
	if (methodList[selectedMethodNumber].favourite) {
		document.getElementById("methodFavouriteButton").setAttribute("value",
				"Remove From Favourites");
	} else {
		document.getElementById("methodFavouriteButton").setAttribute("value",
				"Add To Favourites");
	}

	var ws = createWebServiceURI('http://localhost:8080/setFavourite', {
		methodId : methodList[selectedMethodNumber].id,
		favourite : methodList[selectedMethodNumber].favourite
	});

	callWebService(handleSetFavouriteResponse, ws);
}

var handleSetFavouriteResponse = function() {
	// Do nothing
}

var handleMethodsSaveClick = function() {
	document.getElementById("methodsSaveButton").setAttribute("disabled",
			"true");
}

var refreshMethodsList = function() {
	var ws = createWebServiceURI('http://localhost:8080/getMethodList', {
		numberOfBells : $('#methodsearchnumber').val(),
		searchString : $('#methodsearchtext').val()
	});
	/*
	 * numberOfBellsForMethodsSearch = document
	 * .getElementById("methodsearchnumber").value;
	 */
	callWebService(handleMethodListResponse, ws);
}

function handleMethodListResponse(responseText) {
	var obj = JSON.parse(responseText);

	methodList = obj.items;
	methodList.forEach(function(method) {
		method.selected = false;
	});

	drawMethodList();
}

function createNode(tag_type, obj) {
	var return_string = "<" + tag_type + " ";
	for ( var propt in obj) {
		return_string += propt + "=\"" + obj[propt] + "\" ";

	}
	return_string += ">";
	return return_string;
}

function handleMethodsEditClick() {
	handleCreateMethodClick(methodList[selectedMethodNumber]);
}

function handleCreateMethodClick(method) {
	// methodList[selectedMethodNumber].

	var nameAttributes = {
		id : "methodname",
		name : "methodname",
		type : "text",
		placeholder : "Plain Bob",
		class : "form-control input-md"
	};
	var numberAttributes = {
		id : "methodbellnumber",
		name : "methodbellnumber",
		type : "number",
		min : 3,
		max : 12,
		placeholder : 6,
		class : "form-control input-md"
	};
	var notationAttributes = {
		id : "methodnotation",
		name : "methodnotation",
		type : "text",
		placeholder : "X.16.X.16.X.16.X.16.X.16.X.12",
		class : "form-control input-md"
	};
	var bobAttributes = {
		id : "bobnotation",
		name : "bobnotation",
		type : "text",
		placeholder : "14",
		class : "form-control input-md"
	};
	var singleAttributes = {
		id : "singlenotation",
		name : "singlenotation",
		type : "text",
		placeholder : "1234",
		class : "form-control input-md"
	};
	var idAttributes = {
		id : "methodid",
		name : "methodid",
		hidden : "true",
		type : "text"
	}

	if (method != null) {
		nameAttributes.value = method.name;
		numberAttributes.value = method.number_of_bells;
		notationAttributes.value = method.place_notation;
		bobAttributes.value = method.bob_notation;
		singleAttributes.value = method.single_notation;
		idAttributes.value = method.id;
	}

	bootbox
			.dialog({
				title : "Create Method.",
				message : '<div class="row">  ' + '<div class="col-md-12"> '
						+ '<form class="form-horizontal"> '
						+ createNode("input", idAttributes)

						+ '<div class="form-group"> '
						+ '<label class="col-md-4 control-label">Method Name</label> '
						+ '<div class="col-md-8"> '

						+ createNode("input", nameAttributes)
						// + '<input id="methodname" name="methodname"
						// type="text" placeholder="Plain Bob"
						// class="form-control input-md"> '
						+ '<span class="help-block">Enter the method\'s name here</span> </div> '
						+ '</div> '
						+

						'<div class="form-group"> '
						+ '<label class="col-md-4 control-label">Number of Bells</label> '
						+ '<div class="col-md-2"> '
						+ createNode("input", numberAttributes)
						// + '<input id="methodbellnumber"
						// name="methodbellnumber" type="number" min="3"
						// max="12" placeholder="6" class="form-control
						// input-md"> '
						+ '<span class="help-block">Enter the number of bells (e.g. Minor = 6)</span> </div> '
						+ '</div> '
						+

						'<div class="form-group"> '
						+ '<label class="col-md-4 control-label">Method Place Notation</label> '
						+ '<div class="col-md-8"> '
						+ createNode("input", notationAttributes)
						// + '<input id="methodnotation" name="methodnotation"
						// type="text"
						// placeholder="X.16.X.16.X.16.X.16.X.16.X.12"
						// class="form-control input-md"> '
						+ '<span class="help-block">Enter place notation here</span> </div> '
						+ '</div> '
						+

						'<div class="form-group"> '
						+ '<label class="col-md-4 control-label">Bob Place Notation</label> '
						+ '<div class="col-md-8"> '
						+ createNode("input", bobAttributes)
						// + '<input id="bobnotation" name="bobnotation"
						// type="text" placeholder="14" class="form-control
						// input-md"> '
						+ '<span class="help-block">Enter the bob\'s place notation here</span> </div> '
						+ '</div> '
						+

						'<div class="form-group"> '
						+ '<label class="col-md-4 control-label">Single Place Notation</label> '
						+ '<div class="col-md-8"> '
						+ createNode("input", singleAttributes)
						// + '<input id="singlenotation" name="singlenotation"
						// type="text" placeholder="1234" class="form-control
						// input-md"> '
						+ '<span class="help-block">Enter the single\'s place notation here</span> </div> '
						+ '</form> </div>  </div>',
				buttons : {
					cancel : {
						label : "Cancel",
						className : "btn-cancel",
						callback : function() {
						}
					},
					success : {
						label : "Save",
						className : "btn-success",
						callback : function() {
							console.log("Name is "
									+ $("input[id='methodname'").val()
									+ $('#methodname').val());
							callWebService(function(response) {
							}, createWebServiceURI(
									'http://localhost:8080/createMethod', {
										methodName : $('#methodname').val(),
										numberOfBells : $('#methodbellnumber')
												.val(),
										methodPlaceNotation : $(
												'#methodnotation').val(),
										bobPlaceNotation : $('#bobnotation')
												.val(),
										singlePlaceNotation : $(
												'#singlenotation').val(),
										id : $('#methodid').val()
									}));
							// var name = $('#name').val();
							// var answer =
							// $("input[name='awesomeness']:checked").val()
							// Example.show("Method Created");
						}
					}
				}
			});
}

function createWebServiceURI(url, params) {

	var first = true;
	for ( var propt in params) {
		if (first) {
			url += "?";
			first = false;
		} else {
			url += "&";
		}
		url += propt + "=" + encodeURIComponent(params[propt])
	}
	console.log("createWebServiceURI generated: " + url);
	return url;
}

var callWebService = function(responseHandler, url) {
	var xmlhttp = new XMLHttpRequest();
	// var url = "http://localhost:8080/getMethodList?numberOfBells=6";

	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			responseHandler(xmlhttp.responseText);
		}
	};

	xmlhttp.open("GET", url, true);
	xmlhttp.send();
}

var callPutWebService = function(responseHandler, url, data) {
	var xmlhttp = new XMLHttpRequest();
	// var url = "http://localhost:8080/getMethodList?numberOfBells=6";

	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			responseHandler(xmlhttp.responseText);
		}
	};

	xmlhttp.open("POST", url, true);
	xmlhttp.setRequestHeader('Content-Type', 'application/json')

	var data_string = JSON.stringify(data);
	console.log(data_string);
	xmlhttp.send(data_string);
}

var refreshFavouritesList = function() {
	numberOfBellsForFavouritesSearch = document
			.getElementById("favouritesearchnumber").value;
	
	var ws = createWebServiceURI('http://localhost:8080/getMethodList', {
		numberOfBells : numberOfBellsForFavouritesSearch,
		favouritesOnly : true
	});
	
	callWebService(handleFavouriteListResponse, ws);
}

function handleMethodsBlueLineClick() {

	var innerHTML = '<img src="http://localhost:8080/createImage?methodId='
			+ methodList[selectedMethodNumber].id + '">';
	// innerHTML += '&numberOfBells=' + numberOfBellsForMethodsSearch + '">';
	document.getElementById("bluelineimage").innerHTML = innerHTML;
}

/*
 * var refreshFavouritesList = function() { var xmlhttp = new XMLHttpRequest();
 * var url = "http://localhost:8080/getMethodList?numberOfBells=6";
 * 
 * xmlhttp.onreadystatechange=function() { if (xmlhttp.readyState == 4 &&
 * xmlhttp.status == 200) { handleFavouriteListResponse(xmlhttp.responseText); } };
 * 
 * xmlhttp.open("GET", url, true); xmlhttp.send(); }
 */
function handleFavouriteListResponse(responseText) {
	var obj = JSON.parse(responseText);

	favouriteList = obj.items;
	favouriteList.forEach(function(method) {
		method.selected = false;
	});

	drawFavouriteList();
}

// What happens if a user clicks on a method in the method list
var handleFavouriteListClick = function(itemPosition) {
	// selectedFavouriteNumber = itemPosition;
	favouriteList[itemPosition].selected = !(favouriteList[itemPosition].selected);
	drawFavouriteList();
	/*
	 * document.getElementById("methodButtons").removeAttribute("hidden"); if
	 * (methodList[itemPosition].favourite) {
	 * document.getElementById("methodFavouriteButton").setAttribute("value",
	 * "Remove From Favourites"); } else {
	 * document.getElementById("methodFavouriteButton").setAttribute("value",
	 * "Add To Favourites"); }
	 */
}

function handleCreateTouchClick() {
	var selectedMethods = [];
	var runningCount = 0;

	favouriteList.forEach(function(method) {
		if (method.selected) {
			selectedMethods[runningCount++] = method.id;
			/*
			 * selectedMethods[runningCount++] = { name : method.name, favourite :
			 * false };
			 */
		}
	});

	/*
	 * var payload = { id : 0, number_of_bells :
	 * numberOfBellsForFavouritesSearch, items : selectedMethods };
	 */

	var payload = selectedMethods;

	callPutWebService(handleTouchListResponse,
			"http://localhost:8080/createTouches?numberOfBells="
					+ numberOfBellsForFavouritesSearch, payload);
}

function handleTouchListResponse(responseText) {
	var obj = JSON.parse(responseText);

	touchMaps = obj.maps;

	var innerHTML = "";
	var length_no = 0;

	// Make sure that touch_lengths is empty
	/*
	 * touchLengths = [];
	 * 
	 * touchMaps.forEach(function(touch_length_set) { touchLengths[length_no++] =
	 * touch_length_set.length; console.log("Length: " +
	 * touch_length_set.length); var touches = touch_length_set.items;
	 * touches.forEach(function(touch) { var text_array =
	 * touch.text_representation; text_array.forEach(function(line) { innerHTML +=
	 * line + "<br>"; }); innerHTML += "<br><br>"; });
	 * 
	 * });
	 */

	drawTouchLengths();

	document.getElementById("touchlist").innerHTML = ""; // innerHTML;

	// drawMethodList();
}

function drawTouchLengths() {

	var innerHTML = "";
	/*
	 * touchLengths.forEach(function(length) { innerHTML += '<button
	 * type="button" class="btn btn-default">' + length + '</button>'; });
	 */

	touchMaps
			.forEach(function(touch_length_set) {
				innerHTML += '<button type="button" class="btn btn-default" onClick="drawTouches('
						+ touch_length_set.length
						+ ')">'
						+ touch_length_set.length + '</button>';
			});

	document.getElementById("touchlengthbuttons").innerHTML = innerHTML;

}

function drawTouches(length) {
	var innerHTML = "";

	touchMaps
			.forEach(function(touch_length_set) {

				if (touch_length_set.length == length) {

					var touches = touch_length_set.items;
					innerHTML += '<ul class="list-group">';
					touches
							.forEach(function(touch) {

								innerHTML += '<li onclick="handleTouchClick('
										+ length + ', ' + touch.uid
										+ ')" ondblclick="handleTouchDblClick('
										+ length + ', ' + touch.uid
										+ ')" class="list-group-item';
								if (touch.selected == true) {
									innerHTML += ' active'
								}

								innerHTML += '">';

								if (touch.favourite == true) {
									innerHTML += '<span class="label label-success">Favouite</span><br>';
								}

								innerHTML += '<table class="table"><thead><tr><th>Method</th><th>Lead Head</th><th>Call</th></tr></thead><tbody>';

								// var text_array = touch.text_representation;
								var leads = touch.leads;

								touch.leads.forEach(function(lead) {
									innerHTML += "<tr><td>" + lead.method
											+ "</td><td>" + lead.bell_order
											+ "</td><td>" + lead.call
											+ "</td></tr>";

								});
								innerHTML += "</table></li>";
							});
					innerHTML += '</ul>';
				}
				;

			});

	document.getElementById("touchlist").innerHTML = innerHTML;
}

function handleTouchClick(length, uid) {
	touchMaps.forEach(function(touch_length_set) {
		if (touch_length_set.length == length) {
			touch_length_set.items.forEach(function(touch) {
				if (touch.uid == uid) {
					if (touch.selected == true) {
						touch.selected = false;
					} else {
						touch.selected = true;
					}
				}
			});
		}
		;
	});

	drawTouches(length);
}

function handleTouchDblClick(length, uid) {
	touchMaps.forEach(function(touch_length_set) {
		if (touch_length_set.length == length) {
			touch_length_set.items.forEach(function(touch) {
				if (touch.uid == uid) {
					if (touch.favourite == true) {
						touch.favourite = false;
					} else {
						touch.favourite = true;
					}
				}
			});
		}
		;
	});

	drawTouches(length);
}

// Some Variables

var pages = [ "touches", "methods", "home", "login" ];

var methodList = [];

var numberOfBellsForFavouritesSearch = 6;

var numberOfBellsForMethodsSearch = 6;

var favouriteList = [];

var selectedMethodNumber = null;

var touchLengths = [];

var touchMaps = [];

/* var selectedFavouriteNumber = null; */