/************************************************************************* 
	 * Class to manage alerts to the user
	 *************************************************************************/

	var UserAlert = function(p_alert_element) {
		this.alert_element = p_alert_element;
	}

	UserAlert.prototype.display = function(p_message, p_severity) {
		this.alert_element.style.display = "block";

		var l_severity_pretext = "";
		
		if (p_severity == "warning") {
			l_severity_pretext = "<strong>Warning!</strong> ";
		}
		
		this.alert_element.className ="col-sm-12 alert alert-" + p_severity;
		
		this.alert_element.innerHTML = l_severity_pretext + p_message;

	}

	UserAlert.prototype.hide = function() {
		this.alert_element.style.display = "none";
	}