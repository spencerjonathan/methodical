/*******************************************************************************
 * Class to manage the composition submit button
 ******************************************************************************/

var SaveButton = function(p_saveButton, p_user_alert) {

	function saveCompositionDialog() {
		callWebService(function(result) {
			dialogWizard("Save Composition", result, function() {
			}, "Save", saveComposition);
		}, "./savecomposition.xml");
	}
	
	p_saveButton.onclick = saveCompositionDialog;

	function saveComposition() {
		var title = document.getElementById('compositionname').value;
		var ws = createWebServiceURI(urlPrefix + 'saveComposition', {
			title : title
		});

		var composition_string = document.getElementById("input").value;

		callPutWebService(handleSaveResponse, errorResponse, ws,
				composition_string, 'text/html');
	}

	function errorResponse(responseText) {
		var error = JSON.parse(responseText);
		// Do nothing for now
	}

	function handleSaveResponse(responseText) {

		var response = JSON.parse(responseText);
		if (response > 0) {
			p_user_alert.display(
					"<strong>Composition Saved!</strong>  Composition link: "
							+ document.origin + "/index.html?composition="
							+ response, "success");
		} else {
			p_user_alert.display(
					"<strong>Problem saving composition!</strong>", "warning");
		}

	}
}