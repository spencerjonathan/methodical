/**
 * 
 */

var g_selected_methods = [];

var TouchWizard = function(p_button) {


	var updateDropdown = function() {
		console.log("In updateDropdown()")

		var string_length = $('#method-name-input').val().length;

		if (string_length > 3) {
			searchMethods({
				searchString : $('#method-name-input').val()
			});

		}
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
		drawList(g_selected_methods, document
				.getElementById("selected-methods-list"));
	};

	var generateTouch = function() {
		var touch = "";

		var method_number = 0;
		g_selected_methods.forEach(function(method) {
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
		var name = document.getElementById('method-name-input').value;

		g_selected_methods.forEach(function(method) {
			if (name == method)
				return;
		});

		g_selected_methods.push(name);

		drawMethodList();

		document.getElementById('method-name-input').value = "";
	};

	var removeMethodName = function() {
		var select_list = document.getElementById("method-select-list");
		var name = select_list.options[select_list.selectedIndex].text;

		var new_list = [];

		g_selected_methods.forEach(function(method) {
			if (name != method)
				new_list.push(method);
		});

		g_selected_methods = new_list;

		drawMethodList();
	}

	var display = function() {
		callWebService(function(result) {
			dialogWizard("Touch Wizard", result, drawMethodList,
					"Generate", generateTouch);
			
			//document.getElementById("method-name-input").oninput = updateDropdown;
			document.getElementById("add-method-name").onclick = addMethodName;
			document.getElementById("remove-method-name").onclick = removeMethodName;
			
			

				$('#method-name-input').typeahead({
					hint : true,
					highlight : true,
					minLength : 1
				}, {
					name : 'states',
					source : substringMatcher()
				});

			
			
		}, "./touchwizard.xml");
		
		
		//p_button.oninput = updateDropdown;
	}

	p_button.onclick = display;
	
};

var setSearchText = function(text) {
	document.getElementById("method-name-input").value = text;
}

