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
    this._resultTables = [];
};

//create PlotResult as subclass of AbstractResult
helio.VOTableResult.prototype = new helio.AbstractResult;
helio.VOTableResult.prototype.constructor = helio.VOTableResult;

helio.VOTableResult.prototype.init = function() {
    var THIS = this;
    
    // format the reponse elements
    // 1. buttons
    $(".custom_button").button();
    
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
    $("#download_selection_button").click(function() {
        THIS._download.call(THIS);
    });
    
    // 7. extract params button
    $('#extract_param_button').click(function() {
        THIS._extractParams.call(THIS);
    });
    $('#extract_instrument_param_button').click(function() {
        THIS._extractInstrumentParams.call(THIS);
    });    

    // 2. result table
    $(".resultTable").each(function() {
        var table = THIS._formatTable(this.id);
        THIS._resultTables.push(table);
        
        // do service specific initialisation, if applicable
        var tableName = $(this).attr("name"); 
        if (tableName.indexOf("initTable_") == 0) { // startsWith
            // if there is a function with the initName defined on this object
            var initName = '_' + tableName;
            if (helio.VOTableResult.prototype[initName]) {
                // call the function
                THIS[initName].call(THIS, table);                
            }
        };
    });    
};

/**
 * Formats every datatable in the system and adds listeners to the rows to be clicked
 * @tableName: takes in the id of the datatable to be parsed, data table should have headers set and body set with matching number of elements
 */
helio.VOTableResult.prototype._formatTable = function(tableName) {
    var THIS = this;
    var tmp = tableName.split('_');
    var resultId = tmp[1];
    var tableIndex = tmp[2];
    
    // 1. format the table
    var dataTable = null;
    dataTable =$("#"+tableName).dataTable({
        "bJQueryUI": true,
        "bAutoWidth": true,

        "bProcessing": true,
        "sAjaxSource": './voTable/data?resultId=' + resultId + '&tableIndex=' + tableIndex,
        "bDeferRender": true,

        "sScrollX": "100%",
//        "iScrollLoadGap": 50,
        "sScrollY": "500px",
//        "bScrollInfinite": true,
        "bScrollCollapse": true,
//        "sDom": "frtiS",
        
        "bPaginate": true,
        "sPaginationType": "full_numbers",
        "iDisplayLength": 50,
        "aLengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
        
        "fnDrawCallback": function() {
            THIS._initCustomColumns.call(THIS, this, tableName);
            var oSettings = this.fnSettings();
            // FF hack to fix the problem of a scrolling bar appearing if only one row of data is shown.
            oSettings.nTable.parentNode.style.height = ($(oSettings.nTable).height() + 17) + "px";
        },
        "fnCreatedRow": function( nRow, aData, iDataIndex ) {
            // do service specific row formatting, if required
            var tableName = $(this).attr("name"); 
            var functionName =  "_" + tableName + "_initRow";
            // if there is a function with the functionName defined on this object
            if (helio.VOTableResult.prototype[functionName]) {
                // call the function
                THIS[functionName].call(THIS, nRow, aData, iDataIndex);
            }
        }
        //"sScrollXInner": "100%",
     
    });
        
    // 2. init special columns
    var cols = dataTable.fnSettings().aoColumns;
    for (var col in cols) {
        var th = $(cols[col].nTh); 
        if (th.hasClass('hiddenRow')) {
            dataTable.fnSetColumnVis( col, false);
        }
    }
        
    return dataTable;
};

