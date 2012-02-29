/**
 * Functions related to different dialogs
 */

/**
 * Put everything in the HELIO name space
 */
(function() {

/**
 * Create a dialog to display a submitting message.
 * @param cancelCallback callback method that is called if the user presses cancel, after closing the message.
 * @returns {helio.SubmitMessage}
 */
helio.SubmitMessage = function(cancelCallback) {
    this.cancelCallback = cancelCallback;
};

/**
 * Open the dialog
 */
helio.SubmitMessage.prototype.open = function() {
    var THIS = this;
    // show loading dialog
    $.blockUI({
        message: $("#loading_form"),
        centerY: 0,
        css: {  }
    });
    
    $("#loading_form_cancel_button").button().click(function(){
        $.unblockUI();
        THIS.cancelCallback.call(THIS);
    });
};

/**
 * Close the dialog
 */
helio.SubmitMessage.prototype.close = function() {
    $.unblockUI();
};

/**
 * Create a dialog to display a server side error message.
 * @returns {helio.ErrorMessageDialog}
 */
helio.ErrorMessageDialog = function(title, message) {
    this.title = title;
    this.message = message;
};

/**
 * Open the dialog
 */
helio.ErrorMessageDialog.prototype.open = function() {
    var div = $('<div></div>');
    div.append(this.message);
    div.dialog({
        modal:true,
        title: this.title,
        closeText : 'close',
        height:530,
        width:700,
    });
};

/***************************** DATA SUMMARY AND DIALOG ************************************/

/**
 * Base class for dialog summary.
 * @param {helio.AbstractTask} task the task this object is bound to. 
 * @param {String} taskName the actual name of the task variant. 
 */
helio.AbstractSummary = function(task, taskName, typeName, data) {
    this.task = task;
    this.taskName = taskName;
    this.typeName = typeName; // name of the type: TimeRange | ParamSet | Observatory
    this.droppableName = typeName; // name of the accepted droppable. Can be overwritten in child classes (e.g. ParamSetSummary)
    this.data = data === undefined ? null : data;  // the data stored in this summary object
};

/**
 * Init or re-init a summary section
 */
helio.AbstractSummary.prototype.init = function() {
    var THIS = this;
    
    // 1, format the buttons
    $(".button" + this.typeName).button();
    
    // 2. click handler for time range dialogs
    $(".show" + this.typeName + 'Dialog').click(function() {
        var dialogConstructor = 'new helio.' + THIS.typeName + 'Dialog(THIS.task, THIS.taskName, THIS.data)'; 
        var dialog = eval(dialogConstructor);
        
        var closeCallback = function() {
            THIS.render(dialog.data);
        };
        dialog.show(closeCallback);
    });
    
    // 4. click handler for clear button
    $('.clear' + this.typeName + 'Summary').click(function() {THIS.clear.call(THIS);});
    
    // 5. populate summary
    this.render(this.data);
    
    // 6. init the droppable
    $(".paramDroppable" + this.typeName).droppable({
        accept: ".cartitemDraggable" + this.droppableName,
        activeClass: "paramDroppableActive",
        hoverClass: "paramDroppableHover",
        drop: function( event, ui ) {
           if( ui.draggable.data('data') != null){
                THIS.render(ui.draggable.data('data'));
            }
        }
    });
    
    // 7. and the draggable
    $(".paramDraggable" + this.typeName).draggable({
        // attach data to draggable
        start : function(event, ui) {
            // create a deep copy of the data.
            $(this).data('data', $.extend(true, {}, THIS.data));
        },
        disabled: true,
        revert: "invalid",
        helper: function() {
            return $(this).clone().attr("style", "width:30px; height:30px;");
        },
        zIndex: 1700
    });
};


/**
 * Render the content of the summary text box, init the handlers and 
 * @param data the data to render.
 */
helio.AbstractSummary.prototype.render = function(data) {
    var summary = this.renderSummary(data);
    if (summary != null) { // (re-)populate the summary section
        $("#text" + this.typeName + "Summary").html(summary);

        this.data = data;
        
        $("#img" +  this.typeName + "Summary").attr('src','./images/helio/circle_' + this.typeName + '.png');
        $(".paramDraggable" + this.typeName).draggable("option", "disabled", false );

        this.task.validate();
    } else {
       this.clear();
    }
};

/**
 * Render the content of the summary and return as a dom node.
 * Subclasses must implement this method.
 * @param data the data object to render.
 * @return DOM node containing the summary or null if no summary is rendered.
 */
helio.AbstractSummary.prototype.renderSummary = undefined;

/**
 * Clear the current summary section.
 */ 
helio.AbstractSummary.prototype.clear = function() {
    $("#text" + this.typeName +  "Summary").html("");
    $("#img" + this.typeName + "Summary").attr('src','./images/helio/circle_' + this.typeName + '_grey.png');
    $(".paramDraggable" + this.typeName).draggable("option", "disabled", true);
    this.data = null;
    this.task.validate();
};

/**
 * Return true if there is data for this area.
 * @returns {Boolean} true if there is data around.
 */
helio.AbstractSummary.prototype.isValid = function() {
    return this.data != null;
};

/**
 * TimeRangeSummary class
 * @param {helio.TimeRangeTask} task the task this summary is associated with.  
 * 
 */
helio.TimeRangeSummary = function(task, taskName) {
    helio.AbstractSummary.apply(this, [task, taskName, 'TimeRange', null]);
};

//create TimeRangeDialog as subclass of AbstractDialog
helio.TimeRangeSummary.prototype = new helio.AbstractSummary;
helio.TimeRangeSummary.prototype.constructor = helio.TimeRangeSummary;

/**
 * Render the summary and return a DOM node containing the summary
 * or null if nothing has been rendered
 */
helio.TimeRangeSummary.prototype.renderSummary = function(timeRanges) {
    if (timeRanges) { // (re-)populate the summary section
        var table =$("<table></table>");
        if (timeRanges.name) {
            table.append('<tr><td><b>Name</b> </td><td>' + timeRanges.name + '</td></tr>');
        }
        // loop over the time ranges
        for(var i = 0; i < timeRanges.timeRanges.length; i++) {
            var timeRange =  timeRanges.timeRanges[i];
            table.append('<tr><td><b># ' + (i+1) + '</b>&nbsp;&nbsp;</td><td>' + timeRange.startTime + (timeRange.startTime != timeRange.endTime ? ' - ' + timeRange.endTime : '') + '</td></tr>');
        }
        return table;
    } else {
        return null;
    }
};

/**
 * ParamSetSummary class
 * @param {helio.ParamSetTask} task the task this summary is associated with.  
 * @param {String} taskName the name of the task to send.  
 * 
 */
helio.ParamSetSummary = function(task, taskName, data) {
    helio.AbstractSummary.apply(this, [task, taskName, 'ParamSet', data]);
    this.droppableName = this.typeName + '_' + taskName; 
};

//create ParamSetSummry as subclass of AbstractSummry
helio.ParamSetSummary.prototype = new helio.AbstractSummary;
helio.ParamSetSummary.prototype.constructor = helio.ParamSetSummary;


helio.ParamSetSummary.prototype.renderSummary = function(paramSet) {
    if (paramSet) { // (re-)populate the summary section
        var table =$("<table></table>");
        if (paramSet.name) {
            table.append('<tr><td><b>Name</b> </td><td>' + paramSet.name + '</td></tr>');
        }
        // loop over the data
        for(var param in paramSet.params) {
                table.append('<tr><td><b>' + paramSet.config[param].label + '</b>&nbsp;&nbsp;</td><td>' + paramSet.params[param] + '</td></tr>');
        }
        return table;
    } else {
        return null;
    }
};

/**
 * EventListSummary class
 * @param {helio.EventListTask} task the task this summary is associated with.  
 * @param {String} taskName the name of the task to send.  
 * 
 */
helio.EventListSummary = function(task, taskName, data) {
    helio.AbstractSummary.apply(this, [task, taskName, 'EventList', data]);
};

//create EventListSummry as subclass of AbstractSummry
helio.EventListSummary.prototype = new helio.AbstractSummary;
helio.EventListSummary.prototype.constructor = helio.EventListSummary;

/**
 * Remder the summary box.
 * @param eventList the eventlist to render.
 * @returns the summary box.
 */
helio.EventListSummary.prototype.renderSummary = function(eventList) {
    this.data = eventList;
    if (eventList) { // (re-)populate the summary section
        var table =$("<table></table>");
        if (eventList.name) {
            table.append('<tr><td><b>Name</b> ' + eventList.name + '</td></tr>');
        }

        for (var i = 0; i < eventList.listNames.length; i++) {
            var listLabel = eventList.config.labels[i];
            if (!listLabel) {
                listLabel = eventList.listNames[i];
            }
            table.append('<tr><td><b>' +listLabel + '</b></td></tr>');
        }        
        return table;
    } else {
        return null;
    }
};

/**
 * InstrumentSummary class
 * @param {helio.InstrumentTask} task the task this summary is associated with.  
 * @param {String} taskName the name of the task to send.  
 * 
 */
helio.InstrumentSummary = function(task, taskName) {
    helio.AbstractSummary.apply(this, [task, taskName, 'Instrument']);
};

//create InstrumentSummry as subclass of AbstractSummry
helio.InstrumentSummary.prototype = new helio.AbstractSummary;
helio.InstrumentSummary.prototype.constructor = helio.InstrumentSummary;

/**
 * Remder the summary box.
 * @param instrument the instrument to render.
 * @returns the summary box.
 */
helio.InstrumentSummary.prototype.renderSummary = function(instrument) {
    this.data = instrument;
    if (instrument) { // (re-)populate the summary section
        var table =$("<table></table>");
        if (instrument.name) {
            table.append('<tr><td><b>Name</b> ' + instrument.name + '</td></tr>');
        }
        
        for (var i = 0; i < instrument.instruments.length; i++) {
            var instLabel = instrument.config.labels[i];
            if (!instLabel) {
                instLabel = instrument.instruments[i];
            }
            table.append('<tr><td><b>' +instLabel + '</b></td></tr>');
        }        
        return table;
    } else {
        return null;
    }
};

// ---------------------------------------------------------------------------------- //

/**
 * Base class for dialogs.
 * @param {Object} custom options for a dialog. Will be merged with the default options 
 * @param {helio.Task} task the task this dialog is assigned to.
 * @param {String} taskName the task variant of this dialog.
 * @param {String} data the data stored in this dialog. May be null.
 */
helio.AbstractDialog = function(options, task, taskName, data) {
    this.opts = $.extend({}, helio.AbstractDialog.defaults, options);
    this.task = task;
    this.taskName = taskName;
    this.data = data;
    this.dialog = null; // store the dialog's html text as retrieved from the remote host.
    
    // prevent duplicate opening of dialog
    this.__active = false;
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
 * @param {function} closeCallback optional call back that is executed after the dialog has been closed through an ok button.
 */
helio.AbstractDialog.prototype.__showDialog = function(dialogNode, closeCallback) {
    // prevent dialog from being shown twice (double click)
    var THIS = this;
    if (this.__active) {
        return;
    }
    this.__active = true;
    this.opts.close = function(event, ui) {
        THIS.__active = false;
        $(this).remove();
    };
    
    var okButton = this.opts.buttons.Ok;
    
    if (okButton && closeCallback) {
        this.opts.buttons.Ok = function() {
            okButton.call(THIS);
            closeCallback.call(THIS);
        };
    };
    
    // display the dialog with the configured options.
    dialogNode.dialog(this.opts);
    
    // set focus on ok button.
    $('.ui-dialog-buttonpane > button:last').focus();
};

//---------------------------------------------------------------------------------- //

/**
 * TimeRangeDialog class
 * @param {helio.Task} task the task this dialog is assigned to.
 * @param {String} taskName the task variant of this dialog.
 * @param {helio.TimeRanges} helio.TimeRanges timeRanges the time ranges. 
 * This object is read on init and used to populate the widget. 
 * When the dialog gets closed it is filled with the current values.
 * Note: The object is not touched while the dialog entries are modified.  
 * 
 */
helio.TimeRangeDialog = function(task, taskName, /*helio.TimeRanges*/ timeRanges) {
    helio.AbstractDialog.apply(this, [this.__dialogConfig(), task, taskName, timeRanges]);
};

//create TimeRangeDialog as subclass of AbstractDialog
helio.TimeRangeDialog.prototype = new helio.AbstractDialog;
helio.TimeRangeDialog.prototype.constructor = helio.TimeRangeDialog;

/**
 * The action to be executed when the Ok button is pressed.
 */
helio.TimeRangeDialog.prototype.__dialogActionOK = function() {
    //Validate date ranges and if error is found, notify the user and stop thread
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
            timeRangeValues.push(new helio.TimeRange($("#minDate_"+index).val(), $("#maxDate_"+index).length ? $("#maxDate_"+index).val() : $("#minDate_"+index).val()));
        }
    });
    if(!flag)
        return false;
    
    // fill timeRanges with updated values
    if(!this.data) {
        this.data = new helio.TimeRanges(this.taskName);
    }
    this.data.timeRanges = timeRangeValues;
    this.data.name = $("#time_range_name").val();
        
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
                THIS.__dialogActionOK.call(THIS);
            }
        }
    };
};


