function History() {
    var array=[];
    var limit = 13;
    var offset =0;
    var filter = "all";
    var current = 0;
    //var deleteParameter = false;

    return {
        instrument_input_form: function(selector){

            var holder= $('<div></div>');
            holder.html(selector.attr('inst_data'));
            $("#dialog-message").remove();
            var div =$('<div></div>');
            div.attr('id','dialog-message');
            div.attr('title','Instrument Selection');

            var html = window.workspace.getDivisions()["input_instruments"];
            div.append(html);

            $("#testdiv").append(div);
            $("#input_label_table").css('display',"block");
            $("#input_form_label").val(selector.closest("table").find(".inner_label td").html());
            // create the instrument input data table.
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
            
            $("#extra_list_form").html(holder.html());

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
                    $("#extra_list_form").append("<li internal='"+row+"'>'"+row+"'<input  id='"+row+"' type='hidden'  name='extra' value='"+row+"'/></li>");
                }
            } );
            formatButton($(".custom_button"))
            $('#dialog-message').dialog({
                modal: true,
                height:630,
                width:800,
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
                        $("#dialog-message").dialog( "close" );
                    },
                    Ok: function() {
                        if($("#extra_list_form li").length <=0){
                            alert("Please make a selection before pressing Ok");
                            return false;
                        }
                    
                        selector.attr('inst_data',$("#extra_list_form").html());
                        selector.data('inst_data',$("#extra_list_form").html());
                        selector.closest("table").find(".inner_label td").html($("#input_form_label").val());
                        selector.attr("title",$("#input_form_label").val());
                        saveHistoryBar();

                        $("#dialog-message").dialog( "close" );
                        $("#dialog-message").remove();
                    }
                }
            });
        },
        event_input_form: function(selector){
            var holder = $('<div></div>');
            var buffer = $('<div></div>');
            
            buffer.html(selector.attr('event_data'));

            // fill all event data from jQuery buffer element into holder	
            buffer.find("input[name='extra']").each(function() {  	
	        	holder.append("<li id='" + $(this).attr("value") + "' internal='" + $(this).attr("internal") + "'>'" + $(this).attr("internal") +
	        			"'<input type='hidden'  name='extra' value='" + $(this).attr("value") + "' internal='" + $(this).attr("internal") +  "'/>" +
	        			"<div style='float:right; height: 16px; width: 16px;' class='removeList ui-state-default ui-corner-all' onclick='Workspace.removeList_click(this)'>" +
	        			"<span class='ui-icon ui-icon-close'></span></div></li>");	
            });
            
            $("#dialog-message").remove();
            var div =$('<div></div>');
            div.attr('id','dialog-message');
            div.attr('title','Event Parameter');

            var html = window.workspace.getDivisions()["input_event"];

            div.append(html);
            $("#testdiv").append(div);
            $("#input_label_table").css('display',"block");
            $("#input_form_label").val(selector.closest("table").find(".inner_label td").html());
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

            $("#extra_list_form").html(holder.html());
            
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
                    
                    // create filter expression
                    if($(this).attr("column") == checkboxColumn) {
                        filter_expression = (filter_expression == "" ? "" : (filter_expression + "|")) + "(" + $(this).attr("value") + ")";
                    }
                });
                
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
                    $("#extra_list_form li[internal='" + row + "']").remove();
                }
                else{
                    $(this).addClass('row_selected');
                    var aData = $("#input_table").dataTable().fnGetData( this );
                    $("#extra_list_form").append("<li id='" + aData[0] + "' internal='" + row + "'>'" + row +
                    		"'<input type='hidden' name='extra' value='" + aData[0] + "' internal='" + row +  "'/>" +
            				"<div style='float:right; height: 16px; width: 16px;' class='removeList ui-state-default ui-corner-all' onclick='Workspace.removeList_click(this)'>" +
            				"<span class='ui-icon ui-icon-close'></span></div></li>");//<div class='custom_button input_time_advanced'>Advanced</div>
                    $(".input_time_advanced").unbind();
                    formatButton($(".custom_button"));
                    $(".input_time_advanced").click(function(){
                        getAdvancedFields($("#service_name").val(),$(this).parent().find('input').val());
                    });
                }
            	