helio.VOTableResult.prototype._initCustomColumns = function(dataTable, tableName) {
    var THIS = this;
    // 1. the row selection listener
    $('#' + tableName + ' tr').unbind('click').click( function() {
        if ( $(this).hasClass('row_selected') ){
            $(this).removeClass('row_selected');
        } else {
            $(this).addClass('row_selected');
        };
    });    

    // 2. 
    var cols = dataTable.fnSettings().aoColumns;
    for (var col in cols) {
      var th = $(cols[col].nTh); 
      if (th.hasClass('th_examine_event')) {
        dataTable.find('.examine_event').not(':has(img)').attr('title', 'Inspect the time range covered by this event')
           .append('<img style="width:15px;heigth:15px" src="./images/search.png" />')
           .click((function(dataTable) {
             return function() {
                var settings = dataTable.fnSettings();
                var time_start = -1;
                var time_end = -1;
                for(var j = 0;j< settings.aoColumns.length;j++){
                    var title = $.trim(settings.aoColumns[j].sTitle);
                    if(title == 'time_start'){
                        time_start=j;
                    } else if(title == 'time_end'){
                        time_end=j;
                    } 
                }//end j
                
                if (time_start == -1) {
                    alert("No column with name 'time_start' or 'time' found");
                    return;
                }
                if (time_end == -1) {
                    time_end = time_start;
                }
                var times = dataTable.fnGetData($(this).closest('tr')[0], time_start);
                var timee = dataTable.fnGetData($(this).closest('tr')[0], time_end);
                //sendExamineEvent(times,timee);
                
                // adjust times
                var timeStartObject = moment(times, "YYYY-MM-DDTHH:mm:ss");
                var timeEndObject = moment(timee, "YYYY-MM-DDTHH:mm:ss");
                timeStartObject.subtract("hours", 6);
                timeEndObject.add("hours", 6);                
                
                var timeRange = new helio.TimeRange(timeStartObject, timeEndObject);
                var dialog = new helio.TimeRangeDetailsDialog(null, 'timeRangeDetails', timeRange);
                dialog.show(function() {
                    if (dialog.data.timeStart != times ||
                        dialog.data.timeEnd   != timee) {
                        var timeRanges = new helio.TimeRanges('datacart', 'extracted');
                        timeRanges.timeRanges.push(new helio.TimeRange(dialog.data.timeStart, dialog.data.timeEnd));
                        THIS._showExtractParamsDialog.call(THIS, timeRanges, 
                            "<p><b>You modified the time range.</b> Do you want to store it to the data cart?</p>" +
                            "<p>Click <code>Ok</code> to store or <code>Cancel</code> to dismiss.</p>");
                    }
                });
                return false;
            };
        })(dataTable));
      }
    }   
};


helio.VOTableResult.prototype._download = function(){
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
};

helio.VOTableResult.prototype._extractParams = function() {
    // create a new data object.
    var timeRanges = new helio.TimeRanges('datacart', 'extracted');

    $.each(this._resultTables, function(){
        var dataTable = this;
        // find the position of the time range columns we are interested in.
        var settings = dataTable.fnSettings();
        var time_start = -1;
        var time_end = -1;
        
        for(var j = 0;j< settings.aoColumns.length;j++){
            var title = $.trim(settings.aoColumns[j].sTitle); 
            if(title == 'time_start'){
                time_start=j;
            }
            if(title == 'time_end'){
                time_end=j;
            } else if (title == 'time' && time_start == -1) {
                time_start=j;
            }
        }//end j
        if (time_start == -1) {
            alert("Internal error: no column with name 'time_start' found");
            return;
        }
        if (time_end == -1) {
            time_end = time_start;
        }
        
        // loop over all selected rows
        dataTable.find('.row_selected').each(function() {
            // extract time range and add to timeranges.
            var times = dataTable.fnGetData(this, time_start);
            var timee = dataTable.fnGetData(this, time_end);
     
            // parse and reformat the time to be a bit more forgiving
            var timeStartObject = moment(times, "YYYY-MM-DDTHH:mm:ss");
            var timeEndObject = moment(timee, "YYYY-MM-DDTHH:mm:ss");
            times = timeStartObject.format("YYYY-MM-DDTHH:mm:ss");
            timee = timeEndObject.format("YYYY-MM-DDTHH:mm:ss");
            
            timeRanges.timeRanges.push(new helio.TimeRange(times, timee));
        });
    });

    if (timeRanges.timeRanges.length == 0) {
        alert("Please select any row(s) to extract time ranges from.");
        return;
    }
    
    this._showExtractParamsDialog(timeRanges);
};

