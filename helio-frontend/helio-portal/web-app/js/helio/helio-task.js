/**
 * Object related to tasks and to dialog summaries.
 */
(function() {
/******************************** TASKS ************************************/
/**
 * Base class for a task
 * @param {String} taskName name of the task. 
 */
helio.AbstractTask = function(taskName) {
    this.taskName = taskName;
};


/**
 * Returns true if the task content is valid and can be submitted to the 
 * server. Please overload this method.
 * @return {Boolean} true if valid. 
 */
helio.AbstractTask.prototype._isValid = function() {
    throw "Please override method _isValid().";
    return false;
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
 * Create a PropagationModelTask
 * @param {String} taskName name of the actual implementation of the task.  
 * 
 */
helio.PropagationModelTask = function(taskName) {
    helio.AbstractTask.call(this, taskName);
    this.timeRangeSummary = undefined;
    this.paramSetSummary = undefined;
    this.result = undefined;
};

//create PropagationModelTask as subclass of AbstractTask
helio.PropagationModelTask.prototype = new helio.AbstractTask;
helio.PropagationModelTask.prototype.constructor = helio.PropagationModelTask;

helio.PropagationModelTask.prototype.init = function() {
    if (!this.timeRangeSummary) {
        this.timeRangeSummary =  new helio.TimeRangeSummary(this, this.taskName);
    }
    this.timeRangeSummary.init();
    
    if (!this.paramSetSummary) {
        this.paramSetSummary = new helio.ParamSetSummary(this, this.taskName);
    }
    this.paramSetSummary.init();
    
    if (this.result) {
        $('#task_result_area').html(this.result.data);
        this.result.init();
    }
    var THIS = this;
    $("#result_summary_select").click(function() {THIS._submitQuery.call(THIS);});

    this.validate();
};

/**
 * Submit the query to the server and handle result appropriately
 */
helio.PropagationModelTask.prototype._submitQuery = function() {
    var THIS = this;
    var bindings = {
        "taskName" : this.taskName,
        "inputParams" : {
            "timeRanges" : helio.cache[this.timeRangeSummary.dataKey].timeRanges, 
            "paramSet" : { "params" : helio.cache[this.paramSetSummary.dataKey].params.data }
        }
    };
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
        url: "../processing/propagationModel",
        type: "POST",
        data: {bindings : modelParam},
        error : function(jqXHR, textStatus, errorThrown) {
            submitDialog.close();
            if (textStatus == "abort") {
                // nothing to do
                return;
            }
            
            var errorMessage = $('<div class="errorMessage"></div>');
            errorMessage.append('Message: ' + textStatus + ' (<span id="details_link">details</span>)');
            $('#perform_query_text').html(errorMessage);
            
            $('#details_link').click(function() {
                var pre = $('<pre></pre>');
                pre.append(errorThrown);
                pre.append(jqXHR.responseText)
                errorMessage.append(pre);
                
                pre.dialog();
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
helio.PropagationModelTask.prototype._handleResult = function(data) {
    this.result = data;
	$('#task_result_area').html(data);
    this.result = new helio.ProcessingServiceResult(this, this.taskName, data);
    this.result.init();
};

/**
 * Returns true if the task content is valid and can be submitted to the 
 * server.
 * @return {Boolean} true if valid. 
 */
helio.PropagationModelTask.prototype._isValid = function() {
    var isValid =
       (this.timeRangeSummary != undefined && this.timeRangeSummary.isValid() &&
       this.paramSetSummary != undefined && this.paramSetSummary.isValid());
    return isValid;
};


/**
 * Create a PlotTask
 * @param {String} taskName name of the actual implementation of the task.  
 * 
 */
helio.PlotTask = function(taskName) {
    helio.AbstractTask.call(this, taskName);
    this.timeRangeSummary = undefined;
    if (taskName == 'parkerplot') {
        this.paramSetSummary = undefined;
    }
    this.result = undefined;
};

//create PlotTask as subclass of AbstractTask
helio.PlotTask.prototype = new helio.AbstractTask;
helio.PlotTask.prototype.constructor = helio.PlotTask;

helio.PlotTask.prototype.init = function() {
    if (!this.timeRangeSummary) {
        this.timeRangeSummary =  new helio.TimeRangeSummary(this, this.taskName);
    }
    this.timeRangeSummary.init();
    
    if (this.taskName == 'parkerplot') {
        if (!this.paramSetSummary) {
            this.paramSetSummary = new helio.ParamSetSummary(this, this.taskName);
        }
        this.paramSetSummary.init();
    }
    
    if (this.result) {
        $('#task_result_area').html(this.result.data);
        this.result.init();
    }
    var THIS = this;
    $("#result_summary_select").click(function() {THIS._submitQuery.call(THIS);});

    this.validate();
};

/**
 * Submit the query to the server and handle result appropriately
 */
helio.PlotTask.prototype._submitQuery = function() {
    var THIS = this;
    var bindings = this.taskName == 'parkerplot' ? {
        "taskName" : this.taskName,
        "inputParams" : {
            "timeRanges" : helio.cache[this.timeRangeSummary.dataKey].timeRanges, 
            "paramSet" : { "params" : helio.cache[this.paramSetSummary.dataKey].params.data}
        }
    } :
    {
        "taskName" : this.taskName,
        "inputParams" : {
            "timeRanges" : helio.cache[this.timeRangeSummary.dataKey].timeRanges, 
        }
    };
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
        url: "../plot/plot",
        type: "POST",
        data: {bindings : modelParam},
        error : function(jqXHR, textStatus, errorThrown) {
            submitDialog.close();
            if (textStatus == "abort") {
                // nothing to do
                return;
            }
            
            var errorMessage = $('<div class="errorMessage"></div>');
            errorMessage.append('Message: ' + textStatus + ' (<span id="details_link">details</span>)');
            $('#perform_query_text').html(errorMessage);
            
            $('#details_link').click(function() {
                var pre = $('<pre></pre>');
                pre.append(errorThrown);
                errorMessage.append(pre);
                pre.dialog();
            });
        },
        success: function(data) { 
            submitDialog.close(); 
            THIS._handleResult.call(THIS, data); 
        },
        complete: function(jqXHR, textStatus) {
            submitDialog.close(); // if not already closed
            switch (textStatus) {
            case "success":
                $('#perform_query_text').html("Data sucessfully loaded");
                break;
            case "error":
                //$('#perform_query_text').html("Error occurred");
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
helio.PlotTask.prototype._handleResult = function(data) {
    this.result = data;
    $('#task_result_area').html(data);
    this.result = new helio.PlotTaskResult(this, this.taskName, data);
    this.result.init();
};

/**
 * Returns true if the task content is valid and can be submitted to the 
 * server.
 * @return {Boolean} true if valid. 
 */
helio.PlotTask.prototype._isValid = function() {
    var isValid =
       (this.timeRangeSummary != undefined && this.timeRangeSummary.isValid() &&
       (this.taskName != 'parkerspiral' ||
       this.paramSetSummary != undefined && this.paramSetSummary.isValid()));
    return isValid;
};


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
        success: function(data) { THIS._handleResult.call(THIS, data); }
    }).submit();
};

helio.VOTableUploadTask.prototype._handleResult = function(data) {
    $('#msg_upload').html('');
    this.result = new helio.VOTableResult(this, this.taskName);
    this.result.init();    
};

})();