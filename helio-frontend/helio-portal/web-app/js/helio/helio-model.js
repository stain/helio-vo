/**
 * Helper objects to handle the client-side model.
 * The main purpose of these objects is to cache data.
 * Typically they will be stored in a globally accessible object called 'helio.cache'.
 */

(function() {
    
helio.AbstractModel = function(name, icon) {    
    this.name = name;
    this.icon = icon;
};

/**
 * Data object to store a time range. This is used within the timeRanes
 */
helio.TimeRange = function(/*Date*/ start, /*Date*/ end) {
    this.start = start ? start : new Date();
    this.end = end ? end : new Date();
};

/**
 * Data object to store a collecion of time ranges and a name
 * @param {String} name the name of the time range, if any.
 */
helio.TimeRanges = function(name) {
    helio.AbstractSummary.apply(this, [name, 'time']);
    this.timeRanges = []; // Array of helio.TimeRange
};

//create TimeRanges as subclass of AbstractModel
helio.TimeRanges.prototype = new helio.AbstractModel;
helio.TimeRanges.prototype.constructor = helio.TimeRanges;


/**
 * Data object to store a collection of parameters for a specific task
 * @param {String} taskName the name of the task. Mandatory
 * @param {String} name the name of the params.
 */
helio.ParamSet = function(taskName, name) {
    helio.AbstractSummary.apply(this, [name, 'paramset']);
    if (!taskName) 
        throw "Argument 'taskName' must not be null"; 
    this.taskName = taskName;
    this.params = new Object(); // object will be used as associative array.
};

//create ParamSet subclass of AbstractModel
helio.ParamSet.prototype = new helio.AbstractModel;
helio.ParamSet.prototype.constructor = helio.ParamSet;

/**
 * Data object to hold an instrument.
 * @param {String} name of the instruments param. optional.
 * @returns {helio.Instruments}
 */
helio.Instruments = function(name) {
    helio.AbstractSummary.apply(this, [name, 'instrument']);
    this.instruments = []; // list of instrument keys.
};

//create Instruments subclass of AbstractModel
helio.Instruments.prototype = new helio.AbstractModel;
helio.Instruments.prototype.constructor = helio.Instruments;

/**
 * Data object to hold an observatory.
 * @param {String} name of the instruments param. optional.
 * @returns {helio.Observatories}
 */
helio.Observatories = function(name) {
    helio.AbstractSummary.apply(this, [name, 'observatory']);
    this.observatories = [];
};

//create Observatories subclass of AbstractModel
helio.Observatories.prototype = new helio.AbstractModel;
helio.Observatories.prototype.constructor = helio.Observatories;

/**
 * Data object to hold an event list.
 * @param {String} name of the event lists param. optional.
 * @returns {helio.EventLists}
 */
helio.EventLists = function(name) {
    helio.AbstractSummary.apply(this, [name, 'eventlist']);
    this.name = name;
    this.eventLists = [];
};

//create EventLists subclass of AbstractModel
helio.EventLists.prototype = new helio.AbstractModel;
helio.EventLists.prototype.constructor = helio.EventLists;

})();