/**
 * Show the time extraction dialog.
 * @param {helio.TimeRanges} timeRanges the timeRanges object to use.
 * @param {String} message optional message to be shown in the time range dialog.
 */
helio.VOTableResult.prototype._showExtractParamsDialog = function(timeRanges, message) {
    var dummyTask = new helio.AbstractTask("datacart", null);
    var dialog = new helio.TimeRangeDialog(dummyTask, "datacart", timeRanges, message);
    dialog.show(function() {
        helio.dataCart.addItem(timeRanges); 
        var rowpos = $('#datacart_container').position();
        if(rowpos){
            $('html,body').scrollTop(rowpos.top);
        }        
    });
};

helio.VOTableResult.prototype._extractInstrumentParams = function() {
    // create a new data object.
    var data = new helio.Instrument('datacart', 'extracted');

    $.each(this._resultTables, function(){
        var dataTable = this;
        // find the position of the time range columns we are interested in.
        var settings = dataTable.fnSettings();
        var instrument = -1;
        var longname = -1;
        for(var j = 0;j< settings.aoColumns.length;j++){
            var title = $.trim(settings.aoColumns[j].sTitle); 
            if(title == 'obsinst_key'){
                instrument=j;
            }
            if(title == 'longname'){
                longname=j;
            }
        }//end j
        if (instrument == -1) {
            alert("Internal error: no column with name 'obsinst_key' found");
            return;
        }
        if (longname == -1) {
            longname = instrument;
        }
        
        // loop over all selected rows
        dataTable.find('.row_selected').each(function() {
            // extract time range and add to timeranges.
            var instr = dataTable.fnGetData(this, instrument);
            var instrLabel = dataTable.fnGetData(this, longname);
            data.addInstrument(instr, instrLabel);
        });
    });

    if (data.instruments.length == 0) {
        alert("Please select any row(s) to extract instruments from.");
        return;
    }
    var dummyTask = new helio.AbstractTask("datacart", null);
    var dialog = new helio.InstrumentDialog(dummyTask, "datacart", data);
    dialog.show(function() {
        helio.dataCart.addItem(data); 
        var rowpos = $('#datacart_container').position();
        if(rowpos){
            $('html,body').scrollTop(rowpos.top);
        }
    });
};

/**
 * Init-function for ICS result. Is called from init().
 */
