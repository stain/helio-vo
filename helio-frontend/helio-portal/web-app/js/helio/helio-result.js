/**
 * Classes related to result handling.
 */
(function() {

/**
 * Base class for results
 * @param {helio.AbstractTask} task the task this object is bound to. 
 * @param {String} taskName the actual name of the task variant. 
 */
helio.AbstractResult = function(task, taskName) {
    this.task = task;
    this.taskName = taskName;
};

/**
 * Abstract base function to clear a result
 * Overload this method.
 */
helio.AbstractResult.prototype.clear = undefined;


/**
 * Result container for a call to a processing service.
 * This model delegates the core functionality to VoTableResult and PlotResult.
 * @param {helio.AbstractTask} task the task this result is associated with.  
 * @param {String} taskName the actual name of the task variant. 
 * @param {Object} data the content of the result 
 * 
 */
helio.ProcessingServiceResult = function(task, taskName, data) {
    helio.AbstractResult.apply(this, [task, taskName]);
    this.data = data;
    this.votableResult = new helio.VOTableResult(task, taskName);
    this.plotResult = new helio.PlotResult(task, taskName);
    this.logResult = new helio.LogResult(task, taskName);
};

//create ProcessingServiceResult as subclass of AbstractResult
helio.ProcessingServiceResult.prototype = new helio.AbstractResult;
helio.ProcessingServiceResult.prototype.constructor = helio.ProcessingServiceResult;

/**
 * Initialize the processing service result
 */
helio.ProcessingServiceResult.prototype.init = function() {
    this.votableResult.init();
    this.plotResult.init();
    this.logResult.init();
    var rowpos = $('#task_result_area').position();
    if(rowpos){
        $('html,body').scrollTop(rowpos.top);
    }
};

/**
 * Result container for a call to a plotting service.
 * This model delegates the core functionality to  PlotResult.
 * @param {helio.AbstractTask} task the task this result is associated with.  
 * @param {String} taskName the actual name of the task variant. 
 * @param {Object} data the content of the result 
 * 
 */
helio.PlotTaskResult = function(task, taskName, data) {
    helio.AbstractResult.apply(this, [task, taskName]);
    this.data = data;
    this.plotResult = new helio.PlotResult(task, taskName);
    this.logResult = new helio.LogResult(task, taskName);
};

//create PlotTaskResult as subclass of AbstractResult
helio.PlotTaskResult.prototype = new helio.AbstractResult;
helio.PlotTaskResult.prototype.constructor = helio.PlotTaskResult;

/**
 * Initialize the processing service result
 */
helio.PlotTaskResult.prototype.init = function() {
    this.plotResult.init();
    this.logResult.init();
    var rowpos = $('#task_result_area').position();
    if(rowpos){
        $('html,body').scrollTop(rowpos.top);
    }
};


/******************** Components of a result **************************/
/**
 * VOTable result
 * @param {helio.AbstractTask} task the task this result is associated with.  
 * @param {String} taskName the actual name of the task variant. 
 * 
 */
helio.VOTableResult = function(task, taskName) {
    helio.AbstractResult.apply(this, [task, taskName]);
    this.dataKey = taskName + ".result";
};

//create PlotResult as subclass of AbstractResult
helio.VOTableResult.prototype = new helio.AbstractResult;
helio.VOTableResult.prototype.constructor = helio.VOTableResult;

helio.VOTableResult.prototype.init = function() {
    // format the reponse elements
    // 1. buttons
    formatButton($(".custom_button"));

    // 2. result table
    $(".resultTable").each(function() {fnFormatTable(this.id);});
    
    // 3. enable ok-dialogs
    $(".ok_dialog").dialog({ autoOpen: false, modal: true, width: 600,
        buttons: { "Ok": function() { $(this).dialog("close"); }} 
    });
    
    // 4. make result area collapsible
    $.collapsible(".votableResultHeader", "votableResult");

    // 5. connect table info buttons
    $(".table_info_button").click(function() {
        var dialogId = "#" + this.id.substring(0, this.id.length - '_button'.length);
        $(dialogId).dialog('open');
    });
    
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

/**
 * Plot result
 * @param {helio.AbstractTask} task the task this result is associated with.  
 * @param {String} taskName the actual name of the task variant. 
 * 
 */
helio.PlotResult = function(task, taskName) {
    helio.AbstractResult.apply(this, [task, taskName]);
    this.dataKey = taskName + ".plots";
};

//create PlotResult as subclass of AbstractResult
helio.PlotResult.prototype = new helio.AbstractResult;
helio.PlotResult.prototype.constructor = helio.PlotResult;

helio.PlotResult.prototype.init = function() {
    // 1. make plot area collapsible
    $.collapsible("#task_result_area .plotResultHeader", "plotResult");
};

/**
 * Log result
 * @param {helio.AbstractTask} task the task this result is associated with.  
 * @param {String} taskName the actual name of the task variant. 
 * 
 */
helio.LogResult = function(task, taskName) {
    helio.AbstractResult.apply(this, [task, taskName]);
    this.dataKey = taskName + ".log";
};

//create LogResult as subclass of AbstractResult
helio.LogResult.prototype = new helio.AbstractResult;
helio.LogResult.prototype.constructor = helio.LogResult;

helio.LogResult.prototype.init = function() {
    // 4. make log area collapsible
    $.collapsible("#task_result_area .userLogsHeader", "userLogs");
};

})();


