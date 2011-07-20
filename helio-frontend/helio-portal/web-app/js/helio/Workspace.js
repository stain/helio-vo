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
            ingestDivision("task_ils","#displayableILS");
            ingestDivision("task_dpas","#displayableDPAS");
            ingestDivision("task_uploadVOTable","#displayableUpload");
            ingestDivision("loading","#displayableOnLoading");
            ingestDivision("error","#displayableError");
            ingestDivision("splash","#displayableSplash");
            ingestDivision("selected_result","#displayableSeletedResult");
            ingestDivision("task_searchEvents","#displayableTaskSearchEvents");
            ingestDivision("task_searchInstruments","#displayableTaskSearchInstruments");
            ingestDivision("task_searchData","#displayableTaskSearchData");

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
            $(".custom_button").button();
            $('#dialog-message').dialog({
                modal: true,
                height:530,
                width:700,
                close: function(){

                    $("#dialog-message").remove();
                },
                buttons: {
                    Ok: function() {
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
            $(".custom_button").button();
            $('#dialog-message').dialog({
                modal: true,
                height:530,
                width:700,
                close: function(){

                    $("#dialog-message").remove();
                },
                buttons: {
                    Ok: function() {
                        $("#extra_list").html(($("#extra_list_form").html()));
                        
                        
                        $("#event_drop").attr('src','../images/helio/circle_event.png');
                        $("#event_drop").addClass('drop_able');
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
                $('#minDate'+id).datepicker("destroy");
                $('#minDate'+id).datepicker({
                    defaultDate: "+1w",
                    yearRange: '1970:2011',
                    dateFormat: 'yy-mm-dd',
                    changeMonth: true,
                    showOn: "button",
                    buttonImageOnly: true,
                    buttonImage: "../images/icons/calendar.gif",
                    changeYear: true,
                    numberOfMonths: 1
                });
                $('#maxDate'+id).datepicker("destroy");
                $('#maxDate'+id).datepicker({
                    defaultDate: "+1w",
                    yearRange: '1970:2011',
                    dateFormat: 'yy-mm-dd',
                    changeMonth: true,
                    showOn: "button",
                    buttonImageOnly: true,
                    buttonImage: "../images/icons/calendar.gif",
                    changeYear: true,
                    numberOfMonths: 1
                });


            }
            var _createDateRange =function(num){
                var tr = $('<tr id="input_time_range_'+num+'"></tr>');
                var td = $('<td align="center" valign="center">Range '+num+'</td>');
                tr.append(td);
                td = $('<td align="center" valign="center"> <input size="12" type="text" id="minDate'+num+'" name="minDate" value="2003-01-01"/><input size="6" type="text" name="minTime" id="minTime'+num+'" value="00:00" /></td>');
                tr.append(td);
                td = $('<td align="center" valign="center"> <input size="12" type="text" id="maxDate'+num+'" name="maxDate" value="2003-01-03"/><input size="6" type="text" name="maxTime" id="maxTime'+num+'" value="00:00" /></td>');
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
                
                    $(this).parent('tr').remove();
                
                });
            //                $("#maxTime"+num).keydown(validatemydate(num));
            //                $("#minTime"+num).keydown(validatemydate(num));
            //                $("#maxDate"+num).keydown(validatemydate(num));
            //                $("#minDate"+num).keydown(validatemydate(num));
                
            }
          
            
            var iterator =0;

            $("#time_area").find('tr').each(function(){
                iterator++;
                date_range_list.data("ranges",iterator);
                _createDateRange(iterator);
                $(this).find("input").each(function(){
                    if($(this).attr("name")=="maxDate")$("#maxDate"+iterator).val($(this).val());
                    if($(this).attr("name")=="minDate")$("#minDate"+iterator).val($(this).val());
                    if($(this).attr("name")=="minTime")$("#minTime"+iterator).val($(this).val());
                    if($(this).attr("name")=="maxTime")$("#maxTime"+iterator).val($(this).val());
                });
                
                
                
                
            });
            if(iterator == 0){
                date_range_list.data("ranges",1);
                _createDateRange(1);
            }


            
            $("#input_time_range_button").click(function(){
                var num =date_range_list.data("ranges");
                date_range_list.data("ranges",num+1);
                _createDateRange(num+1);
            //    var range_html = $("<tr></tr>");
            //    range_html.append($("#input_time_range_1").html());
            //
            //   $("#input_time_range_list").append(range_html);
            // fnInitializeDatePicker2();
            });
            $(".custom_button").button();
            //$("#input_time_range_button").button({ disabled: true });
            $('#dialog-message').dialog({
                modal: true,
                height:530,
                width:700,
                buttons: {
                    Ok: function() {
                        var table =$("<table>");
                        var itr = 1;

                        date_range_list.find("tr").each(function(){
                            var tr = $('<tr></tr>');
                            while(!$("#minDate"+itr).length){
                                itr++;
                                if(itr==100)continue;
                            }
                            tr.append("<td><b>Range "+itr+":</b></td>"+
                                "<td>"+$("#minDate"+itr).val()+"</td>"+
                                "<td>"+$("#minTime"+itr).val()+"</td>"+
                                "<td>--</td><td>"+$("#maxDate"+itr).val()+"</td>"+
                                "<td>"+$("#maxTime"+itr).val()+"</td>");
                            tr.append("<input type='hidden' name='maxDate' value='"+$("#maxDate"+itr).val()+"'>")
                            tr.append("<input type='hidden' name='minDate' value='"+$("#minDate"+itr).val()+"'>")
                            tr.append("<input type='hidden' name='maxTime' value='"+$("#maxTime"+itr).val()+"'>")
                            tr.append("<input type='hidden' name='minTime' value='"+$("#minTime"+itr).val()+"'>")
                            table.append(tr);
                            
                            itr++;
                        });
                        $("#time_area").html(table);

                      
                        $("#time_drop").attr('src','../images/helio/circle_time.png');
                        $("#time_drop").addClass('drop_able');

                        $(".custom_button").button();
                        
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
            var minTime=  [];
            $("[name='minTime']").each(function() {

                minTime.push($(this).val());
            });
            var maxTime=  [];
            $("[name='maxTime']").each(function() {

                maxTime.push($(this).val());
            });
            var extra= [];
            $("[name='extra']").each(function() {
                
                extra.push($(this).val());
            });
                
            var serviceName =$("#service_name").val();
            
            
            switch (serviceName) {
                case "HEC":
                    if(minDate.length >0&& maxDate.length >0&& minTime.length >0&& maxTime.length >0&& extra.length >0){
                        
                        sendQuery(minDate, maxDate,minTime , maxTime ,serviceName, extra);
                    }
                    break;
                case "ICS":
                    
                    if(minDate.length >0&& maxDate.length >0&& minTime.length >0&& maxTime.length >0){

                        //extra.push('instrument');
                        sendQuery(minDate, maxDate,minTime , maxTime ,serviceName, extra);
                    }
                    break;
                case "ILS":
                    if(minDate.length >0&& maxDate.length >0&& minTime.length >0&& maxTime.length >0){
                        
                        
                        sendQuery(minDate, maxDate,minTime , maxTime ,serviceName, extra);
                    }
                    break;
                case "DPAS":
                    if(minDate.length >0&& maxDate.length >0&& minTime.length >0&& maxTime.length >0&& extra.length >0){

                        sendQuery(minDate, maxDate,minTime , maxTime ,serviceName, extra);
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
        onLoading: function(){
            if (typeof console!="undefined")console.info("Workspace :: onLoading");
            var element = window.historyBar.getCurrent();
            if(element.getClassName() == 'ActionViewer'){
                element.prepareStep($("#currentDisplay").find("form").serialize(),$('#currentDisplay').find('#advancedParams').html());
            }else{
                element.prepareStep($("#currentDisplay").find("form").serialize(),$('#currentDisplay').find('#hecExtendedQueryContent').html());
            }
            
            


            window.workspace.setDisplay("loading");
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
            
            
        },
        createItem: function(imagePath){
            
            if (typeof console!="undefined")console.info("Workspace :: createItem -> " +imagePath);
            var element;
                        
            this.setDisplay(imagePath);
            
            switch (imagePath) {
                case 'hec_extended':
                    element = new ActionViewerExtended(imagePath,"ghost",text,"label","hec");
                    window.historyBar.addItem(element);
                    window.historyBar.render();
                    break;
                case 'task_hec':
                    element = new ActionViewer();
                    element.init();
                   
                    $("#event_button").click(window.workspace.event_input_form);
                    $("#time_button").click(window.workspace.time_input_form);
                    $("#time_drop").click(window.workspace.time_input_form);
                        
                    
                    
                    break;
                case 'task_ics':
                    element = new ActionViewer();
                    element.init();
                   
                    $("#time_button").click(window.workspace.time_input_form);
                    $("#time_drop").click(window.workspace.time_input_form);
                    
                    break;
                case 'task_ils':
                    element = new ActionViewer();
                    element.init();
                    
                    $("#time_button").click(window.workspace.time_input_form);
                    $("#time_drop").click(window.workspace.time_input_form);
                                   
                    break;
                case 'task_dpas':
                    element = new ActionViewer();
                    element.init();
                    $("#instruments_button").click(window.workspace.instrument_input_form);
                    $("#time_button").click(window.workspace.time_input_form);
                    $("#time_drop").click(window.workspace.time_input_form);

                   
                    
                    //$("#droppable-inner").data("content",$("#instArea").html());
                    break;
                case 'task_searchEvents':
                    element = new ActionViewer();
                    element.init();
                    $("#event_button").click(window.workspace.event_input_form);
                    $("#event_drop").click(window.workspace.event_input_form);
                    $("#time_button").click(window.workspace.time_input_form);
                    $("#time_drop").click(window.workspace.time_input_form);
                    break;
                case 'task_chart':
                    
                    element = new ActionViewer();
                    element.init();
                    createchart();
                    $("#event_button").click(window.workspace.event_input_form);
                    $("#event_drop").click(window.workspace.event_input_form);
                    $("#time_button").click(window.workspace.time_input_form);
                    $("#time_drop").click(window.workspace.time_input_form);
                    break;
                case 'task_searchInstruments':
                    element = new ActionViewer();

                    element.init();
                    $("#time_button").click(window.workspace.time_input_form);
                    $("#time_drop").click(window.workspace.time_input_form);
                    break;
                case 'task_searchData':
                    element = new ActionViewer();
                    element.init();
                    $("#instruments_button").click(window.workspace.instrument_input_form);
                    $("#instruments_drop").click(window.workspace.instrument_input_form);
                    $("#time_button").click(window.workspace.time_input_form);
                    $("#time_drop").click(window.workspace.time_input_form);
                    break;
                case 'upload_vot':
                    element = new UploadViewer(imagePath,"ghost",text);
                    window.historyBar.addItem(element);
                    window.historyBar.render();
                  
                    break;
                default:
                    break;
            }//end case

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
            fnInitializeDatePicker();
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
