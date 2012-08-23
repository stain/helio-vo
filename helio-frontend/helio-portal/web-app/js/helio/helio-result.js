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
    
    // format the response elements
    // 1. buttons
//    $(".custom_button").button();
    
    // 2. result tabs
    $("#tabs_votables").tabs({cache: false}); // tabs for multiple votables
    $(".tabs_votable_result").tabs(); // tab for plot/tabular view
    
    // 3. enable ok-dialogs (but first destroy any existing dialog)
    $(".ok_dialog")
    .dialog('destroy')
    .dialog({ autoOpen: false, modal: true, width: 600,
        buttons: { "Ok": function() { $(this).dialog("close"); }},
    });
    
    // 4. make result area collapsible
    $.collapsible(".votableResultHeader", "votableResult");

    // 5. connect table info buttons
    $(".table_info_button").click(function() {
        var dialogId = "#" + this.id.substring(0, this.id.length - '_button'.length);
        $(dialogId).dialog('open');
    });
    
    // 6. download all/selection button
    $(".download_selection_button").click(function() {
        THIS._download.call(THIS, this);
    });
    
    // 7. extract params button
    $('.extract_param_button').click(function() {
        THIS._extractParams.call(THIS, this);
    });
    
    $('.extract_instrument_param_button').click(function() {
        THIS._extractInstrumentParams.call(THIS, this);
    });

    // 8. init result table when tab is first opened.
    $("#tabs_votables").bind('tabsselect', function(event, ui) {
        $(ui.panel).find(".resultTable").each(function() {
            if (!$(this).hasClass('dataTable') ) {
                var table = THIS._formatTable.call(THIS, this.id);
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
            } 
        });
    });
   
    // trigger selection of the first tab once the dialog is closed.
    var firstTab = $('#tabs_votables div').filter(':first');
    $("#tabs_votables").trigger('tabsselect', [{panel : firstTab }]);
    
    // 9. switch between tabular / plot view of a votable and init plot view on first invocation of the tab
    $(".tabs_votable_result").bind('tabsselect', function(event, ui) {
        if (ui.panel.id.indexOf('tab_votable_plot_') == 0) {
            $(".tabs_votable_result").unbind(event);
            var tmp = ui.panel.id.split('_');
            var resultId = tmp[3];
            var tableIndex = tmp[4];
            
            var containerName = 'table_' + resultId + '_' + tableIndex + '_plot';
            var additionalContainerName = 'table_' + resultId + '_' + tableIndex + '_plot_options';
            var chartTitleName = $(ui.panel).find('input[name="plotTitle"]').val();
            var catalogueName = chartTitleName.split('-')[1];
            // load plot data.
            $.ajax({
                url : './voTable/data?resultId=' + resultId + '&tableIndex=' + tableIndex
            }).done(function(jsonObject) {
                createHELIOChart(containerName, additionalContainerName, chartTitleName, jsonObject, catalogueName);                
            });
        }
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
    
    jQuery.ajax({
        url : './voTable/data?resultId=' + resultId + '&tableIndex=' + tableIndex,
        dataType : 'json',
        async : false,
        success : function(data, textStatus, jqrXHR) {
            dataTable =$("#"+tableName).dataTable(
              jQuery.extend({
                "bJQueryUI": true,
                "bAutoWidth": true,

                "bProcessing": true,
                "bDeferRender": true,

                "sScrollX": "100%",
//                "iScrollLoadGap": 50,
                "sScrollY": "500px",
//                "bScrollInfinite": true,
                "bScrollCollapse": true,
//                "sDom": "frtiS",
                
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
                },
                // add title to the table header. 
                "fnHeaderCallback": function( nHead, aData, iStart, iEnd, aiDisplay ) {
                    var cols = this.fnSettings().aoColumns;
                    for (var col in cols) {
                        var column = cols[col];
                        if (column.sDescription) {
                            var th = column.nTh;
                            $(th).attr('title', column.sDescription);
                        }
                    }
                }
                //"sScrollXInner": "100%",
              },
              data
            )
          );
        }
    });
    
    // 6. hack to format the headers of the datatables prooperly. not sure why this does not work initially.
    setTimeout(function() {
        dataTable.fnAdjustColumnSizing();
    }, 10);

    return dataTable;
};

