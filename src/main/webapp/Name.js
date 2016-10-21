/**
 * 
 */

var NameField = function(p_field, p_num) {
	var field = p_field;
	this.num = p_num;
	
	var variables = {
			num : p_num
	}
	
	var onchange_l = function(event) {
		alert(variables.num + " Name Changed To " + field.value);
	}
	
	this.setNum = function(p_num) {
		variables.num = p_num;
	}
	
	p_field.onchange = onchange_l;
	
	//this.field.onchange = onchange_l;
}

NameField.prototype.onchange = function(event) {
	alert(this.num + " Name Changed To " + this.field.value);
}

NameField.prototype.bind = function(p_field) {
	this.field = p_field;
	this.field.onchange = this.onchange;
	
	alert(this.field.name + " Bind called " + this.field.value);
}
