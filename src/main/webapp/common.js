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

	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			responseHandler(xmlhttp.responseText);
		}
	};

	xmlhttp.open("GET", url, true);
	xmlhttp.send();
}

var searchMethods = function(parameters, cb) {
	var ws = createWebServiceURI(urlPrefix + 'getMethodByTitle', parameters);

	var ret = [];

	callWebService(function(resultset) {
		ret = JSON.parse(resultset);

		g_method_list = ret;

		/*
		 * var innerHTML = '<select id="method-selector" class="selectpicker"
		 * data-live-search="true">'; obj.forEach(function(method) {
		 * 
		 * method = method.replace(/'/g, "\\'"); innerHTML += '<option>' +
		 * method + '</option>';
		 * 
		 * }); innerHTML += '</select>';
		 * 
		 * console.log(innerHTML);
		 * document.getElementById("method-selector-div").innerHTML = innerHTML;
		 * 
		 * $('.selectpicker').selectpicker('refresh');
		 */

	}, ws);

};

var urlPrefix = "ws/";

function autoload() {
	var str = window.location.search;
	var objURL = {};

	str.replace(new RegExp("([^?=&]+)(=([^&]*))?", "g"), function($0, $1, $2,
			$3) {
		objURL[$1] = $3;
	});

	if (objURL["embedded"] != null) {
		urlPrefix = "";
	}

	if (objURL["composition"] != null) {
		loadComposition(objURL["composition"]);
	}

	searchMethods({
		searchString : ""
	});

}

var g_method_list;

var drawLine = function() {
	var method_title = $('#method-name-input').val();

	var parameters = {
		methodName : method_title
	};
	var ws = createWebServiceURI(urlPrefix + 'createImage', parameters);

	var image_html = '<img src="http://localhost:8081/' + ws + '">';
	document.getElementById('blue_line_image').innerHTML = image_html;

};

var substringMatcher = function() {
	return function findMatches(q, cb) {
		var matches, substringRegex;

		// an array that will be populated with substring matches
		matches = [];

		// regex used to determine if a string contains the substring `q`
		substrRegex = new RegExp(q, 'i');

		/*
		 * if (q.length > 3) { searchMethods({ searchString : q }, cb); }
		 */
		// iterate through the pool of strings and for any string that
		// contains the substring `q`, add it to the `matches` array
		$.each(g_method_list, function(i, str) {
			// $.each(strs, function(i, str) {
			if (substrRegex.test(str)) {
				matches.push(str);
			}
		});

		cb(matches);
	};
};