helio.VOTableResult.prototype._initTable_ICS = function(table) {
    this._resultFilterTimeout = {};
    var THIS = this;
    
    // handle filtering
    $("#instrument_filter").tabs();
    
    //function to filter out the data table based on what's being check in the checkboxes
    $("input:checkbox").change(function(){
        // Photons
        // obsEntityPhotons = super filter
        // obsEntityPhotonsType = type filter
        
        // super filter clicked
        if($(this).hasClass("obsEntityPhotons")) {
            if(!$(this).attr("checked")) {
                $(".obsEntityPhotons").attr("checked", false); //uncheck hidden checkbox
            }
            
            var typesChecked = false;
            // if any type is checked, uncheck it
            $(".obsEntityPhotonsType").each(function(){
                if($(this).attr("checked")) {
                    $(this).attr("checked", false);
                    typesChecked = true;
                    THIS._filter.call(THIS, $(this), table);

                }
            });
            
            // if no type is checked, check all
            if(!typesChecked && $(this).attr("checked")) {
                $(".obsEntityPhotonsType").each(function(){
                    // only check visible checkboxes, otherwise they can't be reset again
                    if ($(this).is(":visible")) {
                        $(this).attr("checked", true);
                        THIS._filter.call(THIS, $(this), table);

                    }
                });
            }
        }
        // type filter clicked
        else if($(this).hasClass("obsEntityPhotonsType")) {
            // check super filter
            if($(this).attr("checked")) {
                $(".obsEntityPhotons").attr("checked", true);
                THIS._filter.call(THIS, $($(".obsEntityPhotons").get(-1)), table);

            }
            // if no type filter is selected remove super filter
            else {
                var noTypeChecked = true;
                // prove if no type filter is checked
                $(".obsEntityPhotonsType").each(function(){
                    if($(this).attr("checked")) {
                        noTypeChecked = false;
                    }
                });
                if (noTypeChecked) {
                    $(".obsEntityPhotons").attr("checked", false);
                    THIS._filter.call(THIS, $($(".obsEntityPhotons").get(-1)), table);
                }
            }
        }
        
        // Particles
        if($(this).hasClass("obsEntityParticles")) {
            if(!$(this).attr("checked")) {
                $(".obsEntityParticles").attr("checked", false); //uncheck hidden checkbox
            }
            
            var typesChecked = false;
            $(".obsEntityParticlesType").each(function(){
                if($(this).attr("checked")) {
                    $(this).attr("checked", false);
                    typesChecked = true;
                    THIS._filter.call(THIS, $(this), table);
                }
            });
            
            if(!typesChecked && $(this).attr("checked")) {
                $(".obsEntityParticlesType").each(function(){
                    if ($(this).is(":visible")) {
                        $(this).attr("checked", true);
                        THIS._filter.call(THIS, $(this), table);
                    }
                });
            }
        }
        else if($(this).hasClass("obsEntityParticlesType")) {
            if($(this).attr("checked")) {
                $(".obsEntityParticles").attr("checked", true);
                THIS._filter.call(THIS, $($(".obsEntityParticles").get(-1)), table);
            }
            else {
                var noTypeChecked = true;
                $(".obsEntityParticlesType").each(function(){
                    if($(this).attr("checked")) {
                        noTypeChecked = false;
                    }
                });
                if (noTypeChecked) {
                    $(".obsEntityParticles").attr("checked", false);
                    THIS._filter.call(THIS, $($(".obsEntityParticles").get(-1)), table);
                }
            }
        }

        // Fields
        if($(this).hasClass("obsEntityFields")) {
            if(!$(this).attr("checked")) {
                $(".obsEntityFields").attr("checked", false); //uncheck hidden checkbox
            }
            
            var typesChecked = false;
            $(".obsEntityFieldsType").each(function(){
                if($(this).attr("checked")) {
                    $(this).attr("checked", false);
                    typesChecked = true;
                    THIS._filter.call(THIS, $(this), table);
                }
            });
            
            if(!typesChecked && $(this).attr("checked")) {
                $(".obsEntityFieldsType").each(function(){
                    if ($(this).is(":visible")) {
                        $(this).attr("checked", true);
                        THIS._filter.call(THIS, $(this), table);
                    }
                });
            }
        }
        else if($(this).hasClass("obsEntityFieldsType")) {
            if($(this).attr("checked")) {
                $(".obsEntityFields").attr("checked", true);
                THIS._filter.call(THIS, $($(".obsEntityFields").get(-1)), table);
            }
            else {
                var noTypeChecked = true;
                $(".obsEntityFieldsType").each(function(){
                    if($(this).attr("checked")) {
                        noTypeChecked = false;
                    }
                });
                if (noTypeChecked) {
                    $(".obsEntityFields").attr("checked", false);
                    THIS._filter.call(THIS, $($(".obsEntityFields").get(-1)), table);
                }
            }
        }
        
        //console.log("change: " + $(this).attr("name") + $(this).attr("column"));
         
        THIS._filter.call(THIS, $(this), table);
    });

    $("input:radio").change(function(){
        var checkboxColumn = $(this).attr("column");
        var filter_expression = "";
        filter_expression = (filter_expression == "" ? "" : (filter_expression + "|")) + "(" + $(this).attr("value") + ")";
        table.fnFilter(filter_expression, checkboxColumn, true);
    });
    
    $("#show_accessible").click();

};

