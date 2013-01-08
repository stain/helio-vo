/**
 * The helio name space
 */
//use existing global 'helio' (here, 'this' === window/global context)
//or create new object/package
this.helio = this.helio || { toString: function() { return 'package: helio'; } };

/**
 * Base module for the HELIO project. This is used to initialize the HELIO
 * project and provides some generic classes.
 */
$(document).ready(function() {
	$("#helpBack").click(function() {
		history.back();
	});
	
	$("#helpClose").click(function() {
		window.close();
	});
});