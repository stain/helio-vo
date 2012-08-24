/**
 * Helper objects to handle the client-side model.
 * The main purpose of these objects is to keep parameters on the client side.
 * The model classes resemble the param classes on the server side.
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
 * Data object to store a time range. If startTime == endTime the object stores a point in time rather than a time range.
 * @param startTime the start time either as Date object or as a string in format YYYY-MM-DDTHH:mm:ss. Defaults to the current time.
 * @param endTime the end time either as Date object or as a string in format YYYY-MM-DDTHH:mm:ss.Defaults to the current time.
 * @param {nubmer} autoAdjust make sure that end date is &gt;= start date. If autoAdjust &lt;0 no adjustment will be applied, 
 * if 0 or positive: minimal number of minutes between start and end date. Defaults to -1.
 */
helio.TimeRange = function(/*Date*/ startTime, /*Date*/ endTime, autoAdjust) {
    this.startTime = null;
    this.endTime = null;
    this.autoAdjust = autoAdjust ? autoAdjust : -1;
    this.setStartTime(startTime);
    this.setEndTime(endTime);
};

/**
 * Get the start and end time in an string[2] in format "YYYY-MM-DDTHH:mm:ss".
 * @return a string array of size 2, string[0] is the startTime, string[1] is the endTime.
 */
helio.TimeRange.prototype.timeAsString = function() {
    var ret = [];
    ret.push(this.startTime.format("YYYY-MM-DDTHH:mm:ss"));
    ret.push(this.endTime.format("YYYY-MM-DDTHH:mm:ss"));
    return ret;
};

/**
 * is it a time range.
 * @return true if startTime != endTime.
 */
helio.TimeRange.prototype.isRange = function() {
    return this.startTime.diff(this.endTime) != 0;
};

/**
 * set the start time
 * @param startTime the start time either as Date object or as a string in format YYYY-MM-DDTHH:mm:ss. Defaults to the current time.
 * @returns the start time as moment object
 */
helio.TimeRange.prototype.setStartTime = function(startTime) {
    this.startTime = startTime ? (
            typeof startTime === 'string' ? moment(startTime, "YYYY-MM-DDTHH:mm:ss") :
                moment(startTime)
            )
            : moment();
    this.__adjust("endTime");
    return this.startTime;
};

/**
 * set the end time
 * @param endTime the end time either as Date object or as a string in format YYYY-MM-DDTHH:mm:ss. Defaults to the current time.
 * @returns the end time as moment object
 */
helio.TimeRange.prototype.setEndTime = function(endTime) {
    this.endTime = endTime ? (
            typeof endTime === 'string' ? moment(endTime, "YYYY-MM-DDTHH:mm:ss") :
                moment(endTime)
        )
        : moment();
   this.__adjust("startTime");
   return this.endTime;
};

/**
 * Create a string representation of the time range.
 * @returns string representation of the time range or the single time.
 */
helio.TimeRange.prototype.toString = function() {
   return this.startTime.format("YYYY-MM-DDTHH:mm:ss") + (this.isRange() ? " &ndash; " + this.endTime.format("YYYY-MM-DDTHH:mm:ss") : "");
};

/**
 * Check if the end date is bigger or equal than the start date
 * @returns {boolean} true if the the range is valid.
 */
helio.TimeRange.prototype.isValid = function() {
    return this.endTime.diff(this.startTime) >= 0;
};

/**
 * Increment the start time and adjust end time if required
 * @param unit the unit (seconds, minutes, hours, days)
 * @param inc inc factor, use negative numbers for decrement.
 * @returns the adjusted start time.
 */
helio.TimeRange.prototype.incStartTime = function(unit, inc) {
    this.startTime.add(unit, inc);
    this.__adjust("startTime");
    return this.startTime;
};

/**
 * Increment the end time and adjust start time if required
 * @param unit the unit (seconds, minutes, hours, days)
 * @param inc inc factor, use negative numbers for decrement.
 * @returns the adjusted end time.
 */
helio.TimeRange.prototype.incEndTime = function(unit, inc) {
    this.endTime.add(unit, inc);
    this.__adjust("endTime");
    return this.endTime;
};

/**
 * do auto adjustment if needed.
 * @param toAdjust which field to adjust? either "startTime" or "endTime".
 */
helio.TimeRange.prototype.__adjust = function(toAdjust) {
    if (this.autoAdjust >= 0) {
        var tmpEndTime = moment(this.endTime);
        var tmpStartTime = moment(this.startTime);
        tmpEndTime.add("minutes", this.autoAdjust);
        
        if (tmpEndTime.diff(tmpStartTime) < 0) {
             
            // we have to adjust
            if (toAdjust == "startTime") {
                tmpEndTime = moments(this.endTime);  // reset end time
                this.startTime == tmpEndTime.add("minutes", -this.autoAdjust);
            } else if (toAdjust == "endTime") {
                this.endTime == tmpStartTime.add("minutes", this.autoAdjust);                
            } else {
                throw "Internal Error: unknown value for argument 'toAdjust': " + toAdjust;
            }
        }
    }
};

/**
 * Data object to store a collection of time ranges and a name
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
    this.name = name;
    this.instruments = []; // list of instrument keys.
    this.config = { labels:[]};    // transient config object holding the names of the selected instrument.

};

//create Instrument subclass of AbstractModel
helio.Instrument.prototype = new helio.AbstractModel;
helio.Instrument.prototype.constructor = helio.Instrument;


/**
 * Convenience method to add a list name. Use this method whenever possible.
 * @param instrument the name of the instrument
 * @param instLabel the label of the instrument
 */
helio.Instrument.prototype.addInstrument = function(instrument, instLabel) {
    this.instruments.push(instrument);
    this.config.labels.push(instLabel);
};

/**
 * Convenience method to remove an instrument
 * @param instrument the name of the instrument
 */
helio.Instrument.prototype.removeInstrument = function(instrument) {
    var pos = $.inArray(instrument, this.instruments);
    if (pos >= 0) {
        this.instruments.splice(pos, 1);
        this.config.labels.splice(pos, 1);
    }
};

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
    this.listQueryOptions = [];    // the query options         
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
    this.listQueryOptions.push(new helio.ParamSet(this.taskName, this.listName));
    this.config.labels.push(listLabel);
};

/**
 * Remove a list from the event list set.
 * @param listName the name of the list
 */
helio.EventList.prototype.removeList = function(listName) {
    var pos = $.inArray(listName, this.listNames);
    if (pos >= 0) {
        this.listNames.splice(pos, 1);
        this.listQueryOptions.splice(pos, 1);
        this.config.labels.splice(pos, 1);
    }
};

/**
 * Get the query options for a given list name
 * @param listName the name of the list
 */
helio.EventList.prototype.getQueryOptions = function(listName) {
    var pos = $.inArray(listName, this.listNames);
    if (pos >= 0) {
        return this.listQueryOptions[pos];
    }
    return null;
};

})();
