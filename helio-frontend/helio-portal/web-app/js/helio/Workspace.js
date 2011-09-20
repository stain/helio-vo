function Workspace() {

    var divisions = new Object();
    var element;

    var ingestDivision = function(keyName,divisionName){
        divisions[keyName] = $(divisionName).html();
        $(divisionName).remove();
    };

  
    

    return {
        
        //Iinitializes by filling the @divisions map with the html contained in the sections, also removes the html to not overlap ids
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
                $("#input_table td[internal='"+$(this).attr("id")+"']").parent().addClass('row_selected');

            });
            $("#input_filter").keyup(function(){
                $("#input_table").dataTable().fnFilter($(this).val());
            });

            $('#input_table tr').click( function() {
                var row =$(this).find('td').attr("internal").trim();
                if ( $(this).hasClass('row_selected') ){
                    $(this).removeClass('row_selected');

                    $("#"+row).remove();
                }
                else{
                    $(this).addClass('row_selected');
                    $("#extra_list_form").append("<li id='"+row+"'>'"+row+"'<input type='hidden'  name='extra' value='"+row+"'/></li>");
                }
            } );
            formatButton($(".custom_button"))
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
        event_input_form: function(){
            

            $("#dialog-message").remove();
            var div =$('<div></div>');
            div.attr('id','dialog-message');
            div.attr('title','Event Parameter');
            var html = divisions["input_event"];

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
                $("#input_table td[internal='"+$(this).attr("id")+"']").parent().addClass('row_selected');
               
            });
            $("#input_filter").keyup(function(){
                $("#input_table").dataTable().fnFilter($(this).val());
            });

            $('#input_table tr').click( function() {
                var row =$(this).find('td').attr("internal").trim();
                if ( $(this).hasClass('row_selected') ){
                    $(this).removeClass('row_selected');
                    
                    $("#"+row).remove();
                }
                else{
                    $(this).addClass('row_selected');
                    $("#extra_list_form").append("<li id='"+row+"'>'"+row+"'<input type='hidden'  name='extra' value='"+row+"'/></li>");//<div class='custom_button input_time_advanced'>Advanced</div>
                    $(".input_time_advanced").unbind();
                    formatButton($(".custom_button"))
                    $(".input_time_advanced").click(function(){
                        
                        
                        getAdvancedFields($("#service_name").val(),$(this).parent().find('input').val());
                    });

                }
            } );
            formatButton($(".custom_button"))
            $('#dialog-message').dialog({
                modal: true,
                height:530,
                width:700,
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
        },
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
            var _formatDateRange =function(id){

                $('#minDate'+id).datetimepicker("destroy");
                $('#maxDate'+id).datetimepicker("destroy");
                var dates = $( "#minDate"+id+", #maxDate"+id ).datetimepicker({
                    
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
                    numberOfMonths: 1,
                    onClose: function( selectedDate ) {
                        
                        var option = this.id == "minDate"+id ? "minDate" : "maxDate",
                        instance = $( this ).data( "datepicker" ),
                        date = $.datepicker.parseDate(
                            instance.settings.dateFormat ||
                            $.datepicker._defaults.dateFormat,
                            selectedDate, instance.settings );
                        dates.not( this ).datepicker( "option", option, date );
                        $.cookie("minDate",$("#minDate"+id).val(),{
                            expires: 30
                        });
                        $.cookie("maxDate",$("#maxDate"+id).val(),{
                            expires: 30
                        });
                    }
                });

            }
         
            var _createDateRange =function(num){
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
                
            
                
            }
          
            
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
            formatButton($(".custom_button"))
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
                                
                            tr.append("<td style='display:none'><input type='hidden' name='maxDate' value='"+$("#maxDate"+itr).val()+"'></td>")
                            tr.append("<td style='display:none'><input type='hidden' name='minDate' value='"+$("#minDate"+itr).val()+"'></td>")
                            
                            
                            table.append(tr);
                            
                            itr++;
                        });
                        $("#time_area").html(table);

                      
                        $("#time_drop").attr('src','../images/helio/circle_time.png');
                        $("#time_drop").addClass('drop_able');

                        formatButton($(".custom_button"))
                        
                        $("#dialog-message").dialog( "close" );
                        $("#dialog-message").remove();
                        window.workspace.evaluator();



                    }
                }
            })
        },
        evaluator: function(){
        
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
            
            
            switch (serviceName) {
                case "context":
                    if(minDate.length >0&& maxDate.length >0){

                        sendQueryContext([minDate[0]], [maxDate[0]]);
                    }
                    break;
                case "HEC":
                    
                    if(minDate.length >0&& maxDate.length >0&& extra.length >0){
                        
                        sendQuery(minDate, maxDate,serviceName, extra);
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
                                        sendQuery([minDate[0]], [maxDate[0]] ,serviceName, extra);
                                    },
                                    Cancel: function() {
                                        $( this ).dialog( "close" );
                                    }
                                }
                            });

                        }else{
                            sendQuery(minDate, maxDate ,serviceName, extra);
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
                                        sendQuery([minDate[0]], [maxDate[0]] ,serviceName, extra);
                                    },
                                    Cancel: function() {
                                        $( this ).dialog( "close" );

                                    }
                                }
                            });

                        }else{
                            sendQuery(minDate, maxDate ,serviceName, extra);
                        }
                    }
                    break;
                case "DPAS":
                    
                    if(minDate.length >0&& maxDate.length >0&& extra.length >0){

                        sendQuery(minDate, maxDate,serviceName, extra);
                    }
                    break;
                default:
                    break;
            }
        },
       
         getDivisions: function(){
            if (typeof console!="undefined")console.info("Workspace :: setElement");
            return divisions;
        },
        setDisplay: function(key){
            if (typeof console!="undefined")console.info("Workspace :: setDisplay -> " +key);
            this.clear();
            var newDiv = $('<div></div>');
            if(divisions[key] == null){
                key = "error";
            }//end if
            
            newDiv.html(divisions[key]);
            newDiv.css("display","block");
            newDiv.attr("id","currentDisplay");
            newDiv.attr("class","displayable");
            $("#droppable-inner").append(newDiv);
            
        getPreviousTaskState($("#task_name"),$("#HUID"),key);
             
        
  
            
            
        },
         
        createItem: function(imagePath){
            
            if (typeof console!="undefined")console.info("Workspace :: createItem -> " +imagePath);
            var element;
            formatButton($(".custom_button"))
            if($("#task_name").length >0)setPreviousTaskState($("#task_name"),$("#HUID"),$("#query_form"));
            this.setDisplay(imagePath);
            
            

        },
        //@TODO: delete
        render: function() {
            if (typeof console!="undefined")console.error("Workspace :: render");
            return;
            $( "#droppable-inner" ).droppable({
                accept: ".draggable",

                activeClass: "ui-state-hover",
                hoverClass: "ui-state-active",

                drop: function( event, ui ) {
                    var text =  ui.draggable.find("img").attr("alt");

                    window.workspace.createItem(text);
                }
            });
            fnInitDroppable();
            
        },
        clear: function() {
            if (typeof console!="undefined")console.info("Workspace :: clear");
            //$('#currentDisplay').fadeOut(1000,0);
            $("#currentDisplay").remove();
            $(".displayable").css("display","none");
            $(".resCont").remove();
            $(".tooltip").css("display","none");
            
        }
    };
}
