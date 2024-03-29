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

/***************************** Utilities ************************************/
/**
 * Selector widget for one time range.
 * @param {String} startTimeField id of an input field containing the start time.
 * @param {String} endTimeField id of an input field containing the end time.
 */
helio.TimeRangeSelector = function(/*String*/ startTimeField, /*String*/ endTimeField) {
    this.startTimeField = startTimeField;
    this.endTimeField = endTimeField;
    this._init();
};

/**
 * Initialize the time range selector
 */
helio.TimeRangeSelector.prototype._init = function(){
    var THIS = this;
    
    // remove picker, if already set.
    $(this.startTimeField).datetimepicker("destroy");
    $(this.endTimeField).datetimepicker("destroy");
    
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
        var endTimeTextBox = $(THIS.endTimeField);
        if (endTimeTextBox) {
            var timeRange = new helio.TimeRange(null, null, 0);
            timeRange.setEndTime(endTimeTextBox.val());
            timeRange.setStartTime(selectedDate);  // adjust the end date if required.
            endTimeTextBox.val(timeRange.timeAsString()[1]);
        }
    };
    
    var formatMaxDate = new DatePickerFormat(); 
    formatMaxDate.onClose = function(selectedDate) {
        $(this).blur();
        var startTimeTextBox = $(THIS.startTimeField);
        if (startTimeTextBox) {
            var timeRange = new helio.TimeRange(null, null, 0);
            timeRange.setStartTime(startTimeTextBox.val());
            timeRange.setEndTime(selectedDate);   // adjust the start date if required.
            startTimeTextBox.val(timeRange.timeAsString()[0]);
        }
    };
    
    $(this.startTimeField).datetimepicker(formatMinDate);
    $(this.endTimeField).datetimepicker(formatMaxDate);    
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
    
    // 2. click handler for corresponding dialog
    $(".show" + this.typeName + 'Dialog').click(function() {
        var dialogConstructor = 'new helio.' + THIS.typeName + 'Dialog(THIS.task, THIS.taskName, THIS.data)'; 
        var dialog = eval(dialogConstructor);
        
        var okCallback = function() {
            THIS.data = dialog.data;
            THIS.__render(dialog.data);
        };
        dialog.show(okCallback);
    });
    
    // 4. click handler for clear button
    $('.clear' + this.typeName + 'Summary').click(function() {THIS.clear.call(THIS);});
    
    // 5. populate summary
    this.__render(this.data);
    
    // 6. init the droppable
    $(".paramDroppable" + this.typeName).droppable({
        accept: ".cartitemDraggable" + this.droppableName,
        activeClass: "paramDroppableActive",
        hoverClass: "paramDroppableHover",
        drop: function( event, ui ) {
           if( ui.draggable.data('data') != null){
                THIS.data = ui.draggable.data('data');
                THIS.__render(ui.draggable.data('data'));
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
helio.AbstractSummary.prototype.__render = function(data) {
    var summary = this._renderSummary(data);
    if (summary != null) { // (re-)populate the summary section
        $("#text" + this.typeName + "Summary").empty().html(summary);

        $("#img" +  this.typeName + "Summary").attr('src','./images/helio/circle_' + this.typeName + '.png');
        $(".paramDraggable" + this.typeName).draggable("option", "disabled", false );

        this.task.validate();
    } else {
       this.clear();
    }
};

/**
 * Render the content of the summary into a dom node.
 * Subclasses must implement this method.
 * @param data the data object to render.
 * @return DOM node containing the summary or null if no summary is rendered.
 */
helio.AbstractSummary.prototype._renderSummary = function(data) {
    throw "Please overload method '_renderSummary'";
};

/**
 * Clear the current summary section.
 */ 
helio.AbstractSummary.prototype.clear = function() {
    $("#text" + this.typeName +  "Summary").empty().html("");
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
    return this.data != null && (!$.isArray(this.data) || this.data.length > 0);
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
helio.TimeRangeSummary.prototype._renderSummary = function(timeRanges) {
    if (timeRanges) { // (re-)populate the summary section
        var table =$("<table></table>");
        if (timeRanges.name) {
            table.append('<tr><td><b>Name</b> </td><td>' + timeRanges.name + '</td></tr>');
        }
        // loop over the time ranges
        for(var i = 0; i < timeRanges.timeRanges.length; i++) {
            var timeRange =  timeRanges.timeRanges[i];
            table.append('<tr><td><b># ' + (i+1) + '</b>&nbsp;&nbsp;</td><td>' + timeRange.toString() + '</td></tr>');
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


helio.ParamSetSummary.prototype._renderSummary = function(paramSet) {
    if (paramSet) { // (re-)populate the summary section
        var table =$("<table></table>");
        if (paramSet.name) {
            table.append('<tr><td><b>Name</b> </td><td>' + paramSet.name + '</td></tr>');
        }
        // loop over the data
        for(var param in paramSet.getEntries()) {
            var entry = paramSet.getEntries()[param];
            table.append('<tr><td><b>' + entry.getLabel() + '</b>&nbsp;&nbsp;</td><td>' + entry.paramValue + '</td></tr>');
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
    this.data = data;
};

//create EventListSummry as subclass of AbstractSummry
helio.EventListSummary.prototype = new helio.AbstractSummary;
helio.EventListSummary.prototype.constructor = helio.EventListSummary;

/**
 * Remder the summary box.
 * @param eventList the eventlist to render.
 * @returns the summary box.
 */
helio.EventListSummary.prototype._renderSummary = function(eventList) {
    var THIS = this;
    this.data = eventList;
    if (eventList) { // (re-)populate the summary section
        var table =$("<table></table>");
        if (eventList.name) {
            table.append('<tr><td><b>Name</b> ' + eventList.name + '</td></tr>');
        }

        for (var listName in eventList.entries) {
            var listEntry = eventList.getEventListEntry(listName);
            var listLabel = listEntry.getLabel();
            var whereClause = listEntry.whereClause;  // this is a helio.ParamSet
            
            var tr = $("<tr></tr>");
            table.append(tr);
            var td = $('<td></td>');
            tr.append(td);
            td.append('<b>' +listLabel + '</b>');
            
            // add the query options button (aka whereClause)
            var iconStatus = whereClause && whereClause.entries.length > 0  ? "active" : "inactive";
            var options = $('<span style="margin: 0 1px 0 3px; top: 3px; position:relative;">' +
            		'<span class="list-options list-options-' + iconStatus + 
            		'" title="click to select query options"></span></span>');
            options.click(function(listName) {
                return function() {
                    THIS.__queryOptionsDialog.call(THIS, listName);
                    return false;
                };
            }(listName));
            td.append(options);

            // now render the current content of the where clause
            if (whereClause) {
                var optionsText = '';
                for (var i = 0; i < whereClause.getEntries().length; i++) {
                    if (i != 0) {
                        optionsText += ', ';
                    }
                    optionsText += whereClause.getEntries()[i].toString();
                }
                td.append(optionsText);
            }
        }        
        return table;
    } else {
        return null;
    }
};

helio.EventListSummary.prototype.__queryOptionsDialog = function(listName) {
    var THIS = this;
    var listEntry = this.data.getEventListEntry(listName);
    var whereClause = listEntry.whereClause;  // this is a helio.ParamSet
    var paramSetDialog = new helio.ParamSetDialog(this.task, this.taskName, whereClause, listName);
    var okCallback = function() {
        THIS.__render.call(THIS, THIS.data);
    };
    paramSetDialog.show(okCallback);
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

//create InstrumentSummary as subclass of AbstractSummry
helio.InstrumentSummary.prototype = new helio.AbstractSummary;
helio.InstrumentSummary.prototype.constructor = helio.InstrumentSummary;

/**
 * Remder the summary box.
 * @param instrument the instrument to render.
 * @returns the summary box.
 */
helio.InstrumentSummary.prototype._renderSummary = function(instrument) {
    this.data = instrument;
    if (instrument) { // (re-)populate the summary section
        var table =$("<table></table>");
        if (instrument.name) {
            table.append('<tr><td><b>Name</b> ' + instrument.name + '</td></tr>');
        }
        for (var instrumentName in instrument.instruments) {
            var entry = instrument.getInstrumentEntry(instrumentName);
            table.append('<tr><td><b>' + entry.getLabel() + '</b></td></tr>');
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
    this.okCallback = null;  // the callback being called on success.
    this.dialog = null; // store the dialog's html text as retrieved from the remote host.
    
    // prevent duplicate opening of dialog
    this.__active = false;
    
    var THIS = this;
    
    // setup default buttons
    if (!this.opts.buttons) {
        this.opts.buttons = {
            Help: function(){
                $('#help_overlay h3').text(THIS.opts.dialogTitle);
                $('#help_overlay p').text(THIS.opts.helpText);
                $('#help_overlay').attr('title','Click to close help window').click($.unblockUI);
                $.blockUI({
                    message: $('#help_overlay')
                });
            },
            Cancel: function() {
                $(this).dialog( "close" );
                THIS.__active = false;
                $(this).remove();
            },
            Ok: function() {
                var flag = false;
                var error = false;
                try {
                    flag = THIS._updateDataModel.call(THIS);
                } catch (e) {
                    alert("Internal error: " + e);
                    flag = true;
                    error = true;
                }
                if(flag) {
                    // close the dialog
                    $(this).dialog( "close" );
                    THIS.__active = false;
                    $(this).remove();
                    // and notify the registered okCallback
                    if (!error) {
                        THIS.okCallback.call(THIS);
                    }
                }
            }
        };
    }
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
    closeOnEscape: false,
    
    buttons : null
};

/**
 * Load the dialog from remote and display it.
 * @param {function} okCallback optional call back that is executed after the dialog has been closed.
 */
helio.AbstractDialog.prototype.show = function(okCallback) {
    var THIS = this;
    this.okCallback = okCallback;
    if (this.dialog === null) {
        $('#dialog_placeholder').empty();
        
        $.ajax({
            url: this._dialogUrl(), 
            async: false,
            error: function(error) { alert('HELIO Internal Error: Unable to load dialog: ' + error); },
            success: function(data) { 
                THIS.dialog = $('<div id="dialog_placeholder" style="display:none"></div>').append(data);
                THIS.__onLoad.call(THIS);
            },
            complete: function(status) {
            }
        });
    } else {
        this.__onLoad();
    }
};

/**
 * Overload this function to provide the dialog URL to load.
 * @return the URL to call the dialog
 */
helio.AbstractDialog.prototype._dialogUrl = function() {
    throw "Please overload method '_dialogUrl'";
};

/**
 * Overload this function to initialize the dialog after it has been loaded.
 */
helio.AbstractDialog.prototype._init = function() {
    throw "Please overload method '_init'";
};

/**
 * Method that will be called after pressing the ok button but before closing the dialog.
 * The method should do input validation and then update the internal data model.
 * @return {boolean} if true the dialog will be closed, if false it's up to the implementation
 * to show to the user why the model cannot be updated.
 */
helio.AbstractDialog.prototype._updateDataModel = function() {
    throw "Please overload method '_updateDataModel'";
};


/**
 * Called after successfully loading the dialog (or after redisplaying the dialog).
 */
helio.AbstractDialog.prototype.__onLoad = function() {
    // attach the current dialog to the dom initialize it and display the dialog window.
    $("#dialog_placeholder").empty();
    $("#dialog_placeholder").replaceWith(this.dialog);
    this._init();
    this.__showDialog();
};

/**
 * Generic base function to display a dialog
 */
helio.AbstractDialog.prototype.__showDialog = function() {
    // prevent dialog from being shown twice (double click)
    if (this.__active) {
        return;
    }
    this.__active = true;
    
    // display the dialog with the configured options.
    var dlg = $("#dialog_placeholder > :first-child");
    dlg.dialog(this.opts);
    
    // set focus on ok button.
    $('.ui-dialog-buttonset > button:last').focus();
};

//---------------------------------------------------------------------------------- //

/**
 * TimeRangeDialog class
 * @param {helio.Task} task the task this dialog is assigned to.
 * @param {String} taskName the task variant of this dialog.
 * @param {helio.TimeRanges} helio.TimeRanges timeRanges the time ranges.
 * @param {String} message optional help message show to the user. 
 * This object is read on init and used to populate the widget. 
 * When the dialog gets closed it is filled with the current values.
 * Note: The object is not touched while the dialog entries are modified.  
 * 
 */
helio.TimeRangeDialog = function(task, taskName, /*helio.TimeRanges*/ timeRanges, /*String*/ message) {
    helio.AbstractDialog.apply(this, [this.__dialogConfig(), task, taskName, timeRanges]);
    this.message = message;
    this.__tabOrder = [];  // order of the tabs is stored here.
};

//create TimeRangeDialog as subclass of AbstractDialog
helio.TimeRangeDialog.prototype = new helio.AbstractDialog;
helio.TimeRangeDialog.prototype.constructor = helio.TimeRangeDialog;

/**
 * Url to the dialog
 */
helio.TimeRangeDialog.prototype._dialogUrl = function() {
    var init = this.data ? "none" : "last_task"; // should the template be initialized on the server side.
    return './dialog/timeRangeDialog?init=' + init +'&taskName=' + this.taskName;
};

/**
 * init the dialog
 */
helio.TimeRangeDialog.prototype._init = function() {
    var THIS = this;
    if (this.data) {
        // clear current time ranges
        $("tr.input_time_range").filter(":not(:first)").remove();
        
        // readd
        for (var i = 0; i < this.data.timeRanges.length; i++) {
            var timeRange = this.data.timeRanges[i];
            THIS.__addTimeRange.apply(THIS, timeRange.timeAsString());
        }
        $("#time_range_name").val(this.data.name);
    } else {
        // init the existing time range actions
        var timeRanges = $('.input_time_range');
        timeRanges.each(function() {
            var id = $(this).attr("id");
            var matcher = /^input_time_range_(\d+)$/.exec(id); 
            if (matcher) {
                var index = matcher[1];
                THIS.__initTimeRange.call(THIS, index);
            }
        });
    }
    
    // create the "add time range" button
    $("#input_time_range_button").button()
    .click(function() {THIS.__addTimeRange.call(THIS);});
    
    if (this.message) {
        $("#timeRangeDialogMessage").html(this.message);
    } else {
        $("#timeRangeDialogMessage").remove();
    }
};

/**
 * The action to be executed when the Ok button is pressed.
 */
helio.TimeRangeDialog.prototype._updateDataModel = function() {
    var THIS = this;
    //Validate date ranges and if error is found, notify the user and stop thread
    var flag = true;
    var timeRanges = $('.input_time_range');
    
    var timeRangeValues = [];
    timeRanges.each(function() {
        var id = $(this).attr("id");
        var matcher = /^input_time_range_(\d+)$/.exec(id); 
        if (matcher) {
            var index = matcher[1];
            if(!THIS.__validateTimeRange.call(THIS, index)) {
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
    return true;
};

/**
 * A configuration object for the time range dialog
 */
helio.TimeRangeDialog.prototype.__dialogConfig = function() {
    var THIS = this;
    return {
        title : "Select date and time ranges",
        open : function() {
            THIS.__initTabOrder.call(THIS);
            // HACK: hide date time picker on init
            $('#minDate_1').datetimepicker("hide");
        }
    };
};


// define the static methods of the TimeRangeDialog
/**
 * Add a new timerange widget to the interface
 */
helio.TimeRangeDialog.prototype.__addTimeRange = function(/*String*/ startTime, /*String*/ endTime) {

    // find the highest used index
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

    // and show it
    $('#input_time_range_' + index).show(); 
    
    // set values, if any.
    if (startTime && typeof startTime === "string") {
        $('#minDate_' + index).val(startTime);
    }
    if (endTime && typeof endTime  === "string") {
        $('#maxDate_' + index).val(endTime);
    }
    
    // init the newly created time range
    this.__initTimeRange.call(this, index);
};

/**
 * Init a  time range
 * @param index the index of the time range to init
 */
helio.TimeRangeDialog.prototype.__initTimeRange = function(/*int*/ index) {
    var THIS = this;
    
    // enable datepicker
    new helio.TimeRangeSelector('#minDate_' + index, '#maxDate_' + index);
    
    // create the "remove button"
    $("#input_time_range_remove_" + index).button()
    .click(function() {THIS.__removeTimeRange.call(THIS, this, index);});
    $("#input_time_range_inspect_" + index)
    .button()
    .click((function(index) {
        return function() {
            var timeRange = new helio.TimeRange(
                    $('#minDate_' + index).val(), $('#maxDate_' + index).val()
            );
            var dialog = new helio.TimeRangeDetailsDialog(THIS.task, THIS.taskName, timeRange);
            dialog.show(function() {  // callback when the ok button is pressed
                $('#minDate_' + index).val(dialog.data.timeAsString()[0]);
                $('#maxDate_' + index).val(dialog.data.timeAsString()[1]);
            });
        };
    })(index)
    );
    
    // disable/enable the delete buttons
    var timeRanges = $('.input_time_range'); 
    $(".input_time_range_remove").button( "option", "disabled", timeRanges.size()==2);
    
    // setup the tab order buttons
    this.__initTabOrder();
};

/**
 * remove a time range entry.
 * $(this) refers to the pressed button
 */
helio.TimeRangeDialog.prototype.__removeTimeRange = function(caller, index) {
    if ($(caller).attr('disabled') =='disabled') {
        return;
    }
    
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
    this.__initTabOrder();
};


/**
 * Properly set the order when the tab key is pressed.
 * Any old setting will be overwritten by this method.
 */
helio.TimeRangeDialog.prototype.__initTabOrder = function() {
    var THIS = this;
    
    // setup the tab key sequence
    this.__tabOrder = $('#timeRangeDialog input, button').filter(":visible");
//    console.log(this.__tabOrder);
    
    // update the tab-key bindings.
    this.__tabOrder.unbind('keydown').keydown(function(event) {
        if (event.which == 9) { // TAB key
            
            var next = THIS.__nextInputField.call(THIS, this, event.shiftKey ? -1 : 1);
            //console.log(this, next);
            if (next) {
                if ($(this).hasClass("hasDatepicker")) {
                    $(this).datetimepicker("hide");
                }
                this.blur();
                next.focus();
                event.preventDefault();
            }
        }
    });
};

/**
 * Set compute the next field in the tab order sequence.
 * @param {Node} currentField the current field
 * @param {Number} direction: 1 for forward, -1 for backward.
 * @return the next field or null if not found.
 */
helio.TimeRangeDialog.prototype.__nextInputField = function(currentField, direction) {
    var nextInputPos = $.inArray(currentField, this.__tabOrder);
    if (nextInputPos >= 0) {
        if (direction == 1) {
            nextInputPos = nextInputPos == this.__tabOrder.length-1 ? 0 : nextInputPos +1;                 
        } else if (direction == -1) {                
            nextInputPos = nextInputPos == 0 ? this.__tabOrder.length-1 : nextInputPos - 1;                 
        }
        return this.__tabOrder[nextInputPos];
    }
    return null;
};



/**
 * Helper function to validate correct date input in date selectors, 
 * returns false if wrong date pair.
 * @index index corresponding to the date range pair (maxdate, mindate)
 */
helio.TimeRangeDialog.prototype.__validateTimeRange = function(index) {
    try{
        var timeRange = new helio.TimeRange($("#minDate_"+index).val(), $("#maxDate_"+index).val());
        return timeRange.isValid();
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
 * @param {String} listName optional list name.
 * When the dialog gets closed it is filled with the current values.
 * Note: The object is not touched while the dialog entries are modified.  
 * 
 */
helio.ParamSetDialog = function(task, taskName, /*helio.ParamSet*/ paramSet, listName) {
    helio.AbstractDialog.apply(this, [this.__dialogConfig(), task, taskName, paramSet]);
    this.listName = listName;
    this.data = paramSet;
};

//create ParamSetDialog as subclass of AbstractDialog
helio.ParamSetDialog.prototype = new helio.AbstractDialog;
helio.ParamSetDialog.prototype.constructor = helio.ParamSetDialog;

/**
 * Load the dialog URL.
 */
helio.ParamSetDialog.prototype._dialogUrl = function() {
    var init = "last_task"; // should the template be initialized on the server side.
    return './dialog/paramSetDialog?init=' + init +'&taskName=' + this.taskName + 
        (this.listName ? '&listName=' + this.listName : '');
};

/**
 * init the dialog
 */
helio.ParamSetDialog.prototype._init = function() {
    if (this.data) {
        for (var entry in this.data.entries) {
            var paramName = this.data.entries[entry].paramName;
            var input = $("input[name='"+paramName+"']");
            if (input.length) {  // if there is content
                // handle radio buttons
                if (input.attr("type") == 'radio') {
                    $("input[value='"+this.data.entries[entry].paramValue+"']").prop('checked', true);
                } else {
                    // handle input fields
                    input.val(this.data.entries[entry].paramValue);
                }
            } else {
                // try to handle select box, if any
                $("select[name='"+paramName+"'] option[value='"+this.data.entries[entry].paramValue+"']").prop('selected', true);
            }
            // try to set the operator
            $("select[name='op_"+paramName+"'] option[value='"+this.data.entries[entry].operator+"']").prop('selected', true);
        }
        $("#param_set_name").val(this.data.name); 
    } else {
        // just keep the values coming from the server side.
    }
};

/**
 * The action to be executed when the Ok button is pressed.
 */
helio.ParamSetDialog.prototype._updateDataModel = function() {
    var THIS = this;
    // fill paramSet with updated values
    if(!this.data) {
        this.data = new helio.ParamSet(this.taskName, $("#param_set_name").val());
    }

    // fill the param set object
    this.data.clear();
    this.data.taskName = this.data.taskName;
    $(".paramSetEntry").each(function() {
        if ($(this).attr('type') != 'radio' || ($(this).attr("checked") != "undefined" && $(this).attr('checked') == 'checked')) {
            if ($(this).val() && $(this).val() != undefined) {
                var op = $('#op_' +$(this).attr('name'));  // get operator field (has an op_ prefixed)
                var opVal = (op && op.val() && op.val() != '')? op.val() : 'EQUALS';
                THIS.data.setParamSetEntry($(this).attr('name'), opVal, $(this).val(), $(this).attr('title'));                
            }
        }
    });
    
    this.data.name = $("#param_set_name").val();
    return true;
};

/**
 * A configuration object for the param set dialog
 */
helio.ParamSetDialog.prototype.__dialogConfig = function() {
    return {
        title : "Select Parameter",
        dialogTitle : "Parameter Selection",
        helpText : "Fill out the parameters you are interested in and click Ok." +
                   "Move your mouse over the parameter title to see some help text."
    };
};

/**
 * EventListDialog class
 * @param {helio.Task} task the task this dialog is assigned to.
 * @param {String} taskName the task variant of this dialog.
 * @param {helio.EventList} eventList the eventlist assigend with this dialog. 
 * When the dialog gets closed it is filled with the current values.
 * Note: The object is not touched while the dialog entries are modified.  
 * 
 */
helio.EventListDialog = function(task, taskName, /*helio.EventList*/ eventList) {
    helio.AbstractDialog.apply(this, [this.__dialogConfig(), task, taskName, eventList]);
    // create an object to keep the entered data. if ok is pressed and validation passed it will replace this.data.
    this.newdata = eventList ? $.extend(true, {}, eventList) : new helio.EventList(taskName);
    this._idCol = -1;     // number of the column that contains the id (will be set in init()).
    this._labelCol = -1;  // number of the column that contains the label (TODO: remove as not used anymore).
};

//create EventListDialog as subclass of AbstractDialog
helio.EventListDialog.prototype = new helio.AbstractDialog;
helio.EventListDialog.prototype.constructor = helio.EventListDialog;

/**
 * Load the dialog URL.
 */
helio.EventListDialog.prototype._dialogUrl = function() {
    var init = "last_task"; // should the template be initialized on the server side.
    return './dialog/eventListDialog?init=' + init +'&taskName=' + this.taskName;
};

/**
 * init the dialog
 */
helio.EventListDialog.prototype._init = function() {
    var THIS = this;
    
    // 1. init table
    var table = $("#selectTableEventList").dataTable( {
        "bSort": true,
        "bInfo": true,
        "sScrollY": "220px",
        "bPaginate": false,
        "bJQueryUI": true,
        "sScrollX": "500px",
        "sScrollXInner": "99%",
        "sDom": '<"H">t<"F">',
        "aoColumnDefs": [
            { "asSorting": [ ], "aTargets": [ 'type', 'status', 'infoUrl' ] },  // disable sorting for Type and Status
            { 
                "fnRender" : function(o, val) {
                    var img = '<img width="16px" height="16px" class="hecInfo" src="./images/icons/info.png" alt="' + val + '"/>';
                    return img;
                }, "aTargets" : [ 'infoUrl'] 
            }
        ]
    });

    var columnSettings = {
        'Description' : { visible: true, sortable: true}, 
        'From' : {visible: true, sortable: true}, 
        'To' : {visible: true, sortable: true}, 
        'Type' : {visible:true, sortable: false}, 
        'Status' : {visible:true, sortable: false},
        'Info' : {visible:true, sortable: false}
    };
    
    var idColName = 'Name'; // name of the column that contains the identifier for this table
    var labelColName = 'Description'; // name of the column that contains the identifier for this table
    
    var cols = table.fnSettings().aoColumns;
    
    for (var col in cols) {
        var colSetting = columnSettings[cols[col].sTitle];
        var visible = colSetting && colSetting.visible;
        table.fnSetColumnVis( col, visible );
        if(cols[col].sTitle == idColName) {
            this._idCol = col;
        }
        if(cols[col].sTitle == labelColName) {
            this._labelCol = col;
        }
    }
        
    if (this._idCol < 0) throw "HELIO Internal Error: unable to find id col";
    if (this._labelCol < 0) throw "HELIO Internal Error: unable to find label col";

    // 2. enable filters
    $(".checkFilter").change(function(){
    	// remove "never appearing filter text"
    	table.fnFilter("", 1, true);
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
        	// remove "never appearing filter text"
            table.fnFilter("", 1, true);
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
            table.fnFilter("never appearing filter text", 1, true);
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
        
        if ( $(this).hasClass('row_selected') ){
            $(this).removeClass('row_selected');
            THIS.newdata.removeEntry.call(THIS.newdata, id);
            THIS._renderSummaryBox.call(THIS, table);
        } else {
            $(this).addClass('row_selected');
            THIS.newdata.addEntry.call(THIS.newdata, id);
            THIS._renderSummaryBox.call(THIS, table);
        };
    });
    
    // 4a. Add hec info handler
    $('.hecInfo').click(function() {
        var url = $(this).attr('alt');
        var hecInfoWindow = window.open(url, "hecInfo", "width=600,height=800,left=50,top=50,toolbar=0,location=0,menubar=0");
        hecInfoWindow.focus();
        return false;
    });
    
    // 5. init row selection from previous values. 
    this._updateSelection(table);

    // 6. hack to format the headers of the datatables prooperly. not sure why this does not work initially.
    setTimeout(function() {
        table.fnAdjustColumnSizing();
    }, 1);
};

/**
 * The action to be executed when the Ok button is pressed.
 */
helio.EventListDialog.prototype._updateDataModel = function() {
    if (this.newdata.length() == 0) {
        alert("Please select at least one event list");
        return false;
    }
    this.data = this.newdata;
    this.data.name = $("#nameEventList").val();
    return true;
};

/**
 * A configuration object for the EventList dialog
 */
helio.EventListDialog.prototype.__dialogConfig = function() {
    return {
        width : 800,
        title : "Select Event List",
        dialogTitle : "Event List Selection",
        helpText : "Use the filters to select an event list you are interested in and click Ok."
    };
};

/**
 * update the current table selection based on the data model.
 * @param table the table to init
 */
helio.EventListDialog.prototype._updateSelection = function(table) {
    var THIS = this;
    var trNodes = table.fnGetNodes();
    $.each(trNodes, function(index, tr) {
        var listEntry = THIS.newdata.getEventListEntry(table.fnGetData(tr, THIS._idCol));
        if (listEntry) {
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
    
    for (var listName in this.newdata.entries) {
        var listEntry = this.newdata.getEventListEntry(listName);
        var listLabel = listEntry.getLabel();
        var li = $('<li id="sel_' + listName + '"></li>');
        ul.append(li);
        
        var removeButton =
            $('<div style="float:left; height: 16px; width: 16px; margin-right:3px" class="removeList ui-state-default ui-corner-all">' +
              '<span class="ui-icon ui-icon-close"></span>' +
            '</div>');
        
        removeButton.click((function(listName) {
            return function() {
                THIS.newdata.removeEntry.call(THIS.newdata, listName);
                THIS._updateSelection.call(THIS, table); // notify the data table about the change.
            };
        })(listName));
        li.append(removeButton);
        
        // add the query options button (aka whereClause)
        var iconStatus = listEntry.whereClause && listEntry.whereClause.entries.length > 0 ? "active" : "inactive";
        var options = 
        	$('<div style="float:left; height:16px; width:16px; margin-right:3px;"  class="ui-state-default ui-corner-all">' +
        		'<span class="list-options list-options-' + iconStatus + '" title="click to select query options"></span>' + 
        	'</span>');
        
        options.click(function(listName) {
            return function() {
                THIS.__queryOptionsDialog.call(THIS, listName);
                return false;
            };
        }(listName));
        li.append(options);
        
        li.append('<div class="dialog_selection_area_text" >' + listLabel + '<div>');
    }
};

/**
 * Show the param set dialog for a given eventlist.
 * @param listName the name of the list.
 */
helio.EventListDialog.prototype.__queryOptionsDialog = function(listName) {
    var THIS = this;
    var listEntry = this.newdata.getEventListEntry(listName);
    var whereClause = listEntry.whereClause;  // this is a helio.ParamSet
    var paramSetDialog = new helio.ParamSetDialog(this.task, this.taskName, whereClause, listName);
    var okCallback = function() {
        THIS._renderSummaryBox.call(THIS, THIS.newdata);
    };
    paramSetDialog.show(okCallback);
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
 * Load the dialog URL
 */
helio.InstrumentDialog.prototype._dialogUrl = function() {
    var init = "last_task"; // should the template be initialized on the server side.
    return './dialog/instrumentDialog?init=' + init +'&taskName=' + this.taskName;
};

/**
 * init the dialog
 */
helio.InstrumentDialog.prototype._init = function() {
    var THIS = this;
        
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
    var visibleCols = ['Observatory', 'Instrument', 'Label'];
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
        
        if ( $(this).hasClass('row_selected') ){
            $(this).removeClass('row_selected');
            THIS.newdata.removeInstrument.call(THIS.newdata, id);
            THIS._renderSummaryBox.call(THIS, table);
        } else {
            $(this).addClass('row_selected');
            THIS.newdata.setInstrument.call(THIS.newdata, id);
            THIS._renderSummaryBox.call(THIS, table);
        };
    });
    
    // 5. init row selection from previous values. 
    this._updateSelection(table);

    // hack to format the headers of the datatables prooperly. not sure why this does not work initially.
    setTimeout(function() {
        table.fnFilter('');
    }, 10);
};


/**
 * The action to be executed when the Ok button is pressed.
 */
helio.InstrumentDialog.prototype._updateDataModel = function() {
    if (this.newdata.length() == 0) {
        alert("Please select at least one instrument");
        return false;
    }
    this.data = this.newdata;
    this.data.name = $("#nameInstrument").val();
    return true;
};

/**
 * A configuration object for the Instrument dialog
 */
helio.InstrumentDialog.prototype.__dialogConfig = function() {
    return {
        width : 800,
        title : "Select Instrument",
        dialogTitle : "Instrument Selection",
        helpText : "Select an instrument and click Ok."
    };
};

/**
 * update the current table selection based on the data model.
 * @param table the table to init
 */
helio.InstrumentDialog.prototype._updateSelection = function(table) {
    var THIS = this;
    var trNodes = table.fnGetNodes();
    $.each(trNodes, function(index, tr) {
        var entry = THIS.newdata.getInstrumentEntry(table.fnGetData(tr, THIS._idCol));
        if (entry) {
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

    for (var instrumentName in this.newdata.instruments) {
        var entry = this.newdata.getInstrumentEntry(instrumentName);
        var li = $('<li id="sel_' + instrumentName + '"></li>');
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
        })(instrumentName));
        
        li.append(removeButton);
        li.append('<div class="dialog_selection_area_text' + 
                (entry.isInPat() ? '' : ' item_missing') + '">' +
                entry.getLabel() + '<div>');
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
 * Load the dialog URL
 */
helio.ExtractParamsDialog.prototype._dialogUrl = function() {
    var init = "last_task"; // should the template be initialized on the server side.
    return './dialog/paramSetDialog?init=' + init +'&taskName=' + this.taskName;
};

/**
 * init the dialog
 */
helio.ExtractParamsDialog.prototype._init = function() {
    if (this.newdata) {
        $("#param_set_name").val(this.newdata.name); 
    } else {
        // just keep the values set on the server side.
    }
};

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
    

};

/**
 * A configuration object for the param set dialog
 */
helio.ExtractParamsDialog.prototype.__dialogConfig = function() {
    return {
        title : "Select Parameter",
        dialogTitle : "Parameter Selection",
        helpText : "Fill out the parameters you are interested in and click Ok." +
                   "Move your mouse over the parameter title to see some help text."
    };
};


/**
 * Dialog containing the TimeRange inspector. 
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
 * Load the dialog URL
 *  
 */
helio.TimeRangeDetailsDialog.prototype._dialogUrl = function() {
    var time = this.data.timeAsString();
    return './dialog/timeRangeDetailsDialog?startTime=' + time[0] + '&endTime=' + time[1];
};

/**
 * init the dialog
 */
helio.TimeRangeDetailsDialog.prototype._init = function() {
    var THIS = this;
        
    // 1. show tabs
    $("#tabs_time_range_details").tabs();
    
    // 2. init the date picker
    new helio.TimeRangeSelector("#inspectStartTime", "#inspectEndTime");
    
    // 3. attach the add/subtract listeners to the +/- buttons
    $("#inspect_time_range_start_inc").click(function(){
        $("#inspectStartTime").val(THIS._incTime($("#inspectStartTime").val(), 6));
        if ($("#inspectStartTime").val() > $("#inspectEndTime").val()) {
            $("#inspectEndTime").val($("#inspectStartTime").val());
        }
    });
    $("#inspect_time_range_start_dec").click(function(){
        $("#inspectStartTime").val(THIS._incTime($("#inspectStartTime").val(), -6));
    });
    $("#inspect_time_range_end_inc").click(function(){
        $("#inspectEndTime").val(THIS._incTime($("#inspectEndTime").val(), 6));
    });
    $("#inspect_time_range_end_dec").click(function(){
        $("#inspectEndTime").val(THIS._incTime($("#inspectEndTime").val(), -6));
        if ($("#inspectStartTime").val() > $("#inspectEndTime").val()) {
            $("#inspectStartTime").val($("#inspectEndTime").val());
        }
    });
};

/**
 * A configuration object for the dialog
 */
helio.TimeRangeDetailsDialog.prototype.__dialogConfig = function() {
    return {
        width : 800,
        height: 700,
        title : "Time Range inspector",
        dialogTitle : "Date range inspection.",
        helpText : "Adjust the date range on top and load plots to see if it covers what you need. " +
                   "Clicking ok will use the adjusted date range in the calling item."
    };
};

/**
 * The action to be executed when the Ok button is pressed.
 */
helio.TimeRangeDetailsDialog.prototype._updateDataModel = function() {
    this.data.setStartTime($("#inspectStartTime").val());
    this.data.setEndTime($("#inspectEndTime").val());
    return true;
};

/**
 * Increment a given time string by a given amount of hours (may be negative) and return time as UTC string
 * @param {String} time Time as ISO string
 * @param {int} inc Increment in hours.
 * @return adjusted time as ISO string.
 */
helio.TimeRangeDetailsDialog.prototype._incTime = function(/*String*/ time, /*number*/ inc) {
    var date = moment(time, ["YYYY-MM-DDTHH:mm:ss.SSS", "YYYY-MM-DD HH:mm:ss.SSS"]);
    date.add("hours", inc);
    return date.format("YYYY-MM-DDTHH:mm:ss");
};

})();
