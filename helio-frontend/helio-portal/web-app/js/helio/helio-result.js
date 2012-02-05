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
 * Generic Result container for a call to a Task. Special tasks may implement their own container.
 * This model delegates the core functionality to VOTableResult, PlotResult and LogResult.
 * @param {helio.AbstractTask} task the task this result is associated with.  
 * @param {String} taskName the actual name of the task variant. 
 * @param {Object} data the content of the result 
 * 
 */
helio.TaskResult = function(task, taskName, data) {
    helio.AbstractResult.apply(this, [task, taskName]);
    this.data = data;
    this.votableResult = new helio.VOTableResult(task, taskName);
    this.plotResult = new helio.PlotResult(task, taskName);
    this.logResult = new helio.LogResult(task, taskName);
};

//create TaskResult as subclass of AbstractResult
helio.TaskResult.prototype = new helio.AbstractResult;
helio.TaskResult.prototype.constructor = helio.TaskResult;

/**
 * Initialize the processing service result
 */
helio.TaskResult.prototype.init = function() {
    this.votableResult.init();
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
    var THIS = this;
    
    // format the reponse elements
    // 1. buttons
    formatButton($(".custom_button"));

    // 2. result table
    $(".resultTable").each(function() { THIS._formatTable(this.id);});
    
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
 * Formats every datatable in the system and adds listeners to the rows to be clicked
 * @tableName: takes in the id of the datatable to be parsed, data table should have headers set and body set with matching number of elements
 */
helio.VOTableResult.prototype._formatTable = function(tableName) {
    // 1. format the table
    var dataTable =$("#"+tableName).dataTable({
        "bJQueryUI": true,
        "bAutoWidth": true,
        "bRetrieve":true,
        "bDestroy":true,
        "bLengthChange": true,
        "sPaginationType": "full_numbers",
        "sScrollX": "100%",
        "sScrollY": "500px",
        "bPaginate": false,
        "iDisplayLength": 25,
        "aLengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
        //"sScrollXInner": "100%",
        "bScrollCollapse": true,
     
    });
    
    // 2. init special columns
    var cols = dataTable.fnSettings().aoColumns;
    for (var col in cols) {
        var th = $(cols[col].nTh); 
        if (th.hasClass('hiddenRow')) {
            dataTable.fnSetColumnVis( col, false);
        }
        if (th.hasClass('th_examine_event')) {
            dataTable.find('.examine_event').attr('title', 'Get more information about this event')
               .append('<img style="width:15px;heigth:15px" src="../images/search.png" />')
               .click((function(dataTable) {
                 return function() {
                    var settings = dataTable.fnSettings();
                    var time_start = -1;
                    var time_end = -1;
                    for(var j = 0;j< settings.aoColumns.length;j++){
                        if($.trim(settings.aoColumns[j].sTitle) == 'time_start'){
                            time_start=j;
                        }
                        if($.trim(settings.aoColumns[j].sTitle) == 'time_end'){
                            time_end=j;
                        }
                    }//end j
                    if (time_start == -1) {
                        alert("No column with name time_start found");
                        return;
                    } 
                    if (time_end == -1) {
                        time_end = time_start;
                    }
                    var times = dataTable.fnGetData($(this).closest('tr')[0], time_start);
                    var timee = dataTable.fnGetData($(this).closest('tr')[0], time_end);
                    //sendExamineEvent(times,timee);

                    $("#dialog-message").remove();
                    var div =$('<div></div>');
                    div.attr('id','dialog-message');
                    div.attr('title','Event Details');
                                  
                    var html = window.workspace.getDivisions()["input_event_view"];
                    div.append(html);

                    $("#testdiv").append(div);
                    $("#details_start_date").text(times);
                    $("#details_end_date").text(timee);
                    formatButton($('.custom_button'));
                    $("#fplot_button").click(function(){
                        sendExamineEvent(times,timee,"fplot");
                    });
                    $("#cplot_button").click(function(){
                        sendExamineEvent(times,timee,"cplot");
                    });
                    $("#pplot_button").click(function(){
                        sendExamineEvent(times,timee,"pplot");
                    });
                    sendExamineEvent(times,timee,"link");
                    $('#dialog-message').dialog({
                        modal: true,
                        height:600,
                        width:800,
                        buttons: {
                            Ok: function() {
                                $("#dialog-message").dialog( "close" );
                                $("#dialog-message").remove();
                            }
                        }
                    });
                    return false;
                };
              })(dataTable));
        }
    }

    // 4. the row selection listener
    var rows = $('#' + tableName + ' tr');
    $('#' + tableName + ' tr').click( function() {
        //var row = table.fnGetData(this);    // current row
        if ( $(this).hasClass('row_selected') ){
            $(this).removeClass('row_selected');
            //THIS.newdata.removeList.call(THIS.newdata, id);
        } else {
            $(this).addClass('row_selected');
            //THIS.newdata.addList.apply(THIS.newdata, [id, label]);
        };
    });    
    return dataTable;
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