helio.VOTableResult.prototype._initTable_ICS_initRow = function(nRow, aData, iDataIndex) {
    // loop over all rows.
    var data = $('td:last', nRow).text();
    if(data == "true") {
        $(nRow).addClass("item_found");
    } else {
        $(nRow).addClass("item_missing");
    }
};

/**
 * Init-function for ILS result. Is called from init().
 */
helio.VOTableResult.prototype._initTable_ILS = function(table) {
    this._resultFilterTimeout = {};
    var THIS = this;
    // loop over all rows.
    var trNodes = table.fnGetNodes();
    $.each(trNodes, function(index, tr) {
        var trdata = table.fnGetData(tr);
        var data = trdata[trdata.length-1];
        if(data == "true") {
            $(tr).addClass("item_found");
        }else {
            $(tr).addClass("item_missing");
        }
    });
    
    // handle filtering
    $("#instrument_filter").tabs();
    
    //function to filter out the data table based on what's being check in the checkboxes
    $("input:checkbox").change(function(){
        // Photons
        // obsEntityPhotons = super filter
        // obsEntityPhotonsType = type filter
        
        // super filter clicked
        if($(this).hasClass("obsEntityPhotons")) {
            if(!$(this).attr("checked")) {
                $(".obsEntityPhotons").attr("checked", false); //uncheck hidden checkbox
            }
            
            var typesChecked = false;
            // if any type is checked, uncheck it
            $(".obsEntityPhotonsType").each(function(){
                if($(this).attr("checked")) {
                    $(this).attr("checked", false);
                    typesChecked = true;
                    THIS._filter.call(THIS, $(this), table);

                }
            });
            
            // if no type is checked, check all
            if(!typesChecked && $(this).attr("checked")) {
                $(".obsEntityPhotonsType").each(function(){
                    // only check visible checkboxes, otherwise they can't be reset again
                    if ($(this).is(":visible")) {
                        $(this).attr("checked", true);
                        THIS._filter.call(THIS, $(this), table);

                    }
                });
            }
        }
        // type filter clicked
        else if($(this).hasClass("obsEntityPhotonsType")) {
            // check super filter
            if($(this).attr("checked")) {
                $(".obsEntityPhotons").attr("checked", true);
                THIS._filter.call(THIS, $($(".obsEntityPhotons").get(-1)), table);

            }
            // if no type filter is selected remove super filter
            else {
                var noTypeChecked = true;
                // prove if no type filter is checked
                $(".obsEntityPhotonsType").each(function(){
                    if($(this).attr("checked")) {
                        noTypeChecked = false;
                    }
                });
                if (noTypeChecked) {
                    $(".obsEntityPhotons").attr("checked", false);
                    THIS._filter.call(THIS, $($(".obsEntityPhotons").get(-1)), table);
                }
            }
        }
        
        // Particles
        if($(this).hasClass("obsEntityParticles")) {
            if(!$(this).attr("checked")) {
                $(".obsEntityParticles").attr("checked", false); //uncheck hidden checkbox
            }
            
            var typesChecked = false;
            $(".obsEntityParticlesType").each(function(){
                if($(this).attr("checked")) {
                    $(this).attr("checked", false);
                    typesChecked = true;
                    THIS._filter.call(THIS, $(this), table);
                }
            });
            
            if(!typesChecked && $(this).attr("checked")) {
                $(".obsEntityParticlesType").each(function(){
                    if ($(this).is(":visible")) {
                        $(this).attr("checked", true);
                        THIS._filter.call(THIS, $(this), table);
                    }
                });
            }
        }
        else if($(this).hasClass("obsEntityParticlesType")) {
            if($(this).attr("checked")) {
                $(".obsEntityParticles").attr("checked", true);
                THIS._filter.call(THIS, $($(".obsEntityParticles").get(-1)), table);
            }
            else {
                var noTypeChecked = true;
                $(".obsEntityParticlesType").each(function(){
                    if($(this).attr("checked")) {
                        noTypeChecked = false;
                    }
                });
                if (noTypeChecked) {
                    $(".obsEntityParticles").attr("checked", false);
                    THIS._filter.call(THIS, $($(".obsEntityParticles").get(-1)), table);
                }
            }
        }

        // Fields
        if($(this).hasClass("obsEntityFields")) {
            if(!$(this).attr("checked")) {
                $(".obsEntityFields").attr("checked", false); //uncheck hidden checkbox
            }
            
            var typesChecked = false;
            $(".obsEntityFieldsType").each(function(){
                if($(this).attr("checked")) {
                    $(this).attr("checked", false);
                    typesChecked = true;
                    THIS._filter.call(THIS, $(this), table);
                }
            });
            
            if(!typesChecked && $(this).attr("checked")) {
                $(".obsEntityFieldsType").each(function(){
                    if ($(this).is(":visible")) {
                        $(this).attr("checked", true);
                        THIS._filter.call(THIS, $(this), table);
                    }
                });
            }
        }
        else if($(this).hasClass("obsEntityFieldsType")) {
            if($(this).attr("checked")) {
                $(".obsEntityFields").attr("checked", true);
                THIS._filter.call(THIS, $($(".obsEntityFields").get(-1)), table);
            }
            else {
                var noTypeChecked = true;
                $(".obsEntityFieldsType").each(function(){
                    if($(this).attr("checked")) {
                        noTypeChecked = false;
                    }
                });
                if (noTypeChecked) {
                    $(".obsEntityFields").attr("checked", false);
                    THIS._filter.call(THIS, $($(".obsEntityFields").get(-1)), table);
                }
            }
        }
        
        //console.log("change: " + $(this).attr("name") + $(this).attr("column"));
         
        THIS._filter.call(THIS, $(this), table);
    });

    $("input:radio").change(function(){
        var checkboxColumn = $(this).attr("column");
        var filter_expression = "";
        filter_expression = (filter_expression == "" ? "" : (filter_expression + "|")) + "(" + $(this).attr("value") + ")";
        table.fnFilter(filter_expression, checkboxColumn, true);
    });
    
    $("#show_accessible").click();

};