/**
 * initialise some selected custom columns
 * @param dataTable the table object
 * @param tableName name of the table
 */
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
                alert("No column with name 'time_start' found");
                return;
            }
            if (time_end == -1) {
                time_end = time_start;
            }
            var times = dataTable.fnGetData($(this).closest('tr')[0], time_start);
            var timee = dataTable.fnGetData($(this).closest('tr')[0], time_end);
            //sendExamineEvent(times,timee);
            
            // adjust times
            var timeRange = new helio.TimeRange(times, timee);
            timeRange.incStartTime("hours", -6);
            timeRange.incEndTime("hours", 6);
            var dialog = new helio.TimeRangeDetailsDialog(null, 'timeRangeDetails', timeRange);
            dialog.show(function() {
                if (dialog.data.timeStart != times ||
                    dialog.data.timeEnd   != timee) {
                    var timeRanges = new helio.TimeRanges('datacart', 'extracted');
                    timeRanges.timeRanges.push(new helio.TimeRange(dialog.data.timeStart, dialog.data.timeEnd));
                    THIS._showExtractParamsDialog.call(THIS, timeRanges, 
                        "<p><b>You modified the time range.</b> Do you want to store it to the data cart? " +
                        "Click <code>Ok</code> to store or <code>Cancel</code> to dismiss.</p>");
                }
            });
            return false;
        };
    })(dataTable));
};

/**
 * Download all/selected files
 * @param {Node} source the source button that called this function
 */
