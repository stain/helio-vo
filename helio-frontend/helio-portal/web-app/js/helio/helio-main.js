/**
 * Base module for the HELIO project.
 * This is used to initialize the HELIO project and provides some generic classes.
 */
$(document).ready(function() {
    $("#task_upload2").click(function() {
        $('#content').load('../task/uploadVoTable', function() {
            var task = new helio.VOTableUploadTask("votableupload"); 
            task.init.call(task);
        });
    });    

    $("#task_propagationmodel").click(function() {
        $('#content').load('../task/propagationModel?taskName=pmFwCme', function() {
            var task = new helio.PropagationModelTask("pmFwCme"); 
            task.init.call(task);
        });
    });
});

/**
 * The helio name space
 */
(function() {
// use existing global 'helio' (here, 'this' === window/global context)
//  or create new object/package
this.helio = this.helio ||
  { toString: function() { return 'package: helio'; } };
  
// cache for temporary data. Temporary data is stored in an associative array.
this.helio.cache = new Object();
  
})();
