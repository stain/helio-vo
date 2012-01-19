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
            var task = helio.taskMap.findByName("pmFwCme");
            if (!task) {
                task = new helio.PropagationModelTask("pmFwCme"); 
                helio.taskMap.put("pmFwCme", task);
            }
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

// map of previously stored tasks
this.helio.TaskMap = function() {
    this.tasks = new Object();
};

/**
 * Find task in the task map
 * @param {String} taskName name of the task to find.
 * @return the found task or undefined if not found.
 */
this.helio.TaskMap.prototype.findByName = function(taskName) {
    return this.tasks[taskName];
};

/**
 * Put task into map.
 * @param {String} taskName name of the task to find.
 * @param {helio.AbstractTask} task the task instance to persist.
 * @return the found task or undefined if not found.
 */
this.helio.TaskMap.prototype.put = function(taskName, task) {
    this.tasks[taskName] = task;
};

/**
 * Provide global access to the task map.
 */
this.helio.taskMap = new helio.TaskMap();


})();
