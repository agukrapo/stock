function goToLoginController() {
	var project = String(window.location).split("//")[1].split("/")[1];
	window.location.href = "/" + project + "/pages/login.faces";
}

function redirect(page){
	var project = String(window.location).split("//")[1].split("/")[1];
	window.location.href = "/" + project + page;
}

function returnObjById(id) {
	if (document.getElementById) {
		var returnVar = document.getElementById(id);
	} else if (document.all) {
		var returnVar = document.all[id];
	} else if (document.layers) {
		var returnVar = document.layers[id];
	}
	return returnVar;
}

var firstInputSelector = ' *:input:not(:image):visible:enabled:not(.rich-filter-input):first';

function focusFirstInput(formId) {
	jQuery('#' + formId + firstInputSelector).focus();
};

function focusFirstField(clientId, formId) {
	if (clientId == 'none' || clientId == null) {
		jQuery('#' + formId + firstInputSelector).focus();
	}
};

function focusById(id) {
	jQuery('#' + id).focus();
};

function noWarnLevelSeverity() {
	return document.getElementById("maximumSeverityOrdinal").value < 1;
};

var lastFocusedField;
function focusLastFocusedField() {
	if (lastFocusedField) {
		lastFocusedField.focus();
		lastFocusedField = undefined;
	}
};