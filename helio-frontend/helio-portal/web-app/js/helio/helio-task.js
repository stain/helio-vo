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
    formatButton($(".buttonPerformQuery"));

    // 3. init the summary sections
    for(summary in this.summaries) {
        this.summaries[summary].init.call(this.summaries[summary]);
    }
    
    // 4. init the result area
    if (this.result) {
        $('#task_result_area').html(this.result.data);
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
        $("#task_result_area").html();
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
            $('#perform_query_text').html(errorMessage);
            
            $('#details_link').click(function() {
                new helio.ErrorMessageDialog(errorThrown, jqXHR.responseText).open();
            });
        },
        success: function(data, textStatus, jqXHR) { 
            submitDialog.close(); 
            var status = jqXHR.getResponseHeader('status');
            if (!status) status = "Data sucessfully loaded";
            $('#perform_query_text').html(status);
            
            THIS._handleResult.call(THIS, data); 
        },
        complete: function(jqXHR, textStatus) {
            submitDialog.close(); // if not already closed
            switch (textStatus) {
            case "success":
                break;
            case "error":
                $("#task_result_area").html();
                break;
            case "abort":
                $('#perform_query_text').html("Cancelled by user");
                $("#task_result_area").html();
                break;
            case "timeout":
                $('#perform_query_text').html("Your request timed out");
                $("#task_result_area").html();
            case "notmodified":
            case "parsererror":
            default:
                $('#perform_query_text').html("Unknown status: " + textStatus);
                $("#task_result_area").html();
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
    this.result = data;
    $('#task_result_area').html(data);
    this.result = new helio.TaskResult(this, this.taskName, data);
    this.result.init();
};

/**
 * Create a PropagationModelTask
 * @param {String} taskName name of the actual implementation of the task.  
 * 
 */
helio.PropagationModelTask = function(taskName) {
    helio.AbstractTask.call(this, taskName, "../processing/propagationModel");
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
    helio.AbstractTask.call(this, taskName, "../plot/plot");
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
    helio.AbstractTask.call(this, taskName, "../catalog/hec");
    this.summaries["timeRanges"] = new helio.TimeRangeSummary(this, this.taskName);
    this.summaries["eventList"] = new helio.EventListSummary(this, this.taskName);
};

//create EventListTask as subclass of AbstractTask
helio.EventListTask.prototype = new helio.AbstractTask;
helio.EventListTask.prototype.constructor = helio.EventListTask;


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
    formatButton($("#btn_upload"));
    
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
            $('#msg_upload').html('Submitting...');
        },
        target: '#task_result_area',   // target element(s) to be updated with server response
        success: function(data) { $('#msg_upload').html(); THIS._handleResult.call(THIS, data); }
    }).submit();
};

})();