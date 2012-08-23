/**
 * Object related to tasks and to dialog summaries.
 */
(function() {
/******************************** TASKS ************************************/
/**
 * Base class for a task
 * @param {String} taskName name of the task. 
 * @param {String} url the url to use for this specific task. 
 */
helio.AbstractTask = function(taskName, url) {
    this.taskName = taskName;
    this.url = url;
    this.summaries = {};
    this.result = undefined;
};
    
/**
 * Init or re-init a task
 */
helio.AbstractTask.prototype.init = function() {
    // 1. init the collapsible sections
    $.collapsible(".queryHeader", "group1");
    
    // 2, format the buttons
    $(".buttonPerformQuery").button();

    // 3. init the summary sections
    for(summary in this.summaries) {
        this.summaries[summary].init.call(this.summaries[summary]);
    }
    
    // 4. init the result area
    if (this.result) {
        var resultArea = this._cleanResultArea();
        resultArea.html(this.result.data);
        this.result.init.call(this.result);
    }
    var THIS = this;
    $("#result_summary_select").click(function() {THIS._submitQuery.call(THIS);});
    // 5 enable submit button if required
    this.validate();
};

/**
 * validate the input and enable/disable submit button.
 * The function is called whenever the data model is changed.
 * This method calls method isValid() which needs to be overloaded.
 */
helio.AbstractTask.prototype.validate = function() {
    // enable/disable the run button.
    $("#result_summary_select").button( "option", "disabled", !this._isValid());
};

/**
 * Returns true if the task content is valid and can be submitted to the 
 * server. 
 * @return {Boolean} true if valid. 
 */
helio.AbstractTask.prototype._isValid = function() {
    var flag = true;
    for(summary in this.summaries) {
        flag &= this.summaries[summary] && this.summaries[summary].isValid.call(this.summaries[summary]);
    }
    return flag;
};

/**
 * Lookup the first summary with  a given type
 * @param typeName
 * @returns
 */
helio.AbstractTask.prototype.getSummaryByTypeName = function(typeName) {
    for(summary in this.summaries) {
        if (typeName == summary.typeName) {
            return summary;
        }
        return null;
    }
};


/**
 * Submit the query to the server and handle result appropriately
 */
helio.AbstractTask.prototype._submitQuery = function() {
    var THIS = this;
    var bindings = {
        "taskName" : this.taskName,
        "inputParams" : {},
    };
    
    for(summary in this.summaries) {
        bindings.inputParams[summary] = this.summaries[summary].data; 
    }
    
    var modelParam = JSON.stringify(bindings);

    // ajax control object
    var xhr = undefined;

    // init submit dialog
    var submitDialog = new helio.SubmitMessage(function() {
        if(xhr) xhr.abort();
        // clear result area
        THIS._cleanResultArea.call(THIS);
    });
    
    // call the propModel
    xhr = jQuery.ajax({
        url: this.url,
        type: "POST",
        data: {bindings : modelParam},
        error : function(jqXHR, textStatus, errorThrown) {
            submitDialog.close();
            if (textStatus == "abort") {
                // nothing to do
                return;
            }
            
            var errorMessage = $('<div class="errorMessage"></div>');
            var status = jqXHR.getResponseHeader('status');
            if (!status) status = textStatus;

            errorMessage.append('Message: ' + status + ' (<span id="details_link" style="text-decoration:underline; cursor:pointer">click for details</span>)');
            $('#perform_query_text').empty().html(errorMessage);
            
            $('#details_link').click(function() {
                new helio.ErrorMessageDialog(errorThrown, jqXHR.responseText).open();
            });
            THIS._clearResult.call(THIS); 
        },
        success: function(data, textStatus, jqXHR) { 
            submitDialog.close(); 
            var status = jqXHR.getResponseHeader('status');
            if (!status) status = "Data successfully loaded";
            $('#perform_query_text').empty().html(status);
            
            THIS._handleResult.call(THIS, data); 
        },
        complete: function(jqXHR, textStatus) {
            submitDialog.close(); // if not already closed
            switch (textStatus) {
            case "success":
                break;
            case "error":
                THIS._cleanResultArea.call(THIS);
                break;
            case "abort":
                $('#perform_query_text').empty().html("Cancelled by user");
                THIS._cleanResultArea.call(THIS);
                break;
            case "timeout":
                $('#perform_query_text').empty().html("Your request timed out");
                THIS._clearResult.call(THIS); 
            case "notmodified":
            case "parsererror":
            default:
                $('#perform_query_text').empty().html("Unknown status: " + textStatus);
                THIS._cleanResultArea.call(THIS);
            }
        } 
    });
    
     // display the submit dialog
    submitDialog.open();
};

/**
 * Add the result to the result_area and init handlers.
 * @param data the data to add.
 */
helio.AbstractTask.prototype._handleResult = function(data) {
    var resultArea = this._cleanResultArea.call();
    resultArea.html(data);
    this.result = new helio.TaskResult(this, this.taskName, data);
    this.result.init();
};

/**
 * Clear result area
 */
helio.AbstractTask.prototype._clearResult = function(data) {
    this._cleanResultArea();
    this.result = new helio.TaskResult(this, this.taskName, data);
    this.result.init();
};

/**
 * Remove anything from the result area before it gets populated with a new table.
 * This method makes sure to remove any registered handlers from the result area.
 * @return the empty result area handler
 */
helio.AbstractTask.prototype._cleanResultArea = function() {
    $('#task_result_area .resultTable').each(function() {
       if ($(this).hasClass('dataTable') ) {
          var table = $(this).dataTable();
          if (table) {
            table.fnDestroy();
          }
       }
    });
    return $('#task_result_area').empty();
};


/**
 * Create a PropagationModelTask
 * @param {String} taskName name of the actual implementation of the task.  
 * 
 */
helio.PropagationModelTask = function(taskName) {
    helio.AbstractTask.call(this, taskName, "./processing/propagationModel");
    this.summaries["timeRanges"] = new helio.TimeRangeSummary(this, this.taskName);
    this.summaries["paramSet"] = new helio.ParamSetSummary(this, this.taskName);
};

//create PropagationModelTask as subclass of AbstractTask
helio.PropagationModelTask.prototype = new helio.AbstractTask;
helio.PropagationModelTask.prototype.constructor = helio.PropagationModelTask;

/**
 * Create a PlotTask
 * @param {String} taskName name of the actual implementation of the task.  
 * 
 */
helio.PlotTask = function(taskName) {
    helio.AbstractTask.call(this, taskName, "./plot/plot");
    this.summaries["timeRanges"] = new helio.TimeRangeSummary(this, this.taskName);
    
    if (taskName == 'parkerplot' || taskName == 'goesplot') {
        this.summaries["paramSet"] = new helio.ParamSetSummary(this, this.taskName);
    }
};

//create PlotTask as subclass of AbstractTask
helio.PlotTask.prototype = new helio.AbstractTask;
helio.PlotTask.prototype.constructor = helio.PlotTask;

/**
 * Create a EventListTask
 * @param {String} taskName name of the actual implementation of the task.  
 * 
 */
helio.EventListTask = function(taskName) {
    helio.AbstractTask.call(this, taskName, "./catalog/hec");
    this.summaries["timeRanges"] = new helio.TimeRangeSummary(this, this.taskName);
    this.summaries["eventList"] = new helio.EventListSummary(this, this.taskName);
};

//create EventListTask as subclass of AbstractTask
helio.EventListTask.prototype = new helio.AbstractTask;
helio.EventListTask.prototype.constructor = helio.EventListTask;

/**
 * Create an DataAccessTask
 * @param {String} taskName name of the actual implementation of the task.  
 * 
 */
helio.DataAccessTask = function(taskName) {
    helio.AbstractTask.call(this, taskName, "./catalog/dpas");
    this.summaries["timeRanges"] = new helio.TimeRangeSummary(this, this.taskName);
    this.summaries["instruments"] = new helio.InstrumentSummary(this, this.taskName);
};

//create DataAccessTask as subclass of AbstractTask
helio.DataAccessTask.prototype = new helio.AbstractTask;
helio.DataAccessTask.prototype.constructor = helio.DataAccessTask;

/**
 * Create an IcsTask
 * @param {String} taskName name of the actual implementation of the task.  
 * 
 */
helio.IcsTask = function(taskName) {
    helio.AbstractTask.call(this, taskName, "./catalog/ics");
    this.summaries["timeRanges"] = new helio.TimeRangeSummary(this, this.taskName);
};

//create IcsTask as subclass of AbstractTask
helio.IcsTask.prototype = new helio.AbstractTask;
helio.IcsTask.prototype.constructor = helio.IcsTask;

/**
 * Create an IlsTask
 * @param {String} taskName name of the actual implementation of the task.  
 * 
 */
helio.IlsTask = function(taskName) {
    helio.AbstractTask.call(this, taskName, "./catalog/ils");
    this.summaries["timeRanges"] = new helio.TimeRangeSummary(this, this.taskName);
};

//create IlsTask as subclass of AbstractTask
helio.IlsTask.prototype = new helio.AbstractTask;
helio.IlsTask.prototype.constructor = helio.IlsTask;

/**
 * Create a DES Task
 * @param {String} taskName name of the actual implementation of the task.  
 * 
 */
helio.DesTask = function(taskName) {
    helio.AbstractTask.call(this, taskName, "./catalog/des");
    this.summaries["timeRanges"] = new helio.TimeRangeSummary(this, this.taskName);
    this.summaries["paramSet"] = new helio.ParamSetSummary(this, this.taskName);
};

//create PlotTask as subclass of AbstractTask
helio.DesTask.prototype = new helio.AbstractTask;
helio.DesTask.prototype.constructor = helio.PlotTask;


/**
 * Task to upload a VOTable
 * @param {String} taskName name of the actual implementation variant of the task.  
 * 
 */
helio.VOTableUploadTask = function(taskName) {
    helio.AbstractTask.call(this, taskName);
    this.uploadSummary = undefined; 
    this.result = undefined;
};

//create VOTableUploadTask as subclass of AbstractTask
helio.VOTableUploadTask.prototype = new helio.AbstractTask;
helio.VOTableUploadTask.prototype.constructor = helio.VOTableUploadTask;

helio.VOTableUploadTask.prototype.init = function() {
    var THIS = this;
    
//    if (!this.uploadSummary) {
//        this.uploadSummary =  new helio.UploadSummary(this, this.taskName);
//    }
//    this.uploadSummary.init();

    // 1, format the buttons
    $("#btn_upload").button();
    
    // 2. init the collapsible sections
    $.collapsible(".queryHeader","group1");

    //$.fn.ajaxSubmit.debug = true;
    
    // connect the upload button
    $("#btn_upload").click(function() { THIS._submitQuery.call(THIS);});
    
    this.validate();
};

helio.VOTableUploadTask.prototype._submitQuery = function() {
    var THIS = this;
    $("#upload2Form").ajaxForm({
        beforeSubmit: function() {
            $('#msg_upload').empty().html('Submitting...');
        },
        target: '#task_result_area',   // target element(s) to be updated with server response
        success: function(data) { $('#msg_upload').empty(); THIS._handleResult.call(THIS, data); }
    }).submit();
};

})();