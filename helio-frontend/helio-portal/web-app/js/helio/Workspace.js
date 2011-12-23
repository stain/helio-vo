/**
 * Main data structure that handles the input and output forms 
 * @returns a collection of methods holding the different input forms.
 */
function Workspace() {
    /**
     * Sections used in the UI
     */
    var divisions = new Object();
    
    /**
     * private helper function to ingest the divisions.
     */
    var ingestDivision = function(keyName,divisionName){
        divisions[keyName] = $(divisionName).html();
        $(divisionName).remove();
    };

    /**
     * Data object holding the input forms.
     * TODO: move to GSP.
     */
    return {
        //Initializes by filling the @divisions map with the html contained in the sections, also removes the html to not overlap ids
        init: function() {
            if (typeof console!="undefined")console.info("Workspace :: init");
            ingestDivision("hec_extended","#displayableHEC_extended");
            ingestDivision("task_chart","#displayableTaskChart");
            ingestDivision("task_hec","#displayableHEC");
            ingestDivision("task_ics","#displayableICS");
            ingestDivision("input_time","#displayableInputTime");
            ingestDivision("input_instruments","#displayableInputInstruments");
            ingestDivision("input_event","#displayableInputEvent");
            ingestDivision("input_result","#displayableInputResult");
            ingestDivision("input_datamining","#displayableInputDataMining");
            ingestDivision("task_ils","#displayableILS");
            ingestDivision("task_dpas","#displayableDPAS");
            ingestDivision("task_upload","#displayableTaskUpload");
            ingestDivision("loading","#displayableOnLoading");
            ingestDivision("error","#displayableError");
            ingestDivision("splash","#displayableSplash");
            ingestDivision("selected_result","#displayableSeletedResult");
            ingestDivision("task_searchEvents","#displayableTaskSearchEvents");
            ingestDivision("task_searchInstLoc","#displayableTaskSearchInstLoc");
            ingestDivision("task_searchInstCap","#displayableTaskSearchInstCap");
            ingestDivision("task_searchData","#displayableTaskSearchData");
            ingestDivision("task_datamining","#displayableTaskDataMining");
            ingestDivision("task_context","#displayableTaskContext");
            ingestDivision("input_event_view","#displayableInputEventView");

            window.workspace.setDisplay("splash");
            
        },
        
        /**
         * form actions to select instruments
         * @returns instrument selection form.
         */
        instrument_input_form: function(){
            $("#dialog-message").remove();
            var div =$('<div></div>');
            div.attr('id','dialog-message');
            div.attr('title','Instrument Selection');
            var html = divisions["input_instruments"];
            div.append(html);
            $("#testdiv").append(div);
            $("#input_table").dataTable( {
                "bSort": false,
                "bInfo": true,
                "sScrollY": "300px",
                "bPaginate": false,
                "bJQueryUI": true,
                "sScrollX": "300px",
                "sScrollXInner": "100%",
                "sDom": '<"H">t<"F">'
            });
            $("#extra_list_form").html(($("#extra_list").html()));
            var tempextralist =$("#extra_list").html();
            $("#extra_list").html("");

            $("#extra_list_form li").each(function(){
                $("#input_table td[internal='"+$(this).attr("internal")+"']").parent().addClass('row_selected');

            });
            $("#input_filter").keyup(function(){
                $("#input_table").dataTable().fnFilter($(this).val());
            });

            $('#input_table tr').click( function() {
                var row =$.trim($(this).find('td').attr("internal"));
                if ( $(this).hasClass('row_selected') ){
                    $(this).removeClass('row_selected');
                     
                    $("#extra_list_form li[internal='"+row+"']").remove();
                }
                else{
                    $(this).addClass('row_selected');
                    $("#extra_list_form").append("<li internal='"+row+"'>'"+row+"'<input id='"+row+"' type='hidden'  name='extra' value='"+row+"'/></li>");
                }
            });
            
            formatButton($(".custom_button"));
            $('#dialog-message').dialog({
                modal: true,
                height:530,
                width:700,
                close: function(){

                    $("#dialog-message").remove();
                },
                buttons: {
                    Help: function(){
                        $('#help_overlay h3').text("Instrument Selection Form");
                        $('#help_overlay p').text("Select the instruments you are interested in by clicking the names, once you are confortable with your selection, click Ok");
                        $('#help_overlay').attr('title','Click to unblock').click($.unblockUI);
                        $.blockUI({
                            message: $('#help_overlay')
                        });
                        $('.blockOverlay').attr('title','Click to unblock').click($.unblockUI);
                    },
                    Cancel: function() {
                        $("#extra_list").html(tempextralist);
                        $("#dialog-message").dialog( "close" );
                    },
                    Ok: function() {
                        if($("#extra_list_form li").length <=0){
                            alert("Please make a selection before pressing Ok");
                            return false;
                        }
                        $("#extra_list").html(($("#extra_list_form").html()));

                        
                        $("#instruments_drop").attr('src','../images/helio/circle_inst.png');
                        $("#instruments_drop").addClass('drop_able');
                        $("#dialog-message").dialog( "close" );
                        $("#dialog-message").remove();
                        window.workspace.evaluator();

                    }
                }
            });
        },
        
        /**
         * Form actions to select event list
         * @returns
         */
        event_input_form: function(){

            $("#dialog-message").remove();
            var div =$('<div></div>');
            div.attr('id','dialog-message');
            div.attr('title','Event Parameters');
            var html = divisions["input_event"];

            div.append(html);
            $("#testdiv").append(div);
            $("#input_table").dataTable( {
                "bSort": false,
                "bInfo": true,
                "sScrollY": "300px",
                "bPaginate": false,
                "bJQueryUI": true,
                "sScrollX": "500px",
                "sScrollXInner": "100%",
                "sDom": '<"H">t<"F">'
            });
            $("#input_table").dataTable().fnSetColumnVis( 0, false );
            $("#input_table").dataTable().fnSetColumnVis( 6, false );
            $("#input_table").dataTable().fnSetColumnVis( 7, false );
            $("#input_table").dataTable().fnSetColumnVis( 8, false );
            $("#input_table").dataTable().fnSetColumnVis( 9, false );
            $("#input_table").dataTable().fnSetColumnVis( 10, false );
            $("#input_table").dataTable().fnSetColumnVis( 11, false );
            $("#input_table").dataTable().fnSetColumnVis( 12, false );
            $("#input_table").dataTable().fnSetColumnVis( 13, false );
            $("#input_table").dataTable().fnSetColumnVis( 14, false );
            $("#input_table").dataTable().fnSetColumnVis( 15, false );
            
            // is called when a filter checkbox is clicked
            $(".checkFilter").change(function(){
            	// uncheck "Show all" checkbox
            	$("#checkAll").removeAttr("checked");
            	
				$("#input_table").dataTable().fnFilter("", 15, true);
            	
                var checkboxColumn = $(this).attr("column");
                var filter_expression = "";
                
                var eventCounter = 0;
                var locationCounter = 0;
                var observationCounter = 0;
                
                // clear filterText <td>
                $("#filterText").html("");
                
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
                });

                // create filter expression
                if($(this).attr("column") == checkboxColumn) {
                    filter_expression = (filter_expression == "" ? "" : (filter_expression + "|")) + "(" + $(this).attr("value") + ")";
                }
                
                // creates the new filtered table
                $("#input_table").dataTable().fnFilter(filter_expression, checkboxColumn, true);
            });
            
            $("#checkAll").change(function(){
            	if ($(this).attr("checked")) {
            		$("#input_table").dataTable().fnFilter("", 15, true);
        			$(".checkFilter").each(function(){
        				// uncheck all filter checkboxes
        				$(this).removeAttr("checked");
        				// remove all filters from dataTable
        				$("#input_table").dataTable().fnFilter("", $(this).attr("column"), true);
        			});
        			
    				$("#obsBoth").attr("checked", "checked");
    				$("#filterText").html("All flare lists are shown.");
        		}
        		else {
        			$("#input_table").dataTable().fnFilter("never appearing filter text", 15, true);
        			$("#filterText").html("No flare lists are shown.");
        		}
            });
            
            $("input:radio").change(function(){
                var checkboxColumn = $(this).attr("column");
                var filter_expression = "";
                
                $("input:checked").each(function(){
                    if($(this).attr("column") == checkboxColumn)
                        filter_expression = (filter_expression == "" ? "" : (filter_expression + "|")) + "(" + $(this).attr("value") + ")";
                });

                $("#input_table").dataTable().fnFilter(filter_expression, checkboxColumn, true);
                
            });

            $("#extra_list_form").html(($("#extra_list").html()));
            var tempextralist =$("#extra_list").html();
            $("#extra_list").html("");
       
            $("#extra_list_form li").each(function(){
                $("#input_table td[internal='"+$(this).attr("internal")+"']").parent().addClass('row_selected');
            });
            $("#input_filter").keyup(function(){
                $("#input_table").dataTable().fnFilter($(this).val());
            });

            $('#input_table tr').click( function() {
                var row =$.trim($(this).find('td').attr("internal"));
                
                if ( $(this).hasClass('row_selected') ){
                    $(this).removeClass('row_selected');
                    $("#extra_list_form li[internal='"+row+"']").remove();
                }
                else{
                    $(this).addClass('row_selected');
                    var aData = $("#input_table").dataTable().fnGetData( this );
                    $("#extra_list_form").append("<li id='"+aData[0]+"' internal='"+row+"'>'"+row+"'<input type='hidden'  name='extra' value='"+aData[0]+"'/></li>");//<div class='custom_button input_time_advanced'>Advanced</div>
                    $(".input_time_advanced").unbind();
                    formatButton($(".custom_button"));
                    $(".input_time_advanced").click(function(){
                        getAdvancedFields($("#service_name").val(),$(this).parent().find('input').val());
                    });
                }
            });
            formatButton($(".custom_button"));
            $('#dialog-message').dialog({
                modal: true,
                height:530,
                width:900,
                close: function(){
                    $("#dialog-message").remove();
                },
                buttons: {
                    Help: function(){
                        $('#help_overlay h3').text("Event List Selection Form");
                        $('#help_overlay p').text("Select the catalogues you are interested in by clicking the names, once you are confortable with your selection, click Ok");
                        $('#help_overlay').attr('title','Click to unblock').click($.unblockUI);
                        $.blockUI({
                            message: $('#help_overlay')
                        });
                        $('.blockOverlay').attr('title','Click to unblock').click($.unblockUI);
                    },
                    Cancel: function(){
                        $("#extra_list").html(tempextralist);
                        $("#dialog-message").dialog( "close" );

                    },
                    Ok: function() {
                        if($("#extra_list_form li").length <=0){
                            alert("Please make a selection before pressing Ok");
                            return false;
                        }
                        if($("#extra_list_form").html()!=""){
                            $("#event_drop").attr('src','../images/helio/circle_event.png');
                            $("#event_drop").addClass('drop_able');
                        }
                        else{
                            $("#event_drop").attr('src','../images/helio/circle_event_grey.png');
                            $("#event_drop").removeClass('drop_able');
                        }
                        
                        $(".input_time_advanced").remove();
                        $("#extra_list").html(($("#extra_list_form").html()));
                        
                        $("#dialog-message").dialog( "close" );
                        $("#dialog-message").remove();
                        window.workspace.evaluator();

                    }
                }
            });
            $("#input_table").dataTable().fnDraw();
        },
        
        /**
         * Form actions for time input
         * @returns
         */
        time_input_form: function(){

            $("#dialog-message").remove();
            var div =$('<div></div>');
            div.attr('id','dialog-message');
            div.attr('title','Date Selection');
            var html = divisions["input_time"];
            div.append(html);
            $("#testdiv").append(div);
            var date_range_list = $("#input_time_range_list");

            date_range_list.html("");
            //var num =date_range_list.data("ranges");
            /**
             * private function to format the date range.
             */
            var _formatDateRange =function(id){
                $('#minDate'+id).datetimepicker("destroy");
                $('#maxDate'+id).datetimepicker("destroy");
                
                var formats = function(){
                	return {
                	    yearRange: '1970:2011',
                        dateFormat: 'yy-mm-dd',
                        changeMonth: true,
                        showOn: "both",
                        showSecond: true,
                        timeFormat: 'hh:mm:ss',
                        separator: 'T',
                        showButtonPanel: true,
                        buttonImageOnly: true,
                        buttonImage: "../images/icons/calendar.gif",
                        changeYear: true,
                        numberOfMonths: 1}
                }
                
                /**
                 *	formatMinDate and formatMaxDate format the dates
                 * 	and correct the dates if the user enters a bigger minDate
                 * 	then maxDate or a smaller maxDate then minDate
                 */
                
                var formatMinDate = new formats();
                formatMinDate.onClose = function(selectedDate) {
                	$(this).blur();
                	var endDateTextBox = $('#maxDate' + id);
                	if (endDateTextBox.val() != '') {
                		var testStartDate = new Date(selectedDate);
                		var testEndDate = new Date(endDateTextBox.val());
                		if (testStartDate > testEndDate)
                			endDateTextBox.val(selectedDate);
                	}
                	else {
                		endDateTextBox.val(selectedDate);
                	}
                	$.cookie("minDate",$("#minDate"+id).val(),{
                        expires: 30
                    });
                    $.cookie("maxDate",$("#maxDate"+id).val(),{
                        expires: 30
                    });
                }
                
                var formatMaxDate = new formats(); 
                formatMaxDate.onClose = function(selectedDate) {
                	$(this).blur();
                    var startDateTextBox = $('#minDate' + id);
                    if (startDateTextBox.val() != '') {
                        var testStartDate = new Date(startDateTextBox.val());
                        var testEndDate = new Date(selectedDate);
                        if (testStartDate > testEndDate)
                            startDateTextBox.val(selectedDate);
                    }
                    else {
                        startDateTextBox.val(selectedDate);
                    }
                    $.cookie("minDate",$("#minDate"+id).val(),{
                        expires: 30
                    });
                    $.cookie("maxDate",$("#maxDate"+id).val(),{
                        expires: 30
                    });
                }
                
                $( "#minDate"+id ).datetimepicker(formatMinDate);
                $( "#maxDate"+id ).datetimepicker(formatMaxDate);
            }
         
            /**
             * function to create date range.
             * @param num ???
             * @returns ???
             */
            var _createDateRange = function(num){
                var tr = $('<tr id="input_time_range_'+num+'"></tr>');
                var td = $('<td align="center" valign="center">Range:</td>');
                tr.append(td);
                td = $('<td align="center" valign="center"> <input index="'+num+'" tabindex="-1" size="25" type="text" id="minDate'+num+'" name="minDate" value="'+$.cookie("minDate")+'"/></td>');
                tr.append(td);
                td = $('<td align="center" valign="center"> <input index="'+num+'" tabindex="-1" size="25 type="text" id="maxDate'+num+'" name="maxDate" value="'+$.cookie("maxDate")+'"/></td>');
                tr.append(td);
                td = $('<td><div></div></td>');
                tr.append(td);
                td = $('<td><div class="input_time_range_remove">Delete</div></td>');
                tr.append(td);
                date_range_list.append(tr);
                _formatDateRange(num);
                $(".input_time_range_remove").unbind();
                $(".input_time_range_remove").button();
                $(".input_time_range_remove").click(function(){
                    if($("#input_time_range_list tr").length ==2){
                        $(this).closest('tr').remove();
                        $(".input_time_range_remove").button({
                            'disabled':true
                        });
                    }else if($("#input_time_range_list tr").length >2){
                        $(this).closest('tr').remove();
                    }
                });
            };
          
            var iterator =0;

            $("#time_area").find('tr').each(function(){
                iterator++;
                date_range_list.data("ranges",iterator);
                _createDateRange(iterator);
                $(this).find("input").each(function(){
                    if($(this).attr("name")=="maxDate")$("#maxDate"+iterator).val($(this).val());
                    if($(this).attr("name")=="minDate")$("#minDate"+iterator).val($(this).val());
                });
            });
            if(iterator == 0){
                date_range_list.data("ranges",1);
                _createDateRange(1);
                $(".input_time_range_remove").button({
                    'disabled':true
                });
            }
            if(iterator == 1){
                $(".input_time_range_remove").button({
                    'disabled':true
                });
            }
            
            $("#input_time_range_button").click(function(){
                var num =date_range_list.data("ranges");
                date_range_list.data("ranges",num+1);
                _createDateRange(num+1);
                $(".input_time_range_remove").button({
                    'disabled':false
                });
        
            });
            formatButton($(".custom_button"));
            //$("#input_time_range_button").button({ disabled: true });
            $('#dialog-message').dialog({
                modal: true,
                height:530,
                width:700,
                open: function(event, ui) {
                   
                },

                buttons: {
                    Help: function(){
                        $('#help_overlay h3').text("Time Range Selection");
                        $('#help_overlay p').text("Fill out the time ranges you are interested in and click Ok");
                        $('#help_overlay').attr('title','Click to unblock').click($.unblockUI);
                        $.blockUI({
                            message: $('#help_overlay')
                        });
                        $('.blockOverlay').attr('title','Click to unblock').click($.unblockUI);
                    },
                    Cancel: function() {
                        $("#dialog-message").dialog( "close" );
                        $("#dialog-message").remove();
                    },

                    Ok: function() {
                        //Validate date ranges and if and error is found, notify the user and stop thread
                        var flag =true;
                        date_range_list.find("tr").each(function(index,value){
                            if(!date_form_validate($(this).find('input').attr("index"))){
                                alert("A date entered is invalid, please check your input");
                                flag= false;
                            }
                        });
                        if(!flag)return false;


                        var table =$("<table>");
                        var itr = 1;

                        date_range_list.find("tr").each(function(){
                            var tr = $('<tr></tr>');
                            while(!$("#minDate"+itr).length){
                                itr++;
                                if(itr==100)continue;
                            }
                            tr.append("<td><b>Range:</b></td>"+
                                "<td>"+$("#minDate"+itr).val()+"</td>"+
                                
                                "<td>--</td><td>"+$("#maxDate"+itr).val()+"</td>");
                                
                            tr.append("<td style='display:none'><input type='hidden' name='maxDate' value='"+$("#maxDate"+itr).val()+"'></td>");
                            tr.append("<td style='display:none'><input type='hidden' name='minDate' value='"+$("#minDate"+itr).val()+"'></td>");
                            
                            table.append(tr);
                            itr++;
                        });
                        $("#time_area").html(table);

                      
                        $("#time_drop").attr('src','../images/helio/circle_time.png');
                        $("#time_drop").addClass('drop_able');

                        formatButton($(".custom_button"));
                        
                        $("#dialog-message").dialog( "close" );
                        $("#dialog-message").remove();
                        window.workspace.evaluator();
                    }
                }
            });
        },  // end time-input-form
        
        /**
         * TODO: document
         * 
         * @returns
         */
        evaluator: function(){

            $("#displayableResult").html("");
            $("#result_button").show();
            $("#result_button").unbind();
            $("#result_area").html("Execute Query?");

            // cleanup data before sending it to the backend.
            var minDate= [];
            $("[name='minDate']").each(function() {
                minDate.push($(this).val());
            });
            var maxDate=  [];
            $("[name='maxDate']").each(function() {
                maxDate.push($(this).val());
            });
            
            var extra= [];
            $("[name='extra']").each(function() {
                extra.push($(this).val());
            });
                
            var serviceName =$("#service_name").val();
            
            if($("#task_name").length >0)setPreviousTaskState($("#task_name"),$("#HUID"),$("#query_form"));
            switch (serviceName) {
                case "DES":
                    if(minDate.length >0&& maxDate.length >0&& extra.length >0){
                        $("#result_overview").css("display","table");

                        $("#result_button").click(function(){
                            sendQuery(minDate, maxDate,serviceName, extra,$("[name='where']").val());
                        });
                    }
                    
                    break;
                case "context":
                    if(minDate.length >0&& maxDate.length >0){
                        $("#result_overview").css("display","table");
                        $("#result_button").click(function(){
                            sendQueryContext([minDate[0]], [maxDate[0]]);
                        });
                    }
                    break;
                case "HEC":
                    if(minDate.length >0&& maxDate.length >0&& extra.length >0){
                        $("#result_overview").css("display","table");
                        $("#result_button").click(function(){
                            sendQuery(minDate, maxDate,serviceName, extra);
                        });
                    }
                    break;
                case "ICS":
                    if(minDate.length >0&& maxDate.length >0){
                        if(minDate.length > 1){
                            var div =$('<div></div>');
                            div.attr('id','dialog-message');
                            div.attr('title','Warning');
                            var message = "You have entered multiple time ranges. The system will merge your search into a single range with your overall minimum and maximum time";
                            div.append('<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 50px 0;"></span>'+message+'</p>');
                            $("#testdiv").append(div);

                            $('#dialog-message').dialog({
                                modal: true,
                                buttons: {
                                    Ok: function() {
                                        
                                        $( this ).dialog( "close" );
                                        $("#result_overview").css("display","table");
                                        $("#result_button").click(function(){
                                            sendQuery([minDate[0]], [maxDate[0]] ,serviceName, extra);
                                        });
                                    },
                                    Cancel: function() {
                                        $( this ).dialog( "close" );
                                    }
                                }
                            });

                        }else{
                            $("#result_overview").css("display","table");
                            $("#result_button").click(function(){
                                sendQuery([minDate[0]], [maxDate[0]] ,serviceName, extra);
                            });
                        }
                    }
                    break;
                case "ILS":
                    if(minDate.length >0&& maxDate.length >0){
                        if(minDate.length > 1){
                            var div =$('<div></div>');
                            div.attr('id','dialog-message');
                            div.attr('title','Warning');
                            var message = "You have entered multiple time ranges. The system will merge your search into a single range with your overall minimum and maximum time";
                            div.append('<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 50px 0;"></span>'+message+'</p>');
                            $("#testdiv").append(div);

                            $('#dialog-message').dialog({
                                modal: true,
                                buttons: {
                                    Ok: function() {
                                        $( this ).dialog( "close" );
                                        $("#result_overview").css("display","table");
                                        $("#result_button").click(function(){
                                            sendQuery([minDate[0]], [maxDate[0]] ,serviceName, extra);
                                        });
                                    },
                                    Cancel: function() {
                                        $( this ).dialog( "close" );
                                    }
                                }
                            });
                        }else{
                            $("#result_overview").css("display","table");
                            $("#result_button").click(function(){
                                sendQuery([minDate[0]], [maxDate[0]] ,serviceName, extra);
                            });
                        }
                    }
                    break;
                case "DPAS":
                    
                    if(minDate.length >0&& maxDate.length >0&& extra.length >0){
                        $("#result_overview").css("display","table");
                        $("#result_button").click(function(){
                            sendQuery(minDate, maxDate,serviceName, extra);
                        });
                    }
                    break;
                default:
                    break;
            }
        },  // end evaluator

        /**
         * get all registered divisions
         * @returns the registered divisions as list.
         */
        getDivisions: function(){
            return divisions;
        },
        
        /**
         * Mark division with given key as active.
         * @param key the key of the division to activate.
         * @returns nothing
         */
        setDisplay: function(key){
            if (typeof console!="undefined")console.info("Workspace :: setDisplay -> " +key);
            this.clear();

            // recreate droppable-inner if needed.
            if ($("#droppable-inner").length == 0) {
                $("#content").html('<div id="droppable-inner" class="candybox"></div>');
            }
            var newDiv = $('<div></div>');
            if(divisions[key] == null){
                key = "error";
            }//end if
            
            newDiv.html(divisions[key]);
            newDiv.css("display","block");
            newDiv.attr("id","task");
            newDiv.attr("class","displayable");
            $("#droppable-inner").append(newDiv);
            
            getPreviousTaskState($("#task_name"),$("#HUID"),key);
        },
         
        /**
         * ???
         * @param taskName
         * @returns
         */
        createItem: function(taskName){
            if (typeof console!="undefined")console.info("Workspace :: createItem -> " +taskName);
            formatButton($(".custom_button"));
            if($("#task_name").length >0) 
                setPreviousTaskState($("#task_name"),$("#HUID"),$("#query_form"));
            this.setDisplay(taskName);
        },
        
        /**
         * Clear the workspace, i.e. remove any dialog.
         * @returns nothing
         */
        clear: function() {
            if (typeof console!="undefined")console.info("Workspace :: clear");
            //$('#currentDisplay').fadeOut(1000,0);
            $("#task").remove();
            $(".displayable").css("display","none");
            $(".resCont").remove();
            $(".tooltip").css("display","none");
        }
    };
}