//            	debugger;
//            	
//                var row =$.trim($(this).find('td').attr("internal"));
//                if ( $(this).hasClass('row_selected') ){
//                    $(this).removeClass('row_selected');
//
//                    $("#extra_list_form li[internal='"+row+"']").remove();
//                }
//                else{
//                    var aData = $("#input_table").dataTable().fnGetData( this );
//                    $("#extra_list_form").append("<li id='"+aData[0]+"' internal='"+row+"'>'"+row+"'<input type='hidden'  name='extra' value='"+aData[0]+"'/></li>");//<div class='custom_button input_time_advanced'>Advanced</div>
//                    $(this).addClass('row_selected');
//                }
            });
            formatButton($(".custom_button"))
            $('#dialog-message').dialog({
                modal: true,
                height:630,
                width:900,
                close: function(){
                    $("#dialog-message").remove();
                },
                buttons:{
                    Help: function(){
                        $('#help_overlay h3').text("Event List Selection Form");
                        $('#help_overlay p').text("Select the catalogues you are interested in by clicking the names, once you are confortable with your selection, click Ok");
                        $('#help_overlay').attr('title','Click to unblock').click($.unblockUI);
                        $.blockUI({
                            message: $('#help_overlay')
                        });
                        $('.blockOverlay').attr('title','Click to unblock').click($.unblockUI);
                    },
                    Cancel: function() {
                        $("#dialog-message").dialog( "close" );
                    },
                    Ok: function() {
                        if($("#extra_list_form li").length <=0){
                            alert("Please make a selection before pressing Ok");
                            return false;
                        }
                        
                        var inputList = $('<div></div>');
                        
                        // add selected inputs to inputList
                        $("#extra_list_form").find("input[name='extra']").each(function() {
                        	inputList.append($(this));
                        });  			
            			
                        selector.attr('event_data', inputList.html());
                        selector.data('event_data', inputList.html());
                        selector.closest("table").find(".inner_label td").html($("#input_form_label").val());
                        selector.attr("title", $("#input_form_label").val());
                        
                        saveHistoryBar();

                        $("#dialog-message").dialog( "close" );
                        $("#dialog-message").remove();
                    }
                }
            });
        },
        time_input_form: function(selector,edit){

            var holder= $('<div></div>');
            holder.html(selector.data('time_data'));

            
            
            


            $("#dialog-message").remove();
            var div =$('<div></div>');
            div.attr('id','dialog-message');
            div.attr('title','Date Selection');
            var html = window.workspace.getDivisions()["input_time"];
            div.append(html);
            $("#testdiv").append(div);
            $("#input_label_table").css('display',"block");
            var date_range_list = $("#input_time_range_list");

            date_range_list.html("");
            $("#input_form_label").val(selector.closest("table").find(".inner_label td").html());
            //var num =date_range_list.data("ranges");
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
                }
                
                $( "#minDate"+id ).datetimepicker(formatMinDate);
                $( "#maxDate"+id ).datetimepicker(formatMaxDate);
            }
            var _createDateRange =function(num){
                var tr = $('<tr id="input_time_range_'+num+'"></tr>');
                var td = $('<td align="center" valign="center">Range:</td>');
                tr.append(td);
                td = $('<td align="center" valign="center"> <input index="'+num+'" tabindex="-1" size="25" type="text" id="minDate'+num+'" name="minDate" value="'+$.cookie("minDate")+'"/></td>');
                tr.append(td);
                td = $('<td align="center" valign="center"> <input index="'+num+'" tabindex="-1" size="25 type="text" id="maxDate'+num+'" name="maxDate" value="'+$.cookie("maxDate")+'"/></td>');
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
            }


            var iterator =0;

            holder.find('tr').each(function(){
                
                
                iterator++;
                holder.data("ranges",iterator);
                _createDateRange(iterator);
                $(this).find("input").each(function(){
                    
                    if($(this).attr("name")=="maxDate"){
                        
                        $("#maxDate"+iterator).val($(this).val())
                    };
                    if($(this).attr("name")=="minDate")$("#minDate"+iterator).val($(this).val());
                    
                    
                });




            });
            if(iterator == 0){
                
                holder.data("ranges",1);
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
                var num =holder.data("ranges");
                holder.data("ranges",num+1);
                _createDateRange(num+1);
                $(".input_time_range_remove").button({
                    'disabled':false
                });
            //    var range_html = $("<tr></tr>");
            //    range_html.append($("#input_time_range_1").html());
            //
            //   $("#input_time_range_list").append(range_html);
            
            });
            formatButton($(".custom_button"))
            //$("#input_time_range_button").button({ disabled: true });
            $('#dialog-message').dialog({
                modal: true,
                height:530,
                width:700,
                close: function(){
                    
                    $("#dialog-message").remove();
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
                        
                        var div = $("<div></div>");

                        var table =$("<table></table>");
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
                            tr.append("<td style='display:none'><input type='hidden' name='maxDate' value='"+$("#maxDate"+itr).val()+"'></td>")
                            tr.append("<td style='display:none'><input type='hidden' name='minDate' value='"+$("#minDate"+itr).val()+"'></td>")
                            
                            
                            table.append(tr);

                            itr++;
                        });
                        div.append(table);
                        selector.closest("table").find(".inner_label td").html($("#input_form_label").val());
                        selector.attr("title",$("#input_form_label").val());
                            

                        selector.attr('time_data',div.html());
                        selector.data('time_data',div.html());
                        if(edit == false){
                            var div = $("<div  title='"+$("#task_label").val()+"' class='floaters'></div>");
                            var table2 =$('<table border="0" cellpadding="0" cellspacing="0"></table>');
                            var tr2 =$("<tr></tr>");
                            var td2 =$("<td></td>");
                            
                            td2.append(selector);
                            td2.append($("<div  style='float:right; margin-left: 17px;' class='closeme ui-state-default ui-corner-all'><span class='ui-icon ui-icon-close'></span></div>"));
                            tr2.append(td2);
                            table2.append(tr2);
                        
                            tr2 =$('<tr class="inner_label"><td>'+$("#input_form_label").val()+'</td><tr>')
                            table2.append(tr2);
                            //tr =$('<tr class="inner_label"><td>'+label+'</td><tr>')
                            //table.append(tr);
                            div.append(table2);
                            $("#historyContent").append(div);
                            $(".closeme").unbind();
                            $(".closeme").click(function(){
                            	// dialog which asks if paramter really wants to be deleted 
                            	var floaterDiv = $(this).parent().parent().parent().parent().parent();
                            	$('<div title="Confirmation">Do you really want to delete this parameter?</div>')
                            	.dialog({ buttons:{
                            		"Yes": function() {
	            	                		floaterDiv.remove();
	            	 	                    saveHistoryBar();
	            	                		$(this).dialog("close");
            	                		},
                            		"No": function() {
                                			$(this).dialog("close");
                                		}
                            	}});
                            });
                            var rowpos = $('#historyContent').position();
                            if(rowpos!=null){



                                $('html,body').scrollTop(rowpos.top);
                            }
                        }
                        saveHistoryBar();






                        $("#dialog-message").dialog( "close" );
                        $("#dialog-message").remove();




                    },
                    Cancel: function() {
                        
                        $("#dialog-message").dialog( "close" );
                        $("#dialog-message").remove();

                    }
                }
            })
        },

        result_input_form: function(selector){
            var holder= $('<div></div>');
            holder.html(selector.attr('result_data'));

            $("#dialog-message").remove();
            var div =$('<div></div>');
            div.attr('id','dialog-message');
            div.attr('title','Result Summary');
            var html = window.workspace.getDivisions()["input_result"];
            div.append(html);
            $("#testdiv").append(div);

            $("#input_result_area ul").append("<li>Result Source:</li>");




            formatButton($(".custom_button"))

            $('#dialog-message').dialog({
                modal: true,
                height:330,
                width:700,
                buttons: {


                    Ok: function() {

                        //selector.attr('time_data',table.html());
                        //selector.data('time_data',table.html());
                        //saveHistoryBar();
                        $("#dialog-message").dialog( "close" );
                        $("#dialog-message").remove();

                    },
                    Display: function(){

                        window.workspace.createItem(selector.attr('helio_task'));

                        $("#instruments_drop").attr('src','../images/helio/circle_inst.png');
                        $("#event_drop").attr('src','../images/helio/circle_event.png');
                        $("#time_drop").attr('src','../images/helio/circle_time.png');
                        $('#time_area').html(selector.attr('time_data'));
                        $('#extra_list').html(selector.attr('event_data'));
                        getSavedResult(selector.attr('helio_resultId'));
                        if(selector.attr('inst_data')!= null)$('#extra_list').html(selector.attr('inst_data'));
                        $("#dialog-message").dialog( "close" );
                        $("#dialog-message").remove();

                    }
                }
            })
        },
        
        // TODO: delete?
        clear: function(){
            if (typeof console!="undefined")console.info("History :: clear");
            array = [];
            this.render();
        },
        init: function(){
            if (typeof console!="undefined")console.info("History :: init");

            $( "#history" ).droppable({
                accept: ".drop_able",
                activeClass: "ui-state-hover",
                hoverClass: "ui-state-active",
                drop: function( event, ui ) {
                    var testver =ui.draggable.attr('src');
                    var title ="";
                    var div = $("<div  title='"+$("#task_label").val()+"' class='floaters'></div>");
                    var table =$('<table border="0" cellpadding="0" cellspacing="0"></table>');
                    var tr =$("<tr></tr>");
                    var td =$("<td></td>");
                    var img =   $( "<img class='history_draggable' alt='"+"image missing"+"'/>" ).attr( "src",ui.draggable.attr('src') );
                    if(testver.indexOf('grey') != -1){
                        return;
                    }
                    if(testver.indexOf('block')!= -1){
                        alert("Type not supported yet.");
                        return;
                    }
                    if(testver.indexOf('time') != -1){
                        img.data('time_data',$("#time_area").html());
                        img.attr('time_data',$("#time_area").html());
                        img.attr('helio_type','time');
                    }
                    if(testver.indexOf('event')!= -1){
                        img.data('event_data',$("#extra_list").html());
                        img.attr('event_data',$("#extra_list").html());
                        img.attr('helio_type','event');
                    }
                    if(testver.indexOf('inst')!= -1){
                        img.data('inst_data',$("#extra_list").html());
                        img.attr('inst_data',$("#extra_list").html());
                        img.attr('helio_type','inst');
                    }
                    if(testver.indexOf('result')!= -1){
                        if($("#resultId").val() == null){

                            alert("Result is empty, try executing a query before saving.");
                            return;
                        }
                        img.attr('helio_task',$("#task_name").val());
                        img.attr('helio_resultId',$("#resultId").val());
                        img.attr('event_data',$("#extra_list").html());
                        img.attr('inst_data',$("#extra_list").html());
                        img.attr('time_data',$("#time_area").html());
                        img.attr('helio_type','result');
                    }
                    td.append(img);
                    td.append($("<div  style='float:right; margin-left: 17px;' class='closeme ui-state-default ui-corner-all'><span class='ui-icon ui-icon-close'></span></div>"));

                    img.draggable({
                        revert: "invalid",
                        helper:"clone",
                        zIndex: 1700
                    });

                    img.click(function(){
                        if($(this).attr('helio_type')== 'time'){
                            window.historyBar.time_input_form($(this),true);
                        }
                        if($(this).attr('helio_type')== 'event'){
                            window.historyBar.event_input_form($(this));
                        }
                        if($(this).attr('helio_type')== 'inst'){
                            window.historyBar.instrument_input_form($(this));
                        }
                        if($(this).attr('helio_type')== 'result'){
                            window.historyBar.result_input_form($(this));
                        }
                    });



                    tr.append(td);


                    table.append(tr);
                    //tr =$('<tr class="inner_label"><td>'+label+'</td><tr>')
                    //table.append(tr);

                    div.append(table);


                    $("#historyContent").append(div);


                    $(".closeme").unbind();
                    $(".closeme").click(function(){
                    	// dialog which asks if paramter really wants to be deleted 
                    	var floaterDiv = $(this).parent().parent().parent().parent().parent();
                    	$('<div title="Confirmation">Do you really want to delete this parameter?</div>')
                    	.dialog({ buttons:{
                    		"Yes": function() {
    	                		floaterDiv.remove();
    	 	                    saveHistoryBar();
    	                		$(this).dialog("close");
    	                		},
                    		"No": function() {
                        		$(this).dialog("close");
                        		}
                    	}});
                    });
                    
                    $("#dialog-message").remove();
                    div =$('<div></div>');
                    div.attr('id','dialog-message');
                    div.attr('title','Save Element');
                    var html = $("<div style='padding:15px'><b>Please give your saved element a label:</b> <input id='label_input' type='text'/></div>")
                    div.append(html);
                    $("#testdiv").append(div);
                    tr =$('<tr class="inner_label"><td>'+$("#task_label").val()+'</td><tr>')
                    table.append(tr);

                    saveHistoryBar();
          
                }
            });

        },
        initSaved: function() {



            $(".history_draggable").each(function(){
                
                var img = $(this);
                $(".closeme").unbind();
                $(".closeme").click(function(){
                	// dialog which asks if paramter really wants to be deleted 
                	var floaterDiv = $(this).parent().parent().parent().parent().parent();
                	$('<div title="Confirmation">Do you really want to delete this parameter?</div>')
                	.dialog({ buttons:{
                		"Yes": function() {
	                		floaterDiv.remove();
	 	                    saveHistoryBar();
	                		$(this).dialog("close");
	                		},
                		"No": function() {
                    		$(this).dialog("close");
                    		}
                	}});
                });
                img.draggable({
                    revert: "invalid",
                    helper:"clone",
                    zIndex: 1700
                });
                if(img.attr('time_data') != -1){
                    img.data('time_data',img.attr('time_data'));

                }
                if(img.attr('event_data') != -1){
                    img.data('event_data',img.attr('event_data'));

                }
                if(img.attr('inst_data') != -1){
                    img.data('inst_data',img.attr('inst_data'));

                }
                img.click(function(){
                    if($(this).attr('helio_type')== 'time'){
                        window.historyBar.time_input_form($(this),true);
                    }
                    if($(this).attr('helio_type')== 'event'){
                        window.historyBar.event_input_form($(this));
                    }
                    if($(this).attr('helio_type')== 'inst'){
                        window.historyBar.instrument_input_form($(this));
                    }
                    if($(this).attr('helio_type')== 'result'){
                        window.historyBar.result_input_form($(this));
                    }
                });
            });
     
        }

      

    };
}
