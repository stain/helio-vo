/**
 * Helper objects to handle the client-side model.
 * The main purpose of these objects is to cache data.
 * Typically they will be stored in a globally accessible object called 'helio.cache'.
 */

(function() {
    
/**
 * Base class for all parameters
 * @param taskName the name of the assigned task
 * @param name the name of the parameter, optional
 * @param type the type of the parameters (hard-coded in sub-classes)
 * @returns {helio.AbstractModel}
 */
helio.AbstractModel = function(taskName, name, type) {
    this.taskName = taskName; 
    this.name = name;
    this.type = type;
    this.subtype = null;  // a subtype if required (e.g. for ParamSet)
    
    if (type && !taskName) {
        debugger;
        throw "Argument 'taskName' must not be null";
    }
};

/**
 * Data object to store a time range. This is used within the timeRanes
 */
helio.TimeRange = function(/*Date*/ startTime, /*Date*/ endTime) {
    this.startTime = startTime ? startTime : new Date();
    this.endTime = endTime ? endTime : new Date();
};

/**
 * Data object to store a collecion of time ranges and a name
 * @param {String} name the name of the time range, if any.
 */
helio.TimeRanges = function(taskName, name) {
    helio.AbstractModel.apply(this, [taskName, name, 'TimeRange']);
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
    helio.AbstractModel.apply(this, [taskName, name, 'ParamSet']);
    this.subtype = taskName; 
    this.params = new Object(); // object will be used as associative array.
};

//create ParamSet subclass of AbstractModel
helio.ParamSet.prototype = new helio.AbstractModel;
helio.ParamSet.prototype.constructor = helio.ParamSet;

/**
 * Data object to hold an instrument.
 * @param {String} name of the Instrument param. optional.
 * @returns {helio.Instrument}
 */
helio.Instrument = function(taskName, name) {
    helio.AbstractModel.apply(this, [taskName, name, 'Instrument']);
    this.Instrument = []; // list of instrument keys.
};

//create Instrument subclass of AbstractModel
helio.Instrument.prototype = new helio.AbstractModel;
helio.Instrument.prototype.constructor = helio.Instrument;

/**
 * Data object to hold an observatory.
 * @param {String} name of the observatory param. optional.
 * @returns {helio.Observatory}
 */
helio.Observatory = function(taskName, name) {
    helio.AbstractModel.apply(this, [taskName, name, 'Observatory']);
    this.Observatory = [];
};

//create Observatory subclass of AbstractModel
helio.Observatory.prototype = new helio.AbstractModel;
helio.Observatory.prototype.constructor = helio.Observatory;

/**
 * Data object to hold an event list.
 * @param {String} name of the event lists param. optional.
 * @returns {helio.EventList}
 */
helio.EventList = function(taskName, name) {
    helio.AbstractModel.apply(this, [taskName, name, 'EventList']);
    this.name = name;
    this.listNames = [];           // the list names (use addList and removeList to modify)
    this.config = { labels:[]};    // transient config object holding the names of the selected event lists.
    //this.filter = null;    // selected filters
};

//create EventList subclass of AbstractModel
helio.EventList.prototype = new helio.AbstractModel;
helio.EventList.prototype.constructor = helio.EventList;

/**
 * Convenience method to add a list name. Use this method whenever possible.
 * @param listName the name of the list
 * @param listLabel the label of the list
 */
helio.EventList.prototype.addList = function(listName, listLabel) {
    this.listNames.push(listName);
    this.config.labels.push(listLabel);
};

/**
 * Convenience method to remove a list name
 * @param listName the name of the list
 */
helio.EventList.prototype.removeList = function(listName) {
    var pos = $.inArray(listName, this.listNames);
    if (pos >= 0) {
        this.listNames.splice(pos, 1);
        this.config.labels.splice(pos, 1);
    }
};

})();
