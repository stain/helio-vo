/**
 * Object related to tasks and to dialog summaries.
 */
(function() {
/***************************** DATA SUMMARY ************************************/

/**
 * Base class for dialog summary.
 * @param {helio.AbstractTask} task the task this object is bound to. 
 * @param {String} taskName the actual name of the task variant. 
 */
helio.AbstractSummary = function(task, taskName) {
    this.task = task;
    this.taskName = taskName;
};

/**
 * Abstract base function to clear a dialog
 * Overload this method.
 */
helio.AbstractSummary.prototype.clear = undefined;

/**
 * Check if a summary section contains valid content.
 * @return {Boolean} true if the content is valid
 */
/**
 * Return true if there is data for this area.
 * @returns {Boolean} true if there is data around.
 */
helio.AbstractSummary.prototype.isValid = function() {
    return helio.cache[this.dataKey] != null;
}

/**
 * TimeRangeSummary class
 * @param {helio.TimeRangeTask} task the task this summary is associated with.  
 * 
 */
helio.TimeRangeSummary = function(task, taskName) {
    helio.AbstractSummary.apply(this, [task, taskName]);
    this.dataKey = this.taskName + ".time_range_summary_data";
    this._init();
};

//create TimeRangeDialog as subclass of AbstractDialog
helio.TimeRangeSummary.prototype = new helio.AbstractSummary;
helio.TimeRangeSummary.prototype.constructor = helio.TimeRangeSummary;

/**
 * Init the time range summary
 */
helio.TimeRangeSummary.prototype._init = function() {
    var THIS = this;
    
    var populateSummary = function(timeRanges) {
        if (timeRanges) { // (re-)populate the summary section
            var table =$("<table></table>");
            if (timeRanges.name) {
                table.append('<tr><td><b>Name</b> </td><td>' + timeRanges.name + '</td></tr>');
            }
            // loop over the time ranges
            for(var i = 0; i < timeRanges.timeRanges.length; i++) {
                var timeRange =  timeRanges.timeRanges[i];
                table.append('<tr><td><b># ' + (i+1) + '</b>&nbsp;&nbsp;</td><td>' + timeRange.start + ' - ' + timeRange.end + '</td></tr>');
            }
            $("#time_range_summary_text").html(table);

            // store data in cache
            helio.cache[THIS.dataKey] = timeRanges;
            //console.log();
            
            $("#time_range_summary_drop").attr('src','../images/helio/circle_time.png');
            $("#time_range_summary_drop").addClass('drop_able');
            THIS.task.validate();
        } else {
           THIS.clear();
        }        
    };
    
    // 1, format the buttons
    formatButton($(".custom_button"));
    
    // 2. init the collapsible sections
    $.collapsible(".queryHeader","group1");
    
    // 3. click handler for time range dialogs
    $(".showTimeRangeDialog").click(function() {
        // get time range data from cache.
        var timeRanges = helio.cache[THIS.dataKey];
        
        var dialog = new helio.TimeRangeDialog(timeRanges);
        
        var closeCallback = function() {
            populateSummary(dialog.timeRanges);
        };
        
        dialog.show(closeCallback);
    });
    
    // 4. click handler for clear button
    $('#time_range_summary_clear').click(function() {THIS.clear.call(THIS);});
    
    // 5. populate summary
    populateSummary(helio.cache[THIS.dataKey]);
    
};

/**
 * Clear the current summary section.
 */ 
helio.TimeRangeSummary.prototype.clear = function() {
    $("#time_range_summary_text").html("");
    $("#time_range_summary_drop").attr('src','../images/helio/circle_time_grey.png');
    $("#time_range_summary_drop").removeClass('drop_able');
    delete helio.cache[this.dataKey];
    this.task.validate();
};

/**
 * ParamSetSummary class
 * @param {helio.ParamSetTask} task the task this summary is associated with.  
 * @param {String} taskName the name of the task to send.  
 * 
 */
helio.ParamSetSummary = function(task, taskName) {
    helio.AbstractSummary.call(this, task);
    this.dataKey = this.task.taskName + "_paramset_summary_data";
    this._init();
};

//create ParamSetSummry as subclass of AbstractSummry
helio.ParamSetSummary.prototype = new helio.AbstractSummary;
helio.ParamSetSummary.prototype.constructor = helio.ParamSetSummary;

/**
 * Init the param set summary
 */
helio.ParamSetSummary.prototype._init = function() {
    var THIS = this;
    
    var populateSummary = function(paramSet) {
        if (paramSet) { // (re-)populate the summary section
            var table =$("<table></table>");
            if (paramSet.name) {
                table.append('<tr><td><b>Name</b> </td><td>' + paramSet.name + '</td></tr>');
            }
            // loop over the data
            for(var param in paramSet.params.data) {
                    table.append('<tr><td><b>' + paramSet.params.config[param].label + '</b>&nbsp;&nbsp;</td><td>' + paramSet.params.data[param] + '</td></tr>');
            }
            $("#paramset_summary_text").html(table);
            
            // store data in cache
            helio.cache[THIS.dataKey] = paramSet;
            //console.log();
            
            $("#paramset_summary_drop").attr('src','../images/helio/circle_block.png');
            $("#paramset_summary_drop").addClass('drop_able');
            THIS.task.validate();
        } else {
            THIS.clear();
        }

    }
    
    // 3. click handler for paramset dialogs
    $(".showParamSetDialog").click(function() {
        // get data from cache.
        var paramSet = helio.cache[THIS.dataKey];
        
        var dialog = new helio.ParamSetDialog(paramSet);
        
        var closeCallback = function() {
            populateSummary(dialog.paramSet);
        };
        
        dialog.show(closeCallback);
    });
    
    // 4. click handler for clear button
    $('#paramset_summary_clear').click(function() {THIS.clear.call(THIS);});
    
    // 5. populate summary
    populateSummary(helio.cache[THIS.dataKey]);
};

/**
 * Clear the current summary section.
 */ 
helio.ParamSetSummary.prototype.clear = function() {
    $("#paramset_summary_text").html("");
    $("#paramset_summary_drop").attr('src','../images/helio/circle_block_grey.png');
    $("#paramset_summary_drop").removeClass('drop_able');
    delete helio.cache[this.dataKey];
    this.task.validate();
};


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
};

//create PropagationModelTask as subclass of AbstractTask
helio.PropagationModelTask.prototype = new helio.AbstractTask;
helio.PropagationModelTask.prototype.constructor = helio.PropagationModelTask;

helio.PropagationModelTask.prototype.init = function() {
    this.timeRangeSummary = new helio.TimeRangeSummary(this, this.taskName);
    this.paramSetSummary = new helio.ParamSetSummary(this, this.taskName);
    this.result = null; // store the result
    var THIS = this;
    
    $("#result_summary_select").click(function() {THIS._submitQuery.call(THIS);});
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
    //console.log(decodeURI(modelParam));
    
    jQuery.ajax({
        url: "../processing/propagationModel",
        type: "POST",
        data: {bindings : modelParam},
//        beforeSend: function(x) {
//            if (x && x.overrideMimeType) {
//              x.overrideMimeType("application/json;charset=UTF-8");
//            }
//        },
//        dataType: "json",
        target: '#task_result_area',   // target element(s) to be updated with server response
        success: function(data) { THIS._handleResult.call(THIS, data); }
    });
    
};

helio.PropagationModelTask.prototype._handleResult = function(data) {
    this.result = new helio.VOTableResult(this, this.taskName);
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
 * Task to upload a VOTable
 * @param {String} taskName name of the actual implementation variant of the task.  
 * 
 */
helio.VOTableUploadTask = function(taskName) {
    helio.AbstractTask.call(this, taskName);
    this.result = null; // store the result
};

//create VOTableUploadTask as subclass of AbstractTask
helio.VOTableUploadTask.prototype = new helio.AbstractTask;
helio.VOTableUploadTask.prototype.constructor = helio.VOTableUploadTask;

helio.VOTableUploadTask.prototype.init = function() {
    var THIS = this;
    
    // 1, format the buttons
    formatButton($("#btn_upload"));
    
    // 2. init the collapsible sections
    $.collapsible(".queryHeader","group1");

    //$.fn.ajaxSubmit.debug = true;
    
    // connect the upload button
    $("#btn_upload").click(function() { THIS._submitQuery.call(THIS);});
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
        
    // 6. download all/selection button
    $("#download_selection_button").click(function(){
        var itr= 0;
        $(".resultTable").each(function(){
            //console.debug($(this));
            itr++;
        });
        itr = itr/2;
        var download_list = $("<ul></ul>");
        var found = false;
        
        for(var i = 0;i<itr;i++){
            var dataTable =$("#resultTable"+i).dataTable();
            var settings = dataTable.fnSettings();
            var download_url = -1;

            for(var j = 0;j< settings.aoColumns.length;j++){
                if($.trim(settings.aoColumns[j].sTitle) == 'url'){
                    download_url=j;
                }
            }//end j

            if (download_url >= 0) {
                $("#resultTable"+i+" .even_selected").each(function(){
                    download_list.append("<li>"+$(this).children().eq(download_url).html()+"</li>");
                    found = true;
                });
                $("#resultTable"+i+" .odd_selected").each(function(){
                    download_list.append("<li>"+$(this).children().eq(download_url).html()+"</li>");
                    found = true;
                });
                if(download_list.html().indexOf('li') < 0){
                    var nNodes = dataTable.fnGetNodes();
                    for(var node in nNodes){
                        download_list.append("<li>"+$(nNodes[node]).children().eq(download_url).html()+"</li>");
                        found = true;
                    }
                }
            }
        }//end i
        
        if (!found) {
            download_list.append("<li>Nothing found to download</li>");
        }
            
        
        var recipe =  window.open('','_blank','width=600,height=600');
        var html = '<html><head><title>Helio Downloads</title></head><body><h1>List of URLs to dowload</h1><p>Use a download manager for your browser to download the links.</p><div id="links">' + download_list.html() + '</div></body></html>';
        recipe.document.open();
        recipe.document.write(html);
        recipe.document.close();
    });
};

})();