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
            ingestDivision("task_hec","#displayableHEC");
            ingestDivision("task_ics","#displayableICS");
            ingestDivision("input_time","#displayableInputTime");
            ingestDivision("input_instruments","#displayableInputInstruments");
            ingestDivision("input_event","#displayableInputEvent");
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

                        $("#instruments_drop").css('background','url(../images/helio/circle_inst.png) no-repeat 12px 12px');
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
                        
                        $("#event_drop").css('background','url(../images/helio/circle_event.png) no-repeat 12px 12px');
                        $("#dialog-message").dialog( "close" );
                        $("#dialog-message").remove();
                        window.workspace.evaluator();

                    }
                }
            });
        },
        time_input_form: function(){

            var div =$('<div></div>');
            div.attr('id','dialog-message');
            div.attr('title','Date Selection');

            var html = divisions["input_time"];
            div.append(html);

            $("#testdiv").append(div);
            fnInitializeDatePicker2();
            $("#input_time_range_button").click(function(){

                //    var range_html = $("<tr></tr>");
                //    range_html.append($("#input_time_range_1").html());
                //
                //   $("#input_time_range_list").append(range_html);
                // fnInitializeDatePicker2();
                });
            $(".custom_button").button();
            $('#dialog-message').dialog({
                modal: true,
                height:530,
                width:700,
                buttons: {
                    Ok: function() {
                        var table =$("<table>");
                        
                        table.append("<tr><td><b>Range 1:</b></td>"+
                            "<td>"+$("#minDate").val()+"</td>"+
                            "<td>"+$("#minTime").val()+"</td>"+
                            "<td>--</td><td>"+$("#maxDate").val()+"</td>"+
                            "<td>"+$("#maxTime").val()+"</td></tr>");
                        $("#time_area").html(table);
                        $("#time_area").append("<input type='hidden' name='maxDate' value='"+$("#maxDate").val()+"'>")
                        $("#time_area").append("<input type='hidden' name='minDate' value='"+$("#minDate").val()+"'>")
                        $("#time_area").append("<input type='hidden' name='maxTime' value='"+$("#maxTime").val()+"'>")
                        $("#time_area").append("<input type='hidden' name='minTime' value='"+$("#minTime").val()+"'>")
                        $("#time_drop").css('background','url(../images/helio/circle_time.png) no-repeat 12px 12px')
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
        setElement: function(element){
            if (typeof console!="undefined")console.info("Workspace :: setElement");
            //if(element ==null)return;
            this.element = element;
            element.renderContent();
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
                   
                    
                        
                    $("#time_button").click(window.workspace.time_input_form);
                    
                    break;
                case 'task_ics':
                    element = new ActionViewer();
                    element.init();
                   
                    $("#time_button").click(window.workspace.time_input_form);
                    
                    break;
                case 'task_ils':
                    element = new ActionViewer();
                    element.init();
                    
                    $("#time_button").click(window.workspace.time_input_form);
                                   
                    break;
                case 'task_dpas':
                    element = new ActionViewer();
                    element.init();
                   

                   
                    
                    //$("#droppable-inner").data("content",$("#instArea").html());
                    break;
                case 'task_searchEvents':
                    element = new ActionViewer();
                    element.init();
                    $("#event_button").click(window.workspace.event_input_form);
                    $("#time_button").click(window.workspace.time_input_form);
                    break;
                case 'task_searchInstruments':
                    element = new ActionViewer();

                    element.init();
                    $("#time_button").click(window.workspace.time_input_form);
                    break;
                case 'task_searchData':
                    element = new ActionViewer();
                    element.init();
                    $("#instruments_button").click(window.workspace.instrument_input_form);
                    $("#time_button").click(window.workspace.time_input_form);
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
