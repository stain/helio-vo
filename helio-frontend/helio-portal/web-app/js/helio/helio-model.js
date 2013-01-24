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
        throw "Argument 'taskName' must not be null";
    }
};


helio.AbstractModel.prototype.getConfig = function(paramName) {
    throw "subclasses should overload method getConfig.";
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
    this.autoAdjust = autoAdjust != undefined ? autoAdjust : -1;
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
            typeof startTime === 'string' ? moment(startTime, ["YYYY-MM-DDTHH:mm:ss.SSS", "YYYY-MM-DD HH:mm:ss.SSS"]) :
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
            typeof endTime === 'string' ? moment(endTime, ["YYYY-MM-DDTHH:mm:ss.SSS", "YYYY-MM-DD HH:mm:ss.SSS"]) :
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
        if (!tmpStartTime || !tmpEndTime) {
            return;
        }
        
        if (tmpEndTime.diff(tmpStartTime) < this.autoAdjust) {
             
            // we have to adjust
            if (toAdjust == "startTime") {
                this.startTime = moment(this.endTime);  // reset end time
                this.startTime.add("minutes", -this.autoAdjust);
            } else if (toAdjust == "endTime") {
                this.endTime = moment(this.startTime);
                this.endTime.add("minutes", this.autoAdjust);                
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
 * @param {String} name the name of the ParamSet.
 * @param {String} subtype additional type qualifier for a parameter set. Defaults to taskName if not set.
 */
helio.ParamSet = function(taskName, name, subtype) {
    helio.AbstractModel.apply(this, [taskName, name, 'ParamSet']);
    this.subtype = subtype ? subtype : taskName; 
    this.entries = []; // the array holds ParamSetEntry objects.
};

//create ParamSet subclass of AbstractModel
helio.ParamSet.prototype = new helio.AbstractModel;
helio.ParamSet.prototype.constructor = helio.ParamSet;

/**
 * Overwrite or append a new parameter to the paramSet.
 * @param paramName the name of the param.
 * @param operator the operator.
 * @param paramValue the paramValue.
 * @param paramLabel the corresponding label.
 */
helio.ParamSet.prototype.setParamSetEntry = function(paramName, operator, paramValue) {
    // check for an existing entry to update
    for (var i = 0; i < this.entries.length; i++) {
        var entry = this.entries[i];
        if (entry.paramName == paramName) {
            this.entries[i] = new helio.ParamSetEntry(this, paramName, operator, paramValue);
            return;
        }
    }
    // add new entry
    this.entries.push(new helio.ParamSetEntry(this, paramName, operator, paramValue));
};

/**
 * Clear the entries of the paramset
 */
helio.ParamSet.prototype.clear = function() {
    this.entries = [];
};

/**
 * Get the entries
 * @returns {Array} the entries as array of objects.
 */
helio.ParamSet.prototype.getEntries = function() {
	return this.entries;
};

/**
 * Data object to store a single param set entry
 * @param {String} paramSet the parent paramSet of this ParamSetEntry. Must not be null.
 * @param {String} paramName name of the parameter.
 * @param {String} operator the operator.
 * @param {String} paramValue the value of the operation.
 */
helio.ParamSetEntry = function(paramSet, paramName, operator, paramValue) {
    this.type='ParamSetEntry';
    this.paramSet = paramSet;
    this.paramName=paramName;
    if (!helio.config.Operator[operator]) {
        throw "Error: invalid value for operator: " + operator; 
    }
    this.operator=operator;
    this.paramValue=paramValue;
};

/**
 * Get the label for the current paramName
 * @returns the label or the paramName if not found.
 */
helio.ParamSetEntry.prototype.getLabel = function() {
    var config = helio.config[this.paramSet.type][this.paramSet.subtype][this.paramName];
    if (config) {
        return config.label;
    }
    return this.paramName;
};

/**
 * String representation of the the param set
 */
helio.ParamSetEntry.prototype.toString = function() {
    return this.paramName + helio.config.Operator[this.operator].symbol + this.paramValue;
};

/**
 * Make sure to skip the parent paramSet in the JSON representation
 */
helio.ParamSetEntry.prototype.toJSON = function() {
    return {paramName : this.paramName, operator : this.operator, paramValue : this.paramValue };
};

/**
 * Data object to hold an instrument.
 * @param {String} name of the Instrument param. optional.
 * @returns {helio.Instrument}
 */
helio.Instrument = function(taskName, name) {
    helio.AbstractModel.apply(this, [taskName, name, 'Instrument']);
    this.name = name;
    this.instruments = {}; // map of instrumentEntries, use setInstrument and removeInstrument to modify.
};

//create Instrument subclass of AbstractModel
helio.Instrument.prototype = new helio.AbstractModel;
helio.Instrument.prototype.constructor = helio.Instrument;

/**
 * Add or update an instrument to this object.
 * @param instrument the name of the instrument
 */
helio.Instrument.prototype.setInstrument = function(instrument) {
    this.instruments[instrument] = new helio.InstrumentEntry(instrument);
};

/**
 * Remove an instrument from the list
 * @param instrument the name of the instrument
 */
helio.Instrument.prototype.removeInstrument = function(instrument) {
    delete this.instruments[instrument];
};

/**
 * Get the instrumentEntry for a given instrument name.
 * @param instrument the instrument name to get
 * @returns {helio.InstrumentEntry} the InstrumentEntry or undefined if not specified.
 */
helio.Instrument.prototype.getInstrumentEntry = function(instrument) {
    return this.instruments[instrument];
};

/**
 * Get the number of currently defined instrument entries.
 * @returns {Number} the number of instrument entries.
 */
helio.Instrument.prototype.length = function() {
    var len = 0;
    for (var entry in this.instruments) {
        if (this.instruments.hasOwnProperty(entry)) {
            len++;
        }
    }
    return len;
};

/**
 * Object to hold one selected list.
 * @param {String} instrumentName the unique id of the list.
 * @returns {helio.InstrumentEntry} new instance of an entry.
 */
helio.InstrumentEntry = function(instrumentName) {
    this.type = 'InstrumentEntry';
    this.instrumentName = instrumentName;
};

/**
 * Get the label of the current entry.
 * @returns the instrument label.
 */
helio.InstrumentEntry.prototype.getLabel = function() {
    var config = helio.config.Instrument[this.instrumentName];
    if (config) {
        return config.label;
    }
    return this.instrumentName;
};

/**
 * Check if the current instrument is in the PAT.
 * @returns {Boolean} true if the instrument is listed in the PAT. 
 */
helio.InstrumentEntry.prototype.isInPat = function() {
    var config = helio.config.Instrument[this.instrumentName];
    if (config) {
        return config.isInPat;
    }
    return false;
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
 * Data object to hold several event lists.
 * @param {String} name of the event lists param. optional.
 * @returns {helio.EventList}
 */
helio.EventList = function(taskName, name) {
    helio.AbstractModel.apply(this, [taskName, name, 'EventList']);
    this.name = name;
    this.entries = {};   // Map of EventListEntries (use addEntry and removeEntry to modify)
};

//create EventList subclass of AbstractModel
helio.EventList.prototype = new helio.AbstractModel;
helio.EventList.prototype.constructor = helio.EventList;

/**
 * Add a list name and the corresponding where clause.
 * @param listName the name of the list
 * @param whereClause where clause to add as paramset, if nothing an empty paramset will be reated
 */
helio.EventList.prototype.addEntry = function(listName, whereClause) {
	if (!whereClause) {
		whereClause = new helio.ParamSet(this.taskName, "whereClause", listName);
	}
	this.entries[listName] = new helio.EventListEntry(listName, whereClause);
};

/**
 * Remove a list from the event list set.
 * @param listName the name of the list
 */
helio.EventList.prototype.removeEntry = function(listName) {
    delete this.entries[listName];
};

/**
 * Get the query options for a given list name
 * @param listName the name of the list
 * @return {helio.EventListEntry} the helio.EventListEntry object or nothing if not found.
 */
helio.EventList.prototype.getEventListEntry = function(listName) {
    return this.entries[listName];
};

/**
 * Get the number of currently defined entries.
 * @returns {Number} the number of entries.
 */
helio.EventList.prototype.length = function() {
    var len = 0;
    for (var entry in this.entries) {
        if (this.entries.hasOwnProperty(entry)) {
            len++;
        }
    }
    return len;
};


/**
 * Object to hold one selected list.
 * @param {String} listName the unique id of the list.
 * @param {ParamSet} whereClause a param set holding the query parameters.
 * @param {Object} config a configuration object.
 * @returns {helio.EventListEntry} new instance of an entry.
 */
helio.EventListEntry = function(listName, whereClause) {
    this.type = 'EventListEntry';
    this.listName = listName;
    this.whereClause = whereClause;
};

helio.EventListEntry.prototype.getLabel = function() {
    return helio.config.EventList[this.listName].label;
};


})();