helio.VOTableResult.prototype._download = function(source){
    // get the data table
    /(\d+)/.exec(source.id);
    var id = RegExp.$1;
    // get the data table
    var dataTable = this._resultTables[id];

    var settings = dataTable.fnSettings();
    
    // search the url location
    var downloadUrlPos = -1;
    var instrumentNamePos = -1;
    for(var j = 0;j< settings.aoColumns.length;j++){
        if($.trim(settings.aoColumns[j].sTitle) == 'url'){
            downloadUrlPos=j;
        } else if($.trim(settings.aoColumns[j].sTitle) == 'instrument_name'){
            instrumentNamePos=j;
        }
    }//end j

    var found = false;
    var download_list = [];
    var instrumentName = 'undefined_instrument';
    if (downloadUrlPos >= 0) {
        dataTable.find(".row_selected").each(function(){
            download_list.push($(this).children().eq(downloadUrlPos).find('a').attr('href'));
            if (instrumentNamePos >= 0) {
                instrumentName = $(this).children().eq(instrumentNamePos).html();
            }
            found = true;
        });
        
        // add all, if nothing selected 
        if(!found){
            var nNodes = dataTable.fnGetNodes();
            for(var node in nNodes){
                download_list.push($(nNodes[node]).children().eq(downloadUrlPos).html());
                found = true;
            }
        }
    }
    
    if (!found) {
        download_list.push("Nothing found to download");
    }
    
    var recipe =  window.open('','_blank','width=600,height=700,scrollbars=yes,location=no,status=no');
    
    var htmlHead = '<html><head><title>Helio Downloads</title>' +
                   '<style type="text/css">' +
                   'h1 {font-family:verdana,arial,sans-serif; font-size:14pt}' +
                   '.script {background-color: #EEEEEE; border: 1px solid black; padding: 10px;}' +
                   '</style>' +
                   '</head><body>';
    var htmlFoot = '</body></html>';
    var urlList = '<h1>List of URLs to download</h1>' + 
                  '<p>You can use a download manager to download the following links. </p><ul>' +
                  '<li>For OS X Safari: <a href="http://www.igetter.net/" target="_blank">iGetter</a></li>' +
                  '<li>For Firefox: <a href="http://www.downthemall.net/" target="_blank">DownThemAll</a></li>' +
                  '<li>For Google Chrome: <a href="http://monadownloadmaster.blogspot.ch/" target="_blank">Download Master</a></li>' +
                  '<li>For Internet Explorer: use the built-in download manager</li>' +
                  '</ul><pre class="script">';
    for (var i=0; i< download_list.length; i++) {
        urlList += '<a href="' + download_list[i] + '" target="_blank">' + download_list[i] + '</a>\n'; 
    } 
    urlList += '</pre>\n';

    // the curl script
    var curlScript = '';
    if (found) {
        curlScript = '<h1><code>cURL</code> script for Mac OS X and Linux users</h1>' + 
                     '<p>Copy and paste the following script to your OS X shell.</p>' + 
                     '<pre class="script">' +
                     'mkdir ' + instrumentName + '\n' +
                     'cd ' + instrumentName + '\n' +
                     'curl -O ' + download_list.join('\ncurl -O ') + '\n' +
                     'cd ..' +
                     '</pre>\n';
    }
    
    // the wget script
    var wgetScript = '';
    if (found) {
        wgetScript = '<h1><code>wget</code> script for Linux users</h1>' + 
                     '<p>Copy and paste the following script to your Linux shell.</p>' + 
                     '<pre class="script">' +
                     'mkdir ' + instrumentName + '\n' +
                     'cd ' + instrumentName + '\n' +
                     'wget ' + download_list.join('\nwget ') + '\n' +
                     'cd ..' +
                     '</pre>\n';
    }
    
    // Windows
    var windowsScript = '';
    if (found) {
        windowsScript = '<h1>Guidance for Windows users</h1>' + 
                     '<p>Microsoft Windows does not provide a suitable equivalent for wget or curl.</p>'+
                     '<p>Thus we suggest to install <a href="http://gnuwin32.sourceforge.net/packages/wget.htm" target="_blank">wget for Windows</a>.</p>' + 
                     '\n';
    }

    
    recipe.document.open();
    recipe.document.write(htmlHead);
    recipe.document.write(urlList);
    recipe.document.write(curlScript);
    recipe.document.write(wgetScript);
    recipe.document.write(windowsScript);
    recipe.document.write(htmlFoot);
    recipe.document.close();
};

/**
 * Extract selected time ranges.
 * @param {Node} source the source button that called this function
 */
