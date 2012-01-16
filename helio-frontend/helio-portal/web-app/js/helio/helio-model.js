/**
 * Helper objects to handle the client-side model.
 * The main purpose of these objects is to cache data.
 * Typically they will be stored in a globally accessible object called 'helio.cache'.
 */

(function() {
/**
 * Data object to store a time range
 */
helio.TimeRange = function(/*Date*/ start, /*Date*/ end) {
    this.start = start ? start : new Date();
    this.end = end ? end : new Date();
};

/**
 * Data object to store a collecion of time ranges and a name
 * @param {String} name the name of the time range, if any.
 */
helio.TimeRanges = function(/*String*/ name) {
    this.timeRanges = [];
    this.name = name;
};

/**
 * Data object to store a collecion of parameters for a specific task
 * @param {String} taskName the name of the task. Mandatory
 * @param {String} name the name of the time range, if any.
 */
helio.ParamSet = function(/*String*/taskName, /*String*/ name) {
    if (!taskName) 
        throw "Argument 'taskName' must not be null"; 
    this.taskName = taskName;
    this.name = name;
    this.params = new Object(); // object will be used as associative array.
};

})();
