/**
 * Provide the actions to handle results coming back from catalog services (i.e. as VOTable)
 * @returns a collection of functions to be called on the action viewer instance.
 */
function ActionViewer() {
    // attach a timeout handler to this object.
    var resultFilterTimeout = new Object();

    return {
        /**
         * Load and render the result table.
         * Loads after the ajax call is complete and successful
         * @data:  html for result section
         */
        resultContainerInit: function(data){
            //loads the html into the response division
            $("#responseDivision").html(data);

            $("#result_area").html("Query Success");//modify message on result overview
            $("#result_button").hide();
            $("#displayableResult").html("");
            $("#ics_instrument").css("display","block"); //if instruments controls are present in gsp, show them
            $("#ils_trajectories").css("display","block"); // if trajectory controls are present in gsp, show them
            $("#voTables").prepend($("#ics_instrument").clone()); //add them before votables
            $("#voTables").prepend($("#ils_trajectories").clone());
            $("#ics_instrument").css("display","none");//hide the originals again in the query part, they need to stay intact otherwise when a reloaded result needs it, they wont have them
            $("#ils_trajectories").css("display","none");
            $("#result_overview").css("display","table");//make sure the ovrview is visible,redundant
            $(":checkbox").unbind();//clear all possible listener on checkboxes
            $(":checkbox").removeAttr("checked");
            $("#result_drop").attr('result_id',$("#resultId").val());
            
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
            				filter($(this));
            			}
            		});
            		
            		// if no type is checked, check all
            		if(!typesChecked && $(this).attr("checked")) {
            			$(".obsEntityPhotonsType").each(function(){
            				// only check visible checkboxes, otherwise they can't be reset again
            				if ($(this).is(":visible")) {
                				$(this).attr("checked", true);
                				filter($(this));
            				}
                		});
            		}
            	}
            	// type filter clicked
            	else if($(this).hasClass("obsEntityPhotonsType")) {
            		// check super filter
            		if($(this).attr("checked")) {
            			$(".obsEntityPhotons").attr("checked", true);
                		filter($($(".obsEntityPhotons").get(-1)));
            		}
            		// if no type filter is selected remove super filter
            		else {
            			var noTypeChecked = true;
            			// prove if no type filter is checked
            			$(".obsEntityPhotonsType").each(function(){
            				if($(this).attr("checked")) {
            					noTypeChecked = false;
            				}
            			})
            			if (noTypeChecked) {
            				$(".obsEntityPhotons").attr("checked", false);
            				filter($($(".obsEntityPhotons").get(-1)));
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
            				filter($(this));
            			}
            		});
            		
            		if(!typesChecked && $(this).attr("checked")) {
            			$(".obsEntityParticlesType").each(function(){
            				if ($(this).is(":visible")) {
                				$(this).attr("checked", true);
                				filter($(this));
            				}
                		});
            		}
            	}
            	else if($(this).hasClass("obsEntityParticlesType")) {
            		if($(this).attr("checked")) {
            			$(".obsEntityParticles").attr("checked", true);
                		filter($($(".obsEntityParticles").get(-1)));
            		}
            		else {
            			var noTypeChecked = true;
            			$(".obsEntityParticlesType").each(function(){
            				if($(this).attr("checked")) {
            					noTypeChecked = false;
            				}
            			})
            			if (noTypeChecked) {
            				$(".obsEntityParticles").attr("checked", false);
            				filter($($(".obsEntityParticles").get(-1)));
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
            				filter($(this));
            			}
            		});
            		
            		if(!typesChecked && $(this).attr("checked")) {
            			$(".obsEntityFieldsType").each(function(){
            				if ($(this).is(":visible")) {
                				$(this).attr("checked", true);
                				filter($(this));
            				}
                		});
            		}
            	}
            	else if($(this).hasClass("obsEntityFieldsType")) {
            		if($(this).attr("checked")) {
            			$(".obsEntityFields").attr("checked", true);
                		filter($($(".obsEntityFields").get(-1)));
            		}
            		else {
            			var noTypeChecked = true;
            			$(".obsEntityFieldsType").each(function(){
            				if($(this).attr("checked")) {
            					noTypeChecked = false;
            				}
            			})
            			if (noTypeChecked) {
            				$(".obsEntityFields").attr("checked", false);
            				filter($($(".obsEntityFields").get(-1)));
            			}
            		}
            	}
            	
            	//console.log("change: " + $(this).attr("name") + $(this).attr("column"));
            	 
            	filter($(this));
            });
            
            function filter(checkbox){
            	//console.log("filter: " + checkbox.attr("name") + checkbox.attr("column"))
            	 var checkboxColumn = checkbox.attr("column");
                 var filter_expression = "\0";
                 
                 var anySelected = false;
                 var instruments = new Array();
                 
                 $("input:checked").each(function(){
                	 anySelected = true;
                     if($(this).attr("column") == checkboxColumn)
                         filter_expression = filter_expression + "|(" + $(this).attr("name") + ")";

                     if ($(this).hasClass("planet") || $(this).hasClass("spacecraft")) {
                    	 instruments.push($(this).attr("name"));
                     }
                 });
                 
                 if (anySelected) {
                	 $(".filterInstrumentsText").html("Show following: " + instruments.join(", "));
                 }
                 else {
                	 $(".filterInstrumentsText").html("All instruments are shown.");
                 }
                 
                 if(filter_expression=="\0")
                     filter_expression="";
                 
                 if(resultFilterTimeout[checkboxColumn] != null)
                     clearTimeout(resultFilterTimeout[checkboxColumn]);
                 
                 resultFilterTimeout[checkboxColumn] = setTimeout(function(){
                     $("table#resultTable0").dataTable().fnFilter(filter_expression,checkboxColumn,true);
                     //console.log(filter_expression);
                 },200);
            }
            
            var counter = 0;
            $("input:radio").change(function(){
                var checkboxColumn = $(this).attr("column");
                var filter_expression = "";
                
                filter_expression = (filter_expression == "" ? "" : (filter_expression + "|")) + "(" + $(this).attr("value") + ")";

                $("table#resultTable0").dataTable().fnFilter(filter_expression, checkboxColumn, true);
            });
            
            $('#displayableResult').append($('#tables'));
            $('#displayableResult').css("display","block");

            if($("#task_name").val()=="task_searchEvents"){

                $('.resultTable tr').prepend("<td class='magnify'><img style='width:15px;heigth:15px' src='../images/search.png'></td>");
            }

            $('.resultTable').each(function(){
                var dataTable = fnFormatTable(this.id);
                if($("#service_name").val()=='ICS'){
                    dataTable.fnSetColumnVis( 19, false );
                }
            });
          
            $('.magnify').click(function(){
                var dataTable =$("#"+$(this).parent().parent().parent().attr('id')).dataTable();
                var settings = dataTable.fnSettings();
                var time_start = -1;
                var time_end = -1;
                for(var j = 0;j< settings.aoColumns.length;j++){
                    if($.trim(settings.aoColumns[j].sTitle) == 'time_start'){
                        time_start=j + 1;
                    }
                    if($.trim(settings.aoColumns[j].sTitle) == 'time_end'){
                        time_end=j + 1;
                    }
                }//end j
                var times =$($(this).parent().children()[time_start]).text();
                var timee =$($(this).parent().children()[time_end]).text();

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
            });
            formatButton($(".custom_button"));
            var serviceNameTemp = $("#service_name").val();
            
            if(serviceNameTemp == 'DPAS'){
                $("#response_download_selection").css('display','inherit');
                $("#response_download_selection").click(function(){
                    var itr= 0;
                    $(".resultTable").each(function(){
                        //console.debug($(this));
                        itr++;
                    });
                    itr = itr/2;
                    var table =$("<table></table>");
                 var download_array = $("<ul></ul>");

                    for(var i = 0;i<itr;i++){
                        var dataTable =$("#resultTable"+i).dataTable();
                        var settings = dataTable.fnSettings();
                        var download_url = -1;

                        for(var j = 0;j< settings.aoColumns.length;j++){
                            if($.trim(settings.aoColumns[j].sTitle) == 'url'){
                                download_url=j;
                            }
                        }//end j

                        $("#resultTable"+i+" .even_selected").each(function(){
                            download_array.append("<li>"+$(this).children().eq(download_url).html()+"</li>");
                        });
                        $("#resultTable"+i+" .odd_selected").each(function(){
                            download_array.append("<li>"+$(this).children().eq(download_url).html()+"</li>");
                        });
                        if(download_array.html().indexOf('li') < 0){
                            
                            var nNodes = dataTable.fnGetNodes();
                            for(var node in nNodes){
                                download_array.append("<li>"+$(nNodes[node]).children().eq(download_url).html()+"</li>");
                            }
                        }
                    }//end i
                    
                    var recipe =  window.open('','_blank','width=600,height=600');
                    var html = '<html><head><title>Helio Downloads</title></head><body><div id="links">'+$("#time_area").html()+$("#extra_list").html() + download_array.html() + '</div></body></html>';
                    recipe.document.open();
                    recipe.document.write(html);
                    recipe.document.close();
                });
            }
            
            $("#response_save_selection").click(function(){
                if($(".odd_selected").length <1 && $(".even_selected").length<1){
                    alert("Please select something first");
                    return;
                }

                var serviceName = $("#service_name").val();
                if(serviceName == 'HEC' ||serviceName == 'upload'||serviceName == 'DES'){
                    var itr= 0;
                    $(".resultTable").each(function(){
                        //console.debug($(this));
                        itr++;
                    });
                    itr = itr/2;
                    var table =$("<table></table>");
                    var time_start_array = new Array();
                    var time_end_array = new Array();
                    for(var i = 0;i<itr;i++){
                        var dataTable =$("#resultTable"+i).dataTable();
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
                        if(time_end == -1) time_end = time_start;

                        $("#resultTable"+i+" .even_selected").each(function(){
                            time_start_array.push($(this).children().eq(time_start).text());
                            time_end_array.push($(this).children().eq(time_end).text());
                        });
                        $("#resultTable"+i+" .odd_selected").each(function(){
                            time_start_array.push($(this).children().eq(time_start).text());
                            time_end_array.push($(this).children().eq(time_end).text());
                        });
                    }//end i

                    for(itr= 0;itr < time_start_array.length;itr++){
                        var tr = $('<tr></tr>');
                        tr.append("<td><b>Range :</b></td>"+
                            "<td>"+time_start_array[itr]+"</td>"+
                            "<td>--</td><td>"+time_end_array[itr]+"</td>");
                        tr.append("<td><input type='hidden' name='maxDate' value='"+time_end_array[itr]+"'/></td>");
                        tr.append("<td><input type='hidden' name='minDate' value='"+time_start_array[itr]+"'/></td>");
                        table.append(tr);

                    }//end itr
                    var img =   $( "<img class='history_draggable' alt='"+"image missing"+"'/>" ).attr( "src",'../images/helio/circle_time.png' );
                    var superdiv = $('<div></div>');
                    superdiv.append(table);

                    img.data('time_data',superdiv.html());
                    img.attr('time_data',superdiv.html());
                    img.attr('helio_type','time');

                    img.draggable({
                        revert: "invalid",
                        helper:"clone",
                        zIndex: 1700
                    });

                    img.click(function(){
                        if($(this).attr('helio_type')== 'time'){
                            window.historyBar.time_input_form(img,true);
                        }
                        if($(this).attr('helio_type')== 'event'){
                            window.historyBar.event_input_form(img);
                        }
                        if($(this).attr('helio_type')== 'inst'){
                            window.historyBar.instrument_input_form(img);
                        }
                        if($(this).attr('helio_type')== 'result'){
                            window.historyBar.result_input_form(img);
                        }
                    });

                    window.historyBar.time_input_form(img,false);
                } else if(serviceName == 'ICS'){
                    var itr= 0;
                    $(".resultTable").each(function(){
                        //console.debug($(this));
                        itr++;
                    });
                    itr = itr/2;
                    var table =$("<table></table>");
                    var instrument_array = new Array();

                    for(var i = 0;i<itr;i++){
                        var dataTable =$("#resultTable"+i).dataTable();
                        var settings = dataTable.fnSettings();
                        var instrument = -1;

                        for(var j = 0;j< settings.aoColumns.length;j++){
                            if($.trim(settings.aoColumns[j].sTitle) == 'obsinst_key'){
                                instrument=j;
                            }
                        }//end j

                        $("#resultTable"+i+" .even_selected").each(function(){
                            instrument_array.push($(this).children().eq(instrument).text());
                        });
                        $("#resultTable"+i+" .odd_selected").each(function(){
                            instrument_array.push($(this).children().eq(instrument).text());
                        });

                    }//end i

                    var holder= $('<ul class="candybox"></ul>');
                    for(itr= 0;itr < instrument_array.length;itr++){
                        var instrument_string =instrument_array[itr];
                        holder.append("<li internal='"+instrument_string+"'>'"+instrument_string+"'<input id='"+instrument_string+"' type='hidden'  name='extra' value='"+instrument_string+"'/></li>");
                    }

                    $("#dialog-message").remove();
                    var div =$('<div></div>');
                    div.attr('id','dialog-message');
                    div.attr('title','Instrument Selection');

                    var html = window.workspace.getDivisions()["input_instruments"];
                    div.append(html);

                    div.append("*Items in red are not supported by our Data Search service therefore will not be saved.");
                    $("#testdiv").append(div);
                    
                    $("#input_filter").parent().remove();
                    $("#extra_list_form").html(holder.html());
                    $("#extra_list_form").addClass('candybox');

                    $("#extra_list_form input").each(function(){
                        
                        if($("#input_table td[internal='"+$(this).attr("id")+"']").length ==1){
                            $(this).parent().addClass("item_found");
                        }else{
                            $(this).parent().addClass("item_missing");
                        }
                    });
                    $("#input_table").remove();
                   
                    formatButton($(".custom_button"));
                    $('#dialog-message').dialog({
                        modal: true,
                        height:530,
                        width:700,
                        close: function(){

                            $("#dialog-message").remove();
                        },
                        buttons: {
                            Ok: function() {
                                $(".item_missing").remove();
                                var img =   $( "<img class='history_draggable' alt='"+"image missing"+"'/>" ).attr( "src",'../images/helio/circle_inst.png' );
                                var div = $("<div  title='"+$("#task_label").val()+"' class='floaters'></div>");
                                var table2 =$('<table border="0" cellpadding="0" cellspacing="0"></table>');
                                var tr2 =$("<tr></tr>");
                                var td2 =$("<td></td>");
                                img.data('inst_data',$("#extra_list_form").html());
                                img.attr('inst_data',$("#extra_list_form").html());
                                img.attr('helio_type','inst');

                                td2.append(img);
                                td2.append($("<div  style='float:right; margin-left: 17px;' class='closeme ui-state-default ui-corner-all'><span class='ui-icon ui-icon-close'></span></div>"));

                                img.draggable({
                                    revert: "invalid",
                                    helper:"clone",
                                    zIndex: 1700
                                });

                                img.click(function(){
                                    if($(this).attr('helio_type')== 'time'){

                                        window.historyBar.time_input_form(img,true);
                                    }
                                    if($(this).attr('helio_type')== 'event'){
                                        window.historyBar.event_input_form(img);
                                    }
                                    if($(this).attr('helio_type')== 'inst'){
                                        window.historyBar.instrument_input_form(img);
                                    }
                                    if($(this).attr('helio_type')== 'result'){
                                        window.historyBar.result_input_form(img);
                                    }
                                });

                                tr2.append(td2);
                                table2.append(tr2);
                                tr2 =$('<tr class="inner_label"><td>'+$("#task_label").val()+'</td><tr>');
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
                                saveHistoryBar();
                                $("#dialog-message").dialog( "close" );
                                $("#dialog-message").remove();
                            }
                            ,
                            Cancel: function() {
                                $("#dialog-message").dialog( "close" );
                                $("#dialog-message").remove();
                            }
                        }
                    });
                }else if(serviceName == 'DPAS'){
                    alert("No Extractable Parameters");//@todo why is this here
                }
            });
            var rowpos = $('#displayableResult').position();
            if(rowpos!=null){
                $('html,body').scrollTop(rowpos.top);
            }
            $("#dialog-message").dialog( "close" );
            $("#dialog-message").remove();

            if($("#service_name").val()=='ICS'){

                $("#response_save_selection").parent().prepend("*Items in red are not supported by our Data Search service therefore will not be saved.");
            }
            if($("#service_name").val()=='ILS'){
                $("#response_save_selection").remove();
            }
            if($("#service_name").val()=='DPAS'){
                $("#response_save_selection").remove();
            }
             
        },
        
        /**
         * Init the action viewer, i.e enable drag & and dropping on it.
         * @returns nothing
         */
        init: function(){
            $("#block_drop").draggable({
                helper:'clone'
            });

            $("#time_drop").draggable({
                helper:'clone'
            });
            $("#event_drop").draggable({
                helper:'clone'
            });
            $("#instruments_drop").draggable({
                helper:'clone'
            });
            $("#result_drop").draggable({
                helper:'clone'
            });
            
            $( ".resultDroppable" ).droppable({
                accept: ".history_draggable",
                activeClass: "ui-state-hover",
                hoverClass: "ui-state-active",
                drop: function( event, ui ) {
                    if( ui.draggable.data('time_data') != null){
                        $("#time_drop").attr('src','../images/helio/circle_time.png');
                        $('#time_area').html(ui.draggable.data('time_data'));
                        window.workspace.evaluator();
                        $("#time_drop").addClass('drop_able');
                    }
                }
            });
            $( ".resultDroppableEvent" ).droppable({
                accept: ".history_draggable",
                activeClass: "ui-state-hover",
                hoverClass: "ui-state-active",
                drop: function( event, ui ) {
                    if( ui.draggable.data('event_data') != null){
                        $("#event_drop").attr('src','../images/helio/circle_event.png');
                        
                        // input list which contains all hidden input files
                        var inputList = $("<div></div>");
                        inputList.append(ui.draggable.data('event_data'));
                        
                        // clear extra list
                        $("#extra_list").html("");
                        
                        // add selected lists to extra list
                        var selectedLists = new Array();
                        inputList.find("input[name='extra']").each(function() {
                        	$("#extra_list").append($(this));
                        	selectedLists.push($(this).attr("internal"));
                        });

            			$("#extra_list").append(selectedLists.join(" ,<br/>"));

                        window.workspace.evaluator();
                        $("#event_drop").addClass('drop_able');
                    }
                }
            });
            $( ".resultDroppableInst" ).droppable({
                accept: ".history_draggable",
                activeClass: "ui-state-hover",
                hoverClass: "ui-state-active",
                drop: function( event, ui ) {
                    if( ui.draggable.data('inst_data') != null){
                        $("#instruments_drop").attr('src','../images/helio/circle_inst.png');
                        $('#extra_list').html(ui.draggable.data('inst_data'));
                        window.workspace.evaluator();
                        $("#instruments_drop").addClass('drop_able');
                    }
                }
            });

            $(".placeholder").remove();
            $.collapsible(".queryHeader","group1");
            $.collapsible(".advancedParameters","group2");
        }
    };//end public methods
}//end class