helio.VOTableResult.prototype._extractParams = function(source) {
    // create a new data object.
    var timeRanges = new helio.TimeRanges('datacart', 'extracted');
    
    // find the id of the currently selected resource table
    /(\d+)/.exec(source.id);
    var id = RegExp.$1;
    
    // get the data table
    var dataTable = this._resultTables[id];
    // find the position of the time range columns we are interested in.
    var settings = dataTable.fnSettings();
    var time_start = -1;
    var time_end = -1;
    
    // get start and end end time position
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
 
        timeRanges.timeRanges.push(new helio.TimeRange(times, timee));
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

/**
 * Extract selected instruments.
 * @param {Node} source the source button that called this function
 */
helio.VOTableResult.prototype._extractInstrumentParams = function(source) {
    // create a new data object.
    var data = new helio.Instrument('datacart', 'extracted');

    // find the id of the currently selected resource table
    /(\d+)/.exec(source.id);
    var id = RegExp.$1;
    
    // get the data table
    var dataTable = this._resultTables[id];
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
    
    // handle filtering
    $("#instrument_filter").tabs();
    
    $("input:checkbox").change(function(){
    	var checkboxColumn = $(this).attr("column");
    	
    	var filter_expression = "";
    	
    	var className = $(this).attr("class");

    	// check or uncheck all subelements of photons, particles and fields and remove filters if necessary
    	// subclasses always end with "Type"
    	if (className != null && className.substring(0, 9) == "obsEntity" && className.substring(className.length - 4, className.length) != "Type") {
    		var classNameType = "." + className + "Type";
    		if ($(this).attr("checked")) {
    			$(classNameType).each(function(){
    				if ($(this).attr("checked")) {
	    				table.fnFilter("", $(this).attr("column"), true);
    				}
    				$(this).attr("checked", true);
    			});
    		}
    		else {
    			$(classNameType).each(function(){
    				if ($(this).attr("checked")) {
	    				$(this).attr("checked", false);
	    				table.fnFilter("", $(this).attr("column"), true);
    				}
    			});
    		}
    	}
    	
    	// check or uncheck all subelements of visible (helper attribute: "filterClass")
//    	if ($(this).attr("filterClass") == "visible") {
//    		if ($(this).attr("checked")) {
//    			$('input[filterClass="visibleType"]').each(function() {
//    				$(this).attr("checked", true);
//    			});
//    		}
//    		else {
//    			$('input[filterClass="visibleType"]').each(function() {
//    				$(this).attr("checked", false);
//    			});
//    		}
//    	}
    	
    	var checked = new Array();
    	
	    // filters the table
	    $("input:checked").each(function(){
		    	if ($(this).attr("name") != "instrumentType" && $(this).attr("name") != "true") {
		    		checked.push($(this).attr("name"));
		    	}
		    	
		        // create filter expression
		        if($(this).attr("column") == checkboxColumn) {
		        	if ($(this).attr("name") == "uv") {
		        		// hack because EUV contains the word UV
		        		filter_expression = (filter_expression == "" ? "" : (filter_expression + "|")) + "(^UV)";
		        	}
		        	else {
		        		filter_expression = (filter_expression == "" ? "" : (filter_expression + "|")) + "(" + $(this).attr("name") + ")";
		        	}
		        }
	    });

	    table.fnFilter(filter_expression, checkboxColumn, true);
    	
	    // filter text isn't good but at least it displays all set filters so you can see them in all tabs
	    if (checked.length > 0) {
	    	$(".filterInstrumentsText").text("Folowing filters are set: " + checked.join(", "));
	    }
	    else {
	    	$(".filterInstrumentsText").text("All lists are shown.");
	    }
    });
    
    $("input:radio").change(function(){
        var checkboxColumn = $(this).attr("column");
        var filter_expression = "";
        filter_expression = (filter_expression == "" ? "" : (filter_expression + "|")) + "(" + $(this).attr("value") + ")";
        table.fnFilter(filter_expression, checkboxColumn, true);
    });
    
    //function to filter out the data table based on what's being check in the checkboxes
//    $("input:checkbox").change(function(){
//        // Photons
//        // obsEntityPhotons = super filter
//        // obsEntityPhotonsType = type filter
//        
//        // super filter clicked
//        if($(this).hasClass("obsEntityPhotons")) {
//            if(!$(this).attr("checked")) {
//                $(".obsEntityPhotons").attr("checked", false); //uncheck hidden checkbox
//            }
//            
//            var typesChecked = false;
//            // if any type is checked, uncheck it
//            $(".obsEntityPhotonsType").each(function(){
//                if($(this).attr("checked")) {
//                    $(this).attr("checked", false);
//                    typesChecked = true;
//                    THIS._filter.call(THIS, $(this), table);
//                }
//            });
//            
//            // if no type is checked, check all
//            if(!typesChecked && $(this).attr("checked")) {
//                $(".obsEntityPhotonsType").each(function(){
//                    // only check visible checkboxes, otherwise they can't be reset again
//                    if ($(this).is(":visible")) {
//                        $(this).attr("checked", true);
//                        THIS._filter.call(THIS, $(this), table);
//                    }
//                });
//            }
//        }
//        // type filter clicked
//        else if($(this).hasClass("obsEntityPhotonsType")) {
//            // check super filter
//            if($(this).attr("checked")) {
//                $(".obsEntityPhotons").attr("checked", true);
//                THIS._filter.call(THIS, $($(".obsEntityPhotons").get(-1)), table);
//            }
//            // if no type filter is selected remove super filter
//            else {
//                var noTypeChecked = true;
//                // prove if no type filter is checked
//                $(".obsEntityPhotonsType").each(function(){
//                    if($(this).attr("checked")) {
//                        noTypeChecked = false;
//                    }
//                });
//                if (noTypeChecked) {
//                    $(".obsEntityPhotons").attr("checked", false);
//                    THIS._filter.call(THIS, $($(".obsEntityPhotons").get(-1)), table);
//                }
//            }
//        }
//        
//        // Particles
//        if($(this).hasClass("obsEntityParticles")) {
//            if(!$(this).attr("checked")) {
//                $(".obsEntityParticles").attr("checked", false); //uncheck hidden checkbox
//            }
//            
//            var typesChecked = false;
//            $(".obsEntityParticlesType").each(function(){
//                if($(this).attr("checked")) {
//                    $(this).attr("checked", false);
//                    typesChecked = true;
//                    THIS._filter.call(THIS, $(this), table);
//                }
//            });
//            
//            if(!typesChecked && $(this).attr("checked")) {
//                $(".obsEntityParticlesType").each(function(){
//                    if ($(this).is(":visible")) {
//                        $(this).attr("checked", true);
//                        THIS._filter.call(THIS, $(this), table);
//                    }
//                });
//            }
//        }
//        else if($(this).hasClass("obsEntityParticlesType")) {
//            if($(this).attr("checked")) {
//                $(".obsEntityParticles").attr("checked", true);
//                THIS._filter.call(THIS, $($(".obsEntityParticles").get(-1)), table);
//            }
//            else {
//                var noTypeChecked = true;
//                $(".obsEntityParticlesType").each(function(){
//                    if($(this).attr("checked")) {
//                        noTypeChecked = false;
//                    }
//                });
//                if (noTypeChecked) {
//                    $(".obsEntityParticles").attr("checked", false);
//                    THIS._filter.call(THIS, $($(".obsEntityParticles").get(-1)), table);
//                }
//            }
//        }
//
//        // Fields
//        if($(this).hasClass("obsEntityFields")) {
//            if(!$(this).attr("checked")) {
//                $(".obsEntityFields").attr("checked", false); //uncheck hidden checkbox
//            }
//            
//            var typesChecked = false;
//            $(".obsEntityFieldsType").each(function(){
//                if($(this).attr("checked")) {
//                    $(this).attr("checked", false);
//                    typesChecked = true;
//                    THIS._filter.call(THIS, $(this), table);
//                }
//            });
//            
//            if(!typesChecked && $(this).attr("checked")) {
//                $(".obsEntityFieldsType").each(function(){
//                    if ($(this).is(":visible")) {
//                        $(this).attr("checked", true);
//                        THIS._filter.call(THIS, $(this), table);
//                    }
//                });
//            }
//        }
//        else if($(this).hasClass("obsEntityFieldsType")) {
//            if($(this).attr("checked")) {
//                $(".obsEntityFields").attr("checked", true);
//                THIS._filter.call(THIS, $($(".obsEntityFields").get(-1)), table);
//            }
//            else {
//                var noTypeChecked = true;
//                $(".obsEntityFieldsType").each(function(){
//                    if($(this).attr("checked")) {
//                        noTypeChecked = false;
//                    }
//                });
//                if (noTypeChecked) {
//                    $(".obsEntityFields").attr("checked", false);
//                    THIS._filter.call(THIS, $($(".obsEntityFields").get(-1)), table);
//                }
//            }
//        }
//        
//        //console.log("change: " + $(this).attr("name") + $(this).attr("column"));
//         
//        //THIS._filter.call(THIS, $(this), table);
//    });

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
	debugger;
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