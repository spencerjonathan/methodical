	/************************************************************************* 
	 * Class to manage the composition submit button
	 *************************************************************************/

	var SubmitButton = function(p_submitButton, p_composition_input,
			p_music_input, p_stopAtRounds_input, p_touch_output,
			p_music_output, p_repetition_output, p_user_alert) {

		var handleResponse = function(responseText) {

			var touch = JSON.parse(responseText);
			if (touch.exception) {
				p_user_alert.display(touch.message, "warning");
			} else {
				p_touch_output.draw(touch);
				p_repetition_output.draw(touch);
				p_music_output.draw(touch);
				p_user_alert.hide();
			}
		};

		function errorResponse(responseText) {
			var error = JSON.parse(responseText);
			p_user_alert.display(error.message, "warning");
		}

		var onClick = function(event) {
			var data_string = JSON.stringify({
				"composition" : p_composition_input.value,
				"music" : p_music_input.value,
				"stopAtRounds" : p_stopAtRounds_input.checked
			});

			callPutWebService(handleResponse, errorResponse, urlPrefix
					+ "parse", data_string, 'application/json');
		};

		p_submitButton.onclick = onClick;

	};