helio.VOTableResult.prototype._filter = function(checkbox, table) {
    $(".filterInstrumentsText").hide();
    
    var checkboxColumn = checkbox.attr("column");
    var filter_expression = "\0";
    
    var anySelected = false;
    var instruments = new Array();
    
    $("input:checked").each(function(){
        anySelected = true;
        if($(this).attr("column") == checkboxColumn)
            filter_expression = filter_expression + "|(.*" + $(this).attr("name") + ".*)";

        if ($(this).hasClass("planet") || $(this).hasClass("spacecraft")) {
            instruments.push($(this).attr("name"));
        }
    });
    
    if (anySelected && (checkbox.hasClass("planet") || checkbox.hasClass("spacecraft"))) {
        $(".filterInstrumentsText").html("Show entries WHERE planet/spacecraft is " + instruments.join(" OR "));
    }
    else if (checkbox.hasClass("planet") || checkbox.hasClass("spacecraft")) {
        $(".filterInstrumentsText").html("All planets/spacecraft are shown.");
    } else {
        $(".filterInstrumentsText").html("");        
    }
    
    if(filter_expression=="\0")
        filter_expression="";
    
    if(this._resultFilterTimeout[checkboxColumn] != null)
        clearTimeout(this._resultFilterTimeout[checkboxColumn]);
    
    this._resultFilterTimeout[checkboxColumn] = setTimeout(function(){
        table.fnFilter(filter_expression,checkboxColumn,true);
        //console.log(filter_expression);
    },200);
    $(".filterInstrumentsText").delay(500).fadeIn();   
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


