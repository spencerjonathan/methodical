/**
 * 
 */

var TouchWizard = function(p_button) {


	var updateDropdown = function() {
		console.log("In updateDropdown()")

		var string_length = $('#method-name').val().length;

		if (string_length > 3) {
			searchMethods({
				searchString : $('#method-name').val()
			});

		}
	};

	var searchMethods = function(parameters) {
		var ws = createWebServiceURI(urlPrefix + 'getMethodByTitle', parameters);

		callWebService(
				function(resultset) {
					var obj = JSON.parse(resultset);

					var innerHTML = "";
					obj
							.forEach(function(method) {

								method = method.replace(/'/g, "\\'");
								innerHTML += '<li style="cursor:pointer" onclick="setSearchText(\''
										+ method + '\');">' + method + '</li>';

							});

					console.log(innerHTML);
					document.getElementById("method-dropdown").innerHTML = innerHTML;

				}, ws);

		return g_method_list;
	};

	var drawList = function(list, htmlElement) {
		var innerHTML = '<select id="method-select-list" style="height:100px; width:100%;" class="no-scroll" multiple="true">';

		list.forEach(function(method) {

			innerHTML += '<option>' + method + '</option>';

		});

		console.log(innerHTML);
		htmlElement.innerHTML = innerHTML;
	};

	var drawMethodList = function() {
		drawList(g_method_list, document
				.getElementById("selected-methods-list"));
	};

	var generateTouch = function() {
		var touch = "";

		var method_number = 0;
		g_method_list.forEach(function(method) {
			touch += 'METHOD M' + ++method_number + ' = "' + method + '"\n'
		});
		touch += "\nCALL B = " + document.getElementById('bobnotation').value
				+ "\n";
		touch += "CALL S = " + document.getElementById('singlenotation').value
				+ "\n";
		touch += "\nPART P1 = { P P P P P }\nCOMPOSITION = { M1 P1 }";

		document.getElementById('input').value = touch;
	};

	var addMethodName = function() {
		var name = document.getElementById('method-name').value;

		g_method_list.forEach(function(method) {
			if (name == method)
				return;
		});

		g_method_list.push(name);

		drawMethodList();

		document.getElementById('method-name').value = "";
	};

	var removeMethodName = function() {
		var select_list = document.getElementById("method-select-list");
		var name = select_list.options[select_list.selectedIndex].text;

		var new_list = [];

		g_method_list.forEach(function(method) {
			if (name != method)
				new_list.push(method);
		});

		g_method_list = new_list;

		drawMethodList();
	}

	var display = function() {
		callWebService(function(result) {
			dialogWizard("Touch Wizard", result, drawMethodList,
					"Generate", generateTouch);
			
			document.getElementById("method-name").oninput = updateDropdown;
			document.getElementById("add-method-name").onclick = addMethodName;
			document.getElementById("remove-method-name").onclick = removeMethodName;
			
		}, "./touchwizard.xml");
		
		
		//p_button.oninput = updateDropdown;
	}

	p_button.onclick = display;
	
};

var setSearchText = function(text) {
	document.getElementById("method-name").value = text;
}