/**
 * init the dialog
 * @param {function} closeCallback a call back that is executed after the dialog has been closed.
 */
helio.TimeRangeDialog.prototype.init = function(closeCallback) {
    // attach the current dialog
    $("#dialog_placeholder").replaceWith(this.dialog);
    
    if (this.data) {
        $("tr.input_time_range:not(:first)").remove();
        for (var i = 0; i < this.data.timeRanges.length; i++) {
            var timeRange = this.data.timeRanges[i];
            helio.TimeRangeDialog.__addTimeRange(timeRange.startTime, timeRange.endTime);
        }
        $("#time_range_name").val(this.data.name);
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
    if (this.dialog === null) {
        var THIS = this;
        var init = this.data ? "none" : "last_task"; // should the template be initialized on the server side.
        this.dialog = $('<div id="dialog_placeholder" style="display:none"></div>').load('./dialog/timeRangeDialog?init=' + init +'&taskName=' + THIS.taskName, function() {THIS.init(closeCallback);});
    } else {
        this.init(closeCallback);
    }
};

// define the static methods of the TimeRangeDialog
/**
 * Add a new timerange widget to the interface
 */
helio.TimeRangeDialog.__addTimeRange = function(/*String*/ startTime, /*String*/ endTime) {
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

    timeRange = timeRange.replace(/# tpl/, "# " + (timeRanges.size()));
    timeRange = timeRange.replace(/tpl/g, index);

    // add the time range
    $('#input_time_range_list').append(timeRange);
    
    // set values, if any.
    if (startTime && typeof startTime === "string") {
        $('#minDate_' + index).val(startTime);
    }
    if (endTime && typeof endTime  === "string") {
        $('#maxDate_' + index).val(endTime);
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
    if ($(this).attr('disabled') =='disabled') {
        return;
    }
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
                $(this).find(".input_time_range_label").html("# " + counter);
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
            buttonImage: "./images/icons/calendar.gif",
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
        var endTimeTextBox = $('#maxDate_' + id);
        if (endTimeTextBox && endTimeTextBox.val() != '') {
            var testStartTime = new Date(selectedDate);
            var testEndTime = new Date(endTimeTextBox.val());
            if (testStartTime > testEndTime)
                endTimeTextBox.val(selectedDate);
        }
        else {
            endTimeTextBox.val(selectedDate);
        }
    };
    
    var formatMaxDate = new DatePickerFormat(); 
    formatMaxDate.onClose = function(selectedDate) {
        $(this).blur();
        var startTimeTextBox = $('#minDate_' + id);
        if (startTimeTextBox.val() != '') {
            var testStartTime = new Date(startTimeTextBox.val());
            var testEndTime = new Date(selectedDate);
            if (testStartTime > testEndTime)
                startTimeTextBox.val(selectedDate);
        }
        else {
            startTimeTextBox.val(selectedDate);
        }
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
        if(maxDate && maxDate.indexOf("T") == -1){//validates time part on its own and autocompletes if missing
            maxDate = maxDate + "T00:00:00";
            $("#maxDate_"+index).val(maxDate);
        }
        var minDate = $("#minDate_"+index).val();
        if(minDate && minDate.indexOf("T") == -1){
            minDate = minDate + "T00:00:00";
            $("#minDate_"+index).val(minDate);
        }
        
        var IsoDate = new RegExp("^(19|20)\\d\\d[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])T([0-9]{2}):([0-9]{2}):([0-9]{2})$");
        
        var matches = IsoDate.exec(minDate);
        if(matches === null){
            return false;
        }
        if (maxDate) {
            matches = IsoDate.exec(maxDate);
            if(matches === null){
                return false;
            }
        }

        return true;

    }
    catch(err){
        return false;
    }
};

/**
 * ParamSetDialog class
 * @param {helio.Task} task the task this dialog is assigned to.
 * @param {String} taskName the task variant of this dialog.
 * @param {helio.ParamSet} helio.ParamSet the param set used to populate the dialog. 
 * When the dialog gets closed it is filled with the current values.
 * Note: The object is not touched while the dialog entries are modified.  
 * 
 */
helio.ParamSetDialog = function(task, taskName, /*helio.ParamSet*/ paramSet) {
    helio.AbstractDialog.apply(this, [this.__dialogConfig(), task, taskName, paramSet]);
    this.data = paramSet;
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
    if(!this.data) {
        this.data = new helio.ParamSet(this.taskName);
    }

    // fill the param set object
    this.data.config = this.data.config ? this.data.config : new Object();
    this.data.params = this.data.params ? this.data.params : new Object();
    this.data.taskName = this.data.taskName;
    $(".paramSetEntry").each(function() {
        if ($(this).attr('type') != 'radio' || ($(this).attr("checked") != "undefined" && $(this).attr('checked') == 'checked')) {
            THIS.data["params"][$(this).attr('name')] = $(this).val();
            THIS.data["config"][$(this).attr('name')] = {label:$(this).attr('title')};
        }
    });
    
    this.data.name = $("#param_set_name").val();
        
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
helio.ParamSetDialog.prototype.init = function(closeCallback) {
    // attach the current dialog
    $("#dialog_placeholder").replaceWith(this.dialog);
    
    if (this.data) {
        for (var paramName in this.data.params) {
            var input = $("input[name='"+paramName+"']");
            // handle radio buttons
            if (input.length) {
                if (input.attr("type") == 'radio') {
                    $("input[value='"+this.data.params[paramName]+"']").prop('checked', true);
                } else {
                    input.val(this.data.params[paramName]);
                }
            } else {
                // try to set select box
                $("select[name='"+paramName+"'] option[value='"+this.data.params[paramName]+"']").prop('selected', true);
            }
        }
        $("#param_set_name").val(this.data.name); 
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
    if (this.dialog === null) {
        var THIS = this;
        var init = "last_task"; // should the template be initialized on the server side.
        this.dialog = $('<div id="dialog_placeholder" style="display:none"></div>').load('./dialog/paramSetDialog?init=' + init +'&taskName=' + THIS.taskName, function() {THIS.init(closeCallback);});
    } else {
        this.init(closeCallback);
    }
};


/**
 * EventListDialog class
 * @param {helio.Task} task the task this dialog is assigned to.
 * @param {String} taskName the task variant of this dialog.
 * @param {helio.ParamSet} helio.ParamSet the param set used to populate the dialog. 
 * When the dialog gets closed it is filled with the current values.
 * Note: The object is not touched while the dialog entries are modified.  
 * 
 */
helio.EventListDialog = function(task, taskName, /*helio.ParamSet*/ eventList) {
    helio.AbstractDialog.apply(this, [this.__dialogConfig(), task, taskName, eventList]);
    // create an object to keep the entered data. if ok is pressed and validation passed it will replace this.data.
    this.newdata = eventList ? $.extend(true, {}, eventList) : new helio.EventList(taskName);
    this._idCol = -1;     // number of the column that contains the id (will be set in init()).
    this._labelCol = -1;  // number of the column that contains the label.
};

//create EventListDialog as subclass of AbstractDialog
helio.EventListDialog.prototype = new helio.AbstractDialog;
helio.EventListDialog.prototype.constructor = helio.EventListDialog;

/**
 * The action to be executed when the Ok button is pressed.
 */
helio.EventListDialog.prototype.__dialogActionOK = function() {
    if (this.newdata.listNames.length == 0) {
        alert("Please select at least one event list");
        return false;
    }
    this.data = this.newdata;
    this.data.name = $("#nameEventList").val();
    $("#eventListDialog").dialog( "close" ); // this should trigger the closeCallback
    $("#eventListDialog").remove();
};

/**
 * A configuration object for the EventList dialog
 */
helio.EventListDialog.prototype.__dialogConfig = function() {
    var THIS = this;
    return {
        width : 800,
        title : "Select Event List",
        buttons: {
            Help: function(){
                $('#help_overlay h3').text("Event List Selection");
                $('#help_overlay p').text("Use the filters to select an event list you are interested in and click Ok.");
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
helio.EventListDialog.prototype.init = function(closeCallback) {
    var THIS = this;
    
    // attach the current dialog
    $("#dialog_placeholder").replaceWith(this.dialog);
        
    // 1. init table
    var table = $("#selectTableEventList").dataTable( {
        "bSort": false,
        "bInfo": true,
        "sScrollY": "230px",
        "bPaginate": false,
        "bJQueryUI": true,
        "sScrollX": "500px",
        "sScrollXInner": "100%",
        "sDom": '<"H">t<"F">'
    });

    var visibleCols = ['description', 'timefrom', 'timeto', 'type', 'status'];
    var idColName = 'name'; // name of the column that contains the identifier for this table
    var labelColName = 'description'; // name of the column that contains the identifier for this table
    
    var cols = table.fnSettings().aoColumns;
    
    for (var col in cols) {
        var flag = $.inArray(cols[col].sTitle, visibleCols) >= 0;
        table.fnSetColumnVis( col, flag );
        if(cols[col].sTitle == idColName) {
            this._idCol = col;
        }
        if(cols[col].sTitle == labelColName) {
            this._labelCol = col;
        }
    }
    
    if (this._idCol < 0) throw "Internal Error: unable to find id col";
    if (this._labelCol < 0) throw "Internal Error: unable to find label col";
    
    // 2. enable filters
    $(".checkFilter").change(function(){
        // uncheck "Show all" checkbox
        $("#checkAll").removeAttr("checked");
        
        table.fnFilter("", 15, true);
        
        var checkboxColumn = $(this).attr("column");
        var filter_expression = "";
        
        var eventCounter = 0;
        var locationCounter = 0;
        var observationCounter = 0;
        
        // clear filterText <td>
        $("#filterText").html("");
        $("#filterText").hide();

        // filters the table and displays filter text
        $("input:checked").each(function(){
            // ignore filterText when obs. type is both otherwise display default filter text
            if (eventCounter == 0 && locationCounter == 0 && observationCounter == 0){
                if ($(this).attr("title") == "Both") {
                    $("#filterText").html("All flare lists are shown.");
                }
                else {
                    $("#filterText").html($("#filterText").html() + "Show flare lists WHERE ");
                }
            }
            
            // display all filter criteria connected with AND's
            if($(this).hasClass("event")) {
                if (eventCounter == 0) {
                    $("#filterText").html($("#filterText").html() + "Event is " + $(this).attr("name"));
                }
                else {
                    $("#filterText").html($("#filterText").html() + " AND " + $(this).attr("name"));
                }
                eventCounter++;
            } else if ($(this).hasClass("location")) {
                if (locationCounter == 0) {
                    // only display first AND if no event filters are set
                    if (eventCounter == 0) {
                        $("#filterText").html($("#filterText").html() + "Location is " + $(this).attr("name"));
                    } else {
                        $("#filterText").html($("#filterText").html() + " <b> AND</b> Location is " + $(this).attr("name"));
                    }
                } else {
                    $("#filterText").html($("#filterText").html() + " AND " + $(this).attr("title"));
                }
                locationCounter++;
            } else if ($(this).hasClass("observation")) {
                if ($(this).attr("title") != "Both") {
                    if (observationCounter == 0) {
                        // only display first AND if no event filters and no location filters are set
                        if (eventCounter == 0 && locationCounter == 0) {
                            $("#filterText").html($("#filterText").html() + "Obs. Type is " + $(this).attr("title"));
                        } else {
                            $("#filterText").html($("#filterText").html() + " <b> AND</b> Obs. Type is " + $(this).attr("title"));
                        }
                    } else {
                        $("#filterText").html($("#filterText").html() + " <b>AND " + $(this).attr("title"));
                    }
                }
                observationCounter++;
            }
            
            // create filter expression
            if($(this).attr("column") == checkboxColumn) {
                filter_expression = (filter_expression == "" ? "" : (filter_expression + "|")) + "(" + $(this).attr("value") + ")";
            }
        });
        
        // creates the new filtered table
        table.fnFilter(filter_expression, checkboxColumn, true);
        $("#filterText").delay(500).fadeIn();
    });
    
    // the check all option
    $("#checkAll").change(function(){
        $("#filterText").hide();
        if ($(this).attr("checked")) {
            table.fnFilter("", 15, true);
            $(".checkFilter").each(function(){
                // uncheck all filter checkboxes
                $(this).removeAttr("checked");
                // remove all filters from dataTable
                table.fnFilter("", $(this).attr("column"), true);
            });
            
            $("#obsBoth").attr("checked", "checked");
            $("#filterText").html("All flare lists are shown.");
        }
        else {
            table.fnFilter("never appearing filter text", 15, true);
            $("#filterText").html("No flare lists are shown.");
        }
        $("#filterText").delay(500).fadeIn();
    });
    
    // handle the radio buttons
    $("input:radio").change(function(){
        $("#filterText").hide();
        var checkboxColumn = $(this).attr("column");
        var filter_expression = "";
        
        $("input:checked").each(function(){
            if($(this).attr("column") == checkboxColumn)
                filter_expression = (filter_expression == "" ? "" : (filter_expression + "|")) + "(" + $(this).attr("value") + ")";
        });

        table.fnFilter(filter_expression, checkboxColumn, true);
        $("#filterText").delay(500).fadeIn();
    });
    
    // the textbox filters
    $("#input_filter").keyup(function(){
        table.fnFilter($(this).val());
    });

    // 3. render the content of the summary box
    this._renderSummaryBox(table);
    
     // 4. the row selection listener
    $('#selectTableEventList tr').click( function() {
        var id = table.fnGetData(this, THIS._idCol);    // id of the selected event list
        var label = table.fnGetData(this, THIS._labelCol);
        
        if ( $(this).hasClass('row_selected') ){
            $(this).removeClass('row_selected');
            THIS.newdata.removeList.call(THIS.newdata, id);
            THIS._renderSummaryBox.call(THIS, table);
        } else {
            $(this).addClass('row_selected');
            THIS.newdata.addList.apply(THIS.newdata, [id, label]);
            THIS._renderSummaryBox.call(THIS, table);
        };
    });
    
    // 1. init row selection from previous values. 
    this._updateSelection(table);

    // hack to format the headers of the datatables prooperly. not sure why this does not work initially.
    setTimeout(function() {
        $('#checkAll').click();
        $('#checkAll').click();
    }, 10);
    
    // show the input dialog
    this.__showDialog($("#eventListDialog"), closeCallback);
};

/**
 * update the current table selection based on the data model.
 * @param table the table to init
 */
helio.EventListDialog.prototype._updateSelection = function(table) {
    var THIS = this;
    var trNodes = table.fnGetNodes();
    $.each(trNodes, function(index, tr) {
        var pos = $.inArray(table.fnGetData(tr, THIS._idCol), THIS.newdata.listNames);
        var flag = pos >= 0;
        if (flag) {
            if (!THIS.newdata.config.labels[pos]) {
                var label = table.fnGetData(tr, THIS._labelCol);
                THIS.newdata.config.labels[pos] = label;
            }
            $(tr).addClass('row_selected');                
        } else {
            $(tr).removeClass('row_selected');                
        }
    });
    $("#nameEventList").val(this.newdata.name);
    
    this._renderSummaryBox(table);
};

/**
 * (Re-)render the content of the summary box within the dialog
 */
helio.EventListDialog.prototype._renderSummaryBox = function(table) {
    var THIS = this;
    $("#summaryEventList").empty();
    var ul = $("<ul></ul>");
    $("#summaryEventList").append(ul);
    
    for (var i = 0; i < this.newdata.listNames.length; i++) {
        var listName = this.newdata.listNames[i];
        var listLabel = this.newdata.config.labels[i];
        var li = $('<li id="sel_' + listName + '"></li>');
        ul.append(li);
        
        var removeButton =
            $('<div style="float:left; height: 16px; width: 16px; margin-right:3px" class="removeList ui-state-default ui-corner-all">' +
              '<span class="ui-icon ui-icon-close"></span>' +
            '</div>');
        
        removeButton.click((function(listName) {
            return function() {
                THIS.newdata.removeList.call(THIS.newdata, listName);
                THIS._updateSelection.call(THIS, table); // notify the data table about the change.
            };
        })(listName));
        
        li.append(removeButton);
        li.append('<div class="dialog_selection_area_text" >' + listLabel + '<div>');
    }
};


/**
 * Load the dialog from remote and display it.
 * @param {function} closeCallback optional call back that is executed after the dialog has been closed.
 *  
 */
helio.EventListDialog.prototype.show = function(closeCallback) {
    if (this.dialog === null) {
        var THIS = this;
        var init = "last_task"; // should the template be initialized on the server side.
        this.dialog = $('<div id="dialog_placeholder" style="display:none"></div>').load('./dialog/eventListDialog?init=' + init +'&taskName=' + THIS.taskName, function() {THIS.init(closeCallback);});
    } else {
        this.init(closeCallback);
    }
};

/**
 * InstrumentDialog class
 * @param {helio.Task} task the task this dialog is assigned to.
 * @param {String} taskName the task variant of this dialog.
 * @param {helio.Instrument} instrument the instrument used to populate the dialog. 
 * When the dialog gets closed it is filled with the current values.
 * Note: The object is not touched while the dialog entries are modified.  
 * 
 */
helio.InstrumentDialog = function(task, taskName, /*helio.ParamSet*/ instrument) {
    helio.AbstractDialog.apply(this, [this.__dialogConfig(), task, taskName, instrument]);
    // create an object to keep the entered data. if ok is pressed and validation passed it will replace this.data.
    this.newdata = instrument ? $.extend(true, {}, instrument) : new helio.Instrument(taskName);
    this._idCol = -1;     // number of the column that contains the id (will be set in init()).
    this._labelCol = -1;  // number of the column that contains the label.
};



//create InstrumentDialog as subclass of AbstractDialog
helio.InstrumentDialog.prototype = new helio.AbstractDialog;
helio.InstrumentDialog.prototype.constructor = helio.InstrumentDialog;

/**
 * The action to be executed when the Ok button is pressed.
 */
helio.InstrumentDialog.prototype.__dialogActionOK = function() {
    if (this.newdata.instruments.length == 0) {
        alert("Please select at least one instrument");
        return false;
    }
    this.data = this.newdata;
    this.data.name = $("#nameInstrument").val();
    $("#instrumentDialog").dialog( "close" ); // this should trigger the closeCallback
    $("#instrumentDialog").remove();
};

/**
 * A configuration object for the Instrument dialog
 */
helio.InstrumentDialog.prototype.__dialogConfig = function() {
    var THIS = this;
    return {
        width : 800,
        title : "Select Instrument",
        buttons: {
            Help: function(){
                $('#help_overlay h3').text("Instrument Selection");
                $('#help_overlay p').text("Select an instrument and click Ok.");
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
helio.InstrumentDialog.prototype.init = function(closeCallback) {
    var THIS = this;
    
    // attach the current dialog
    $("#dialog_placeholder").replaceWith(this.dialog);
        
    // 1. init table
    var table = $("#selectInstrument").dataTable( {
        "bSort": false,
        "bInfo": true,
        "sScrollY": "230px",
        "bPaginate": false,
        "bJQueryUI": true,
        "sScrollX": "300px",
        "sScrollXInner": "100%",
        "sDom": '<"H">t<"F">'
    });
    var visibleCols = ['Label', 'Internal Name'];
    var idColName = 'Internal Name'; // name of the column that contains the identifier for this table
    var labelColName = 'Label'; // name of the column that contains the identifier for this table
    
    var cols = table.fnSettings().aoColumns;
    for (var col in cols) {
        var flag = $.inArray(cols[col].sTitle, visibleCols) >= 0;
        table.fnSetColumnVis( col, flag );
        if(cols[col].sTitle == idColName) {
            this._idCol = col;
        }
        if(cols[col].sTitle == labelColName) {
            this._labelCol = col;
        }
    }
    
    if (this._idCol < 0) throw "Internal Error: unable to find id col";
    if (this._labelCol < 0) throw "Internal Error: unable to find label col";
    
    // 2. enable filters
    // the textbox filters
    $("#input_filter").keyup(function(){
        table.fnFilter($(this).val());
    });

    // 3. render the content of the summary box
    this._renderSummaryBox(table);
    
     // 4. the row selection listener
    $('#selectInstrument tr').click( function() {
        var id = table.fnGetData(this, THIS._idCol);    // id of the selected instrument list
        var label = table.fnGetData(this, THIS._labelCol);
        
        if ( $(this).hasClass('row_selected') ){
            $(this).removeClass('row_selected');
            THIS.newdata.removeInstrument.call(THIS.newdata, id);
            THIS._renderSummaryBox.call(THIS, table);
        } else {
            $(this).addClass('row_selected');
            THIS.newdata.addInstrument.apply(THIS.newdata, [id, label]);
            THIS._renderSummaryBox.call(THIS, table);
        };
    });
    
    // 5. init row selection from previous values. 
    this._updateSelection(table);

    // hack to format the headers of the datatables prooperly. not sure why this does not work initially.
    setTimeout(function() {
        table.fnFilter('');
    }, 10);
    
    // show the input dialog
    this.__showDialog($("#instrumentDialog"), closeCallback);
};

/**
 * update the current table selection based on the data model.
 * @param table the table to init
 */
helio.InstrumentDialog.prototype._updateSelection = function(table) {
    var THIS = this;
    var trNodes = table.fnGetNodes();
    $.each(trNodes, function(index, tr) {
        var pos = $.inArray(table.fnGetData(tr, THIS._idCol), THIS.newdata.instruments);
        var flag = pos >= 0;
        if (flag) {
            if (!THIS.newdata.config.labels[pos]) {
                var label = table.fnGetData(tr, THIS._labelCol);
                THIS.newdata.config.labels[pos] = label;
            }
            $(tr).addClass('row_selected');                
        } else {
            $(tr).removeClass('row_selected');                
        }
    });
    $("#nameInstrument").val(this.newdata.name);
    
    this._renderSummaryBox(table);
};

/**
 * (Re-)render the content of the summary box within the dialog
 */
helio.InstrumentDialog.prototype._renderSummaryBox = function(table) {
    var THIS = this;
    $("#summaryInstrument").empty();
    var ul = $("<ul></ul>");
    $("#summaryInstrument").append(ul);
    
    for (var i = 0; i < this.newdata.instruments.length; i++) {
        var instrument = this.newdata.instruments[i];
        var instLabel = this.newdata.config.labels[i];
        var li = $('<li id="sel_' + instrument + '"></li>');
        ul.append(li);
        
        var removeButton =
            $('<div style="float:left; height: 16px; width: 16px; margin-right:3px" class="removeInst ui-state-default ui-corner-all">' +
              '<span class="ui-icon ui-icon-close"></span>' +
            '</div>');
        
        removeButton.click((function(instrument) {
            return function() {
                THIS.newdata.removeInstrument.call(THIS.newdata, instrument);
                THIS._updateSelection.call(THIS, table); // notify the data table about the change.
            };
        })(instrument));
        
        li.append(removeButton);
        li.append('<div class="dialog_selection_area_text" >' + instLabel + '<div>');
    }
};

/**
 * Load the dialog from remote and display it.
 * @param {function} closeCallback optional call back that is executed after the dialog has been closed.
 *  
 */
helio.InstrumentDialog.prototype.show = function(closeCallback) {
    if (this.dialog === null) {
        var THIS = this;
        var init = "last_task"; // should the template be initialized on the server side.
        this.dialog = $('<div id="dialog_placeholder" style="display:none"></div>').load('./dialog/instrumentDialog?init=' + init +'&taskName=' + THIS.taskName, function() {THIS.init(closeCallback);});
    } else {
        this.init(closeCallback);
    }
};

/**
 * ExtractParamsDialog class
 * @param {helio.Task} task the task this dialog is assigned to.
 * @param {String} taskName the task variant of this dialog.
 * @param {String} tableId id of the table to extract params from. 
 * @param {int} tableNr nr of the table within the votable to use. 
 * When the dialog gets closed it is filled with the current values.
 * Note: The object is not touched while the dialog entries are modified.  
 * 
 */
helio.ExtractParamsDialog = function(task, taskName, tableId, tableNr) {
    helio.AbstractDialog.apply(this, [this.__dialogConfig(), task, taskName]);
    this.task = task;
    this.taskName = taskName;
    this.tableId = tableId;  // must not be empty
    this.tableNr = tableNr;  // must not be empty
};

//create ExtractParamsDialog as subclass of AbstractDialog
helio.ExtractParamsDialog.prototype = new helio.AbstractDialog;
helio.ExtractParamsDialog.prototype.constructor = helio.ExtractParamsDialog;

/**
 * Action to be executed when the Ok button is pressed.
 */
helio.ExtractParamsDialog.prototype._extractParams = function() {
    // get name label
    
    // get active tab
    
    switch (activeTab) {
    case 'instrument':
            // validate selected params
            if ($('.param_mapping').count() == 0) ;
            
            // create model object
            new helio.Instruments();
        break;

    default:
        break;
    }
    
    
    // fill the param set object
        
    $("#paramSetDialog").dialog( "close" ); // this should trigger the closeCallback
    $("#paramSetDialog").remove();
};

/**
 * A configuration object for the param set dialog
 */
helio.ExtractParamsDialog.prototype.__dialogConfig = function() {
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
helio.ExtractParamsDialog.prototype.init = function(closeCallback) {
    
    // attach the current dialog
    $("#dialog_placeholder").replaceWith(this.dialog);
    
    if (this.newdata) {
        $("#param_set_name").val(this.newdata.name); 
    } else {
        // just keep the values set on the server side.
    }
    
    // show the input dialog
    this.__showDialog($("#paramSetDialog"), closeCallback);
};

/**
 * Load the dialog from remote and display it.
 * @param {function} closeCallback optional call back that is executed after the dialog has been closed.
 */
helio.ExtractParamsDialog.prototype.show = function(closeCallback) {
    if (this.dialog === null) {
        var THIS = this;
        var init = "last_task"; // should the template be initialized on the server side.
        this.dialog = $('<div id="dialog_placeholder" style="display:none"></div>').load('./dialog/paramSetDialog?init=' + init +'&taskName=' + THIS.taskName, function() {THIS.init(closeCallback);});
    } else {
        this.init(closeCallback);
    }
};

/**
 * TimeRangeDetailsDialog class
 * @param {helio.Task} task the task this dialog is assigned to.
 * @param {String} taskName the task variant of this dialog.
 * @param {helio.TimeRange} helio.TimeRange the time range to use in the dialog. 
 */
helio.TimeRangeDetailsDialog = function(task, taskName, /*helio.TimeRange*/ timeRange) {
    helio.AbstractDialog.apply(this, [this.__dialogConfig(), task, taskName, timeRange]);
    this.data = timeRange;
};

//create TimeRangeDetailsDialog as subclass of AbstractDialog
helio.TimeRangeDetailsDialog.prototype = new helio.AbstractDialog;
helio.TimeRangeDetailsDialog.prototype.constructor = helio.TimeRangeDetailsDialog;

/**
 * A configuration object for the dialog
 */
helio.TimeRangeDetailsDialog.prototype.__dialogConfig = function() {    
    return {
        title : "Details for a time range",
        buttons: {
            Ok: function() {
                $(this).dialog( "close" );
                $(this).remove();
            },
        }
    };
};

/**
 * init the dialog
 * @param {function} closeCallback a call back that is executed after the dialog has been closed.
 */
helio.TimeRangeDetailsDialog.prototype.init = function(closeCallback) {
    // attach the current dialog
    $("#dialog_placeholder").replaceWith(this.dialog);
    
    // show the input dialog
    this.__showDialog($("#timeRangeDetailsDialog"), closeCallback);
};

/**
 * Load the dialog from remote and display it.
 * @param {function} closeCallback optional call back that is executed after the dialog has been closed.
 *  
 */
helio.TimeRangeDetailsDialog.prototype.show = function(closeCallback) {
    if (this.dialog === null) {
        var THIS = this;
        this.dialog = $('<div id="dialog_placeholder" style="display:none"></div>').load('./dialog/timeRangeDetailsDialog?startTime=' + this.data.startTime + '&endTime=' + this.data.endTime, function() {THIS.init(closeCallback);});
    } else {
        this.init(closeCallback);
    }
};

})();
