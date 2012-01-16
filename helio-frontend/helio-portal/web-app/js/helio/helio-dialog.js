/**
 * Functions related to different dialogs
 */

/**
 * Put everything in the HELIO name space
 */
(function() {

/**
 * Base class for dialogs.
 * @param {Object} custom options for a dialog. Will be merged with the default options 
 */
helio.AbstractDialog = function(options) {
    this.opts = $.extend({}, helio.AbstractDialog.defaults, options);
};

/**
 * default options.
 */
helio.AbstractDialog.defaults = {
    // some defaults
    modal: true,
    height:530,
    width:700,
    
    // some custom values
    title : null,
    
    // custom callbacks
    open: null,
    focus: null,
    close: null,

    // custom buttons
    buttons: null
};

/**
 * Generic base function to display a dialog
 * @param {function} closeCallback optional call back that is executed after the dialog has been closed.
 */
helio.AbstractDialog.prototype.__showDialog = function(dialogNode, closeCallback) {
    this.opts.close = closeCallback;
    // display the dialog with the configured options.
    dialogNode.dialog(this.opts);
    // set focus on ok button.
    $('.ui-dialog-buttonpane > button:last').focus();
};


/**
 * TimeRangeDialog class
 * @param {helio.TimeRanges} helio.TimeRanges timeRanges the time ranges. 
 * This object is read on init and used to populate the widget. 
 * When the dialog gets closed it is filled with the current values.
 * Note: The object is not touched while the dialog entries are modified.  
 * 
 */
helio.TimeRangeDialog = function(/*helio.TimeRanges*/ timeRanges) {
    helio.AbstractDialog.call(this, this.__dialogConfig());
    this.timeRanges = timeRanges;  // may be null
    this.dialog = undefined; // store the dialog
};

//create TimeRangeDialog as subclass of AbstractDialog
helio.TimeRangeDialog.prototype = new helio.AbstractDialog;
helio.TimeRangeDialog.prototype.constructor = helio.TimeRangeDialog;


// define the static methods of the TimeRangeDialog
/**
 * Add a new timerange widget to the interface
 */
helio.TimeRangeDialog.__addTimeRange = function(/*String*/ start, /*String*/ end) {
    var THIS = this;
    // find the largest index
    var index = 0;
    var timeRanges = $('.input_time_range'); 
    timeRanges.each(function() {
        var id = $(this).attr("id");
        var matcher = /^input_time_range_(\d+)$/.exec(id); 
        if (matcher) {
            index = Math.max(index, matcher[1]);
        }
    });
    index++; // next free index
    
    // create the time range (.html() only encodes the children of an element, thus the <div></div> section)
    var timeRange = $("<div></div>").append($('#input_time_range_tpl').clone()).html();

    timeRange = timeRange.replace(/Range tpl/, "Range " + (timeRanges.size()));
    timeRange = timeRange.replace(/tpl/g, index);

    // add the time range
    $('#input_time_range_list').append(timeRange);
    
    // set values, if any.
    if (start && typeof start === "string") {
        $('#minDate_' + index).val(start);
    }
    if (end && typeof end  === "string") {
        $('#maxDate_' + index).val(end);
    }
    
    helio.TimeRangeDialog.__formatTimeRange(index);
    
    $('#input_time_range_' + index).show(); // unhide the new range.
    $("#input_time_range_remove_" + index).button();
    $("#input_time_range_remove_" + index).click(helio.TimeRangeDialog.__removeTimeRange);
    
    // enable or disable time ranges.
    $(".input_time_range_remove").button( "option", "disabled", timeRanges.size()==1);
};
    
/**
 * remove a time range entry.
 * $(this) refers to the pressed button
 */
helio.TimeRangeDialog.__removeTimeRange = function() {
    // get index to remove (read from id-suffix of pressed button)
    var matcher = /^input_time_range_remove_(\d+)$/.exec(this.id);
    if (!matcher) {
        throw "Unable to find index of " + this.id;
    }
    var index = matcher[1];
    
    // remove timeRange
    $('#input_time_range_'+index).remove();
    
    // get remaining time ranges and adjust numbering
    var counter = 1;
    
    var timeRanges = $('.input_time_range');
    timeRanges.each(function() {
        matcher = /^input_time_range_(\d+)$/.exec(this.id);
        if (matcher) {
            var curIndex = matcher[1];
            if (curIndex != counter) {
                $(this).find(".input_time_range_label").html("Range " + counter);
            }
            counter++;
        }
    });
    
    // disable/enable the delete button
    $(".input_time_range_remove").button( "option", "disabled", timeRanges.size()==2);
};

/**
 * private function to format the date range.
 */
helio.TimeRangeDialog.__formatTimeRange = function(id){
    $('#minDate_'+id).datetimepicker("destroy");
    $('#maxDate_'+id).datetimepicker("destroy");
    
    /**
     * Format for the date picker widget
     */
    var DatePickerFormat = function(){
        return {
            yearRange: '1997:' + (new Date().getFullYear() + 1),
            dateFormat: 'yy-mm-dd',
            changeMonth: true,
            showOn: "both",
            showSecond: true,
            timeFormat: 'hh:mm:ss',
            separator: 'T',
            showButtonPanel: true,
            buttonImageOnly: true,
            buttonImage: "../images/icons/calendar.gif",
            changeYear: true,
            numberOfMonths: 1
        };
    };
    
    /**
     *  formatMinDate and formatMaxDate format the dates
     *  and correct the dates if the user enters a bigger minDate
     *  than maxDate or a smaller maxDate than minDate
     */
    var formatMinDate = new DatePickerFormat();
    formatMinDate.onClose = function(selectedDate) {
        $(this).blur();
        var endDateTextBox = $('#maxDate_' + id);
        if (endDateTextBox.val() != '') {
            var testStartDate = new Date(selectedDate);
            var testEndDate = new Date(endDateTextBox.val());
            if (testStartDate > testEndDate)
                endDateTextBox.val(selectedDate);
        }
        else {
            endDateTextBox.val(selectedDate);
        }
        $.cookie("minDate",$("#minDate_"+id).val(),{
            expires: 30
        });
        $.cookie("maxDate",$("#maxDate_"+id).val(),{
            expires: 30
        });
    };
    
    var formatMaxDate = new DatePickerFormat(); 
    formatMaxDate.onClose = function(selectedDate) {
        $(this).blur();
        var startDateTextBox = $('#minDate_' + id);
        if (startDateTextBox.val() != '') {
            var testStartDate = new Date(startDateTextBox.val());
            var testEndDate = new Date(selectedDate);
            if (testStartDate > testEndDate)
                startDateTextBox.val(selectedDate);
        }
        else {
            startDateTextBox.val(selectedDate);
        }
        $.cookie("minDate",$("#minDate_"+id).val(),{
            expires: 30
        });
        $.cookie("maxDate",$("#maxDate_"+id).val(),{
            expires: 30
        });
    };
    
    $( "#minDate_"+id ).datetimepicker(formatMinDate);
    $( "#maxDate_"+id ).datetimepicker(formatMaxDate);
};

/**
 * Helper function to validate correct date input in date selectors, 
 * returns false if wrong date pair.
 * @index index corresponding to the date range pair (maxdate, mindate)
 */
helio.TimeRangeDialog.__validateTimeRange = function(index) {
    try{
        var maxDate = $("#maxDate_"+index).val();
        if(maxDate.indexOf("T") == -1){//validates time part on its own and autocompletes if missing
            maxDate = maxDate + "T00:00:00";
            $("#maxDate_"+index).val(maxDate);
        }
        var minDate = $("#minDate_"+index).val();
        if(minDate.indexOf("T") == -1){

            minDate = minDate + "T00:00:00";
            $("#minDate_"+index).val(minDate);

        }
        var IsoDate = new RegExp("^(19|20)\\d\\d[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])T([0-9]{2}):([0-9]{2}):([0-9]{2})$");
        
        var matches = IsoDate.exec(minDate);
        if(matches === null){
            return false;
        }
        matches = IsoDate.exec(maxDate);
        if(matches === null){
            return false;
        }

        return true;

    }
    catch(err){
        return false;
    }
};

/**
 * The action to be executed when the Ok button is pressed.
 */
helio.TimeRangeDialog.prototype.__dialogActionOK = function() {
    //Validate date ranges and if and error is found, notify the user and stop thread
    var THIS = this;
    var flag = true;
    var timeRanges = $('.input_time_range');
    
    var timeRangeValues = [];
    timeRanges.each(function() {
        var id = $(this).attr("id");
        var matcher = /^input_time_range_(\d+)$/.exec(id); 
        if (matcher) {
            var index = matcher[1];
            if(!helio.TimeRangeDialog.__validateTimeRange(index)) {
                alert("Time Range " + id + " is not valid. Please check your input.");
                flag= false;
            }
            timeRangeValues.push(new helio.TimeRange($("#minDate_"+index).val(), $("#maxDate_"+index).val()));
        }
    });
    if(!flag)
        return false;
    
    // fill timeRanges with updated values
    if(!this.timeRanges) {
        this.timeRanges = new helio.TimeRanges();
    }
    this.timeRanges.timeRanges = timeRangeValues;
    this.timeRanges.name = $("#time_range_name").val();
        
    $("#timeRangeDialog").dialog( "close" ); // this should trigger the closeCallback
    $("#timeRangeDialog").remove();
};

/**
 * A configuration object for the time range dialog
 */
helio.TimeRangeDialog.prototype.__dialogConfig = function() {
    var THIS = this;
    
    return {
        title : "Select date and time ranges",
        buttons: {
            Help: function(){
                $('#help_overlay h3').text("Time Range Selection");
                $('#help_overlay p').text("Fill out the time ranges you are interested in and click Ok");
                $('#help_overlay').attr('title','Click to close help window').click($.unblockUI);
                $.blockUI({
                    message: $('#help_overlay')
                });
            },
            Cancel: function() {
                $(this).dialog( "close" );
                $(this).remove();
            },
            Ok: function() {
                THIS.__dialogActionOK();
            }
        }
    };
};

/**
 * init the dialog
 * @param {function} closeCallback a call back that is executed after the dialog has been closed.
 */
helio.TimeRangeDialog.prototype.__init = function(closeCallback) {
    var THIS = this;
    
    // attach the current dialog
    $("#dialog_placeholder").replaceWith(this.dialog);
    
    if (this.timeRanges) {
        for (var i = 0; i < this.timeRanges.timeRanges.length; i++) {
            var timeRange = this.timeRanges.timeRanges[i];
            helio.TimeRangeDialog.__addTimeRange(timeRange.start, timeRange.end);
        }
        $("#time_range_name").val(this.timeRanges.name);
    } else { 
        // init the time ranges defined on server side
        var timeRanges = $('.input_time_range');
        timeRanges.each(function() {
            var id = $(this).attr("id");
            var matcher = /^input_time_range_(\d+)$/.exec(id); 
            if (matcher) {
                var index = matcher[1];
                // enable datepicker
                helio.TimeRangeDialog.__formatTimeRange(index);
                // and remove button
                $("#input_time_range_remove_" + index).button({
                    'disabled' : timeRanges.length <= 2  // do not remove the last entry
                });
                $("#input_time_range_remove_" + index).click(helio.TimeRangeDialog.__removeTimeRange);
            }
        });
    }
    $("#input_time_range_button").button();
    $("#input_time_range_button").click(helio.TimeRangeDialog.__addTimeRange);
    
    // show the input dialog
    this.__showDialog($("#timeRangeDialog"), closeCallback);
};

/**
 * Load the dialog from remote and display it.
 * @param {function} closeCallback optional call back that is executed after the dialog has been closed.
 *  
 */
helio.TimeRangeDialog.prototype.show = function(closeCallback) {
    if (this.dialog === undefined) {
        var THIS = this;
        var init = this.timeRanges ? "none" : "last_task"; // should the template be initialized on the server side.
        this.dialog = $('<div id="dialog_placeholder" style="display:none"></div>').load('../dialog/timeRangeDialog?init=' + init +'&taskName=pmFwCme', function() {THIS.__init(closeCallback);});
    } else {
        this.__init(closeCallback);
    }
};

/**
 * ParamSetDialog class
 * @param {helio.ParamSet} helio.ParamSet the param set used to populate the dialog. 
 * When the dialog gets closed it is filled with the current values.
 * Note: The object is not touched while the dialog entries are modified.  
 * 
 */
helio.ParamSetDialog = function(/*helio.ParamSet*/ paramSet) {
    helio.AbstractDialog.call(this, this.__dialogConfig());
    this.paramSet = paramSet;  // may be null
    this.dialog = undefined; // store the dialog
};

//create ParamSetDialog as subclass of AbstractDialog
helio.ParamSetDialog.prototype = new helio.AbstractDialog;
helio.ParamSetDialog.prototype.constructor = helio.ParamSetDialog;

/**
 * The action to be executed when the Ok button is pressed.
 */
helio.ParamSetDialog.prototype.__dialogActionOK = function() {
    var THIS = this;
    
    // fill paramSet with updated values
    if(!this.paramSet) {
        this.paramSet = new helio.ParamSet("pmFwCme");
    }

    // fill the param set object
    var params = new Object();
    params.config = new Object();
    params.data = new Object();
    $(".paramSetEntry").each(function() {
        params["data"][$(this).attr('name')] = $(this).val();
        params["config"][$(this).attr('name')] = {label:$(this).attr('title')};
    });
    
    this.paramSet.params = params;
    this.paramSet.name = $("#param_set_name").val();
        
    $("#paramSetDialog").dialog( "close" ); // this should trigger the closeCallback
    $("#paramSetDialog").remove();
};

/**
 * A configuration object for the param set dialog
 */
helio.ParamSetDialog.prototype.__dialogConfig = function() {
    var THIS = this;
    
    return {
        title : "Select Parameter",
        buttons: {
            Help: function(){
                $('#help_overlay h3').text("Parameter Selection");
                $('#help_overlay p').text("Fill out the parameters you are interested in and click Ok." +
                		"Move your mouse over the parameter title to see some help text.");
                $('#help_overlay').attr('title','Click to close help window').click($.unblockUI);
                $.blockUI({
                    message: $('#help_overlay')
                });
            },
            Cancel: function() {
                $(this).dialog( "close" );
                $(this).remove();
            },
            Ok: function() {
                THIS.__dialogActionOK();
            }
        }
    };
};

/**
 * init the dialog
 * @param {function} closeCallback a call back that is executed after the dialog has been closed.
 */
helio.ParamSetDialog.prototype.__init = function(closeCallback) {
    var THIS = this;
    
    // attach the current dialog
    $("#dialog_placeholder").replaceWith(this.dialog);
    
    if (this.paramSet) {
        for (var paramName in this.paramSet.params) {
            var test = $("input[name='"+paramName+"']");
            $("input[name='"+paramName+"']").val(this.paramSet.params[paramName].value);
        }
        $("#param_set_name").val(this.paramSet.name); 
    } else {
        // just keep the values set on the server side.
    }
    
    // show the input dialog
    this.__showDialog($("#paramSetDialog"), closeCallback);
};

/**
 * Load the dialog from remote and display it.
 * @param {function} closeCallback optional call back that is executed after the dialog has been closed.
 *  
 */
helio.ParamSetDialog.prototype.show = function(closeCallback) {
    if (this.dialog === undefined) {
        var THIS = this;
        var init = "last_task"; // should the template be initialized on the server side.
        this.dialog = $('<div id="dialog_placeholder" style="display:none"></div>').load('../dialog/paramSetDialog?init=' + init +'&taskName=pmFwCme', function() {THIS.__init(closeCallback);});
    } else {
        this.__init(closeCallback);
    }
};

})();