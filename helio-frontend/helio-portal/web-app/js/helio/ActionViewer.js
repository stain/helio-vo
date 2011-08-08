function ActionViewer() {
    var resultFilterTimeout;

    return {
        // Public methods
        resultContainerInit: function(data){
            
            $("#responseDivision").html(data);
            $("#result_area").html("Query Success");
            $("#result_button").remove();
            $("#displayableResult").html("");
            $("#ics_instrument").css("display","block");
            $("#ils_trajectories").css("display","block");
            $("#voTables").prepend($("#ics_instrument").clone());
            $("#voTables").prepend($("#ils_trajectories").clone());
            $("#ics_instrument").css("display","none");
            $("#ils_trajectories").css("display","none");
            $("#result_overview").css("display","table");
            $(":checkbox").unbind();
            $(":checkbox").removeAttr("checked");
            $("input:checkbox").change(function(){
                var checkboxColumn=$(this).attr("column");
                var filter_expression = "\0";
                
                $("input:checked").each(function(){
                    if($(this).attr("column")==checkboxColumn)
                        filter_expression=filter_expression+"|("+$(this).attr("name")+")";
                });
                
                if(filter_expression=="\0")
                    filter_expression="";
                
                if(resultFilterTimeout!=null)
                    clearTimeout(resultFilterTimeout);
                
                resultFilterTimeout=setTimeout(function(){
                    $("table#resultTable0").dataTable().fnFilter(filter_expression,checkboxColumn,true);
                },200);
            });

            $('#displayableResult').append($('#tables'));
            $('#displayableResult').css("display","block");

            $('.resultTable').each(function(){
                fnFormatTable(this.id);
            });
            $(".custom_button").button();
            $("#response_save_selection").click(function(){

                if($(".odd_selected").length <1 && $(".even_selected").length<1){
                    alert("Please select something first");
                    return;
                }

                var serviceName = $("#service_name").val();

                if(serviceName == 'HEC'){

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
                            if(settings.aoColumns[j].sTitle.trim() == 'time_start'){
                                time_start=j;
                            }
                            if(settings.aoColumns[j].sTitle.trim() == 'time_end'){
                                time_end=j;
                            }

                        }//end j

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
                        tr.append("<td><b>Range "+itr+":</b></td>"+
                            "<td>"+time_start_array[itr]+"</td>"+
                            
                            "<td>--</td><td>"+time_end_array[itr]+"</td>");

                        tr.append("<td><input type='hidden' name='maxDate' value='"+time_end_array[itr]+"'/></td>")
                        tr.append("<td><input type='hidden' name='minDate' value='"+time_start_array[itr]+"'/></td>")
                        
                        
                        table.append(tr);

                    }//end itr
                    var img =   $( "<img class='history_draggable' alt='"+"image missing"+"'/>" ).attr( "src",'../images/helio/circle_time.png' );
                    var superdiv = $('<div></div>')
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
                }else if(serviceName == 'ICS'){
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
                            if(settings.aoColumns[j].sTitle.trim() == 'obsinst_key'){
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

                        holder.append("<li>'"+instrument_string+"'<input id="+instrument_string+" type='hidden'  name='extra' value='"+instrument_string+"'/></li>");

                    }

                    $("#dialog-message").remove();
                    var div =$('<div></div>');
                    div.attr('id','dialog-message');
                    div.attr('title','Instrument Selection');

                    var html = window.workspace.getDivisions()["input_instruments"];
                    div.append(html);

                    div.append("*Items in red are not supported by our Data Search service therefore will not be saved.")
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
                                td2.append($("<div  style='margin-left:10px;margin-top:10px;;float:right' class='closeme ui-state-default ui-corner-all'><span class='ui-icon ui-icon-close'></span></div>"));

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
                                tr2 =$('<tr class="inner_label"><td>'+$("#task_label").val()+'</td><tr>')
                                table2.append(tr2);
                                //tr =$('<tr class="inner_label"><td>'+label+'</td><tr>')
                                //table.append(tr);
                                div.append(table2);
                                $("#historyContent").append(div);
                                $(".closeme").unbind();
                                $(".closeme").click(function(){
                                    $(this).parent().parent().parent().parent().parent().remove();
                                    saveHistoryBar();

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
                    alert("No Extractable Parameters");
                }
            });
            var rowpos = $('#displayableResult').position();
            if(rowpos!=null){
                $('html,body').scrollTop(rowpos.top);
            }
            $("#dialog-message").dialog( "close" );
            $("#dialog-message").remove();
        },
        
        init: function(){
            
            if($.cookie("minDate")==null)$.cookie("minDate","2003-01-01");
            if($.cookie("maxDate")==null)$.cookie("maxDate","2003-01-03");
            if($.cookie("minTime")==null)$.cookie("minTime","00:00");
            if($.cookie("maxTime")==null)$.cookie("maxTime","00:00");
            $("#minDate").val($.cookie("minDate"));
            $("#maxDate").val($.cookie("maxDate"));
            $("#minTime").val($.cookie("minTime"));
            $("#maxTime").val($.cookie("maxTime"));

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
                        $('#extra_list').html(ui.draggable.data('event_data'));
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

            //$('.submit_button').button({disabled: !$(".catalogueSelector input:checked").val()});
            $.collapsible(".queryHeader","group1");
            $.collapsible(".advancedParameters","group2");
            $( ".custom_button").button();
            $( ".submit").button();          
        },
        renderContent: function() {
            
            
            if(history.length > 0){

                var result = history[step].result;
                var formData = history[step].formData;
                var advancedSearch= history[step].advancedSearch;
                _unserialize(formData,advancedSearch);
                $("#responseDivision").html(result);
                $('#displayableResult').append($('#tables'));
                $('#displayableResult').css("display","block");
                fnInitSave();
                $("#responseDivision").html("");
               
                $('.resultTable').each(function(){
                    fnFormatTable(this.id);
                });
                _initSolidElements();
            }
            _initGhostElements();           
            $(".tooltipme").tooltip({
                position: "top center",
                delay: 0,
                predelay:0
            });
        },//end renderContent
        render: function(key,current) {
            if (typeof console!="undefined")console.info("ActionViewer :: render ->"+ key +" current "+current);

            if(history.length <= 0){

                //var title ="Element contains no data";
                var title ="";
                var div = $("<div  title='"+title+"' class='floaters'></div>");
                var table =$('<table border="0" cellpadding="0" cellspacing="0"></table>');
                var tr =$("<tr></tr>");
                var td =$("<td></td>");
                var img =   $( "<img alt='" +"image missing"+"' class='ghost'  />" ).attr( "src",imagePath );
                td.append(img);
                tr.append(td);
                if(label != null){
                    td =$("<td></td>");
                    td.css("padding-left","3px");
                    td.append(label);
                    tr.append(td);
                }
                if(key==current){
                    div.addClass('current');
                }
                table.append(tr);
                div.append(table);
                $("#historyContent").append(div);
                type="ghost";
            }else{
                //var title ="<div>Number of elements: "+history.length+"<br>Label: "+label+"<br>Service name: "+serviceName+"</div>";
                var title ="";
                var div = $("<div  title='"+title+"' class='floaters'></div>");
                var table =$('<table border="0" cellpadding="0" cellspacing="0"></table>');
                var tr =$("<tr></tr>");
                var td =$("<td></td>");
                var img =   $( "<img alt='"+"image missing"+"'/>" ).attr( "src",imagePath );
                td.append(img);
                tr.append(td);
                if(label != null){
                    td =$("<td></td>");
                    td.css("padding-left","3px");
                    td.append(label);
                    tr.append(td);
                }
                table.append(tr);
                div.append(table);
                if(key==current){
                    div.addClass('current');
                    
                    for(var i=0;i < history.length;i++){
                        var pageDiv =$("<div style='cursor:pointer' id='"+i+"' class='ui-state-default new1'>"+"Page "+(i+1)+"</div>");
                        pageDiv.click(function(){

                            step = parseInt($(this).attr('id'),10);
                            $('#currentDisplay').fadeOut(300, function(){
                                window.historyBar.cleanGhost();
                                window.historyBar.setFocus(key);
                            //window.historyBar.render();
                            });
                        

                        });
                        div.append(pageDiv);
                    }
                    

                }else{
                    div.css("cursor","pointer");
                    div.click(function() {
                        if (typeof console!="undefined")console.info("ActionViewer :: item clicked ->"+ key);

                        $('#currentDisplay').fadeOut(300, function(){
                            window.historyBar.cleanGhost();
                            window.historyBar.setFocus(key);
                        //window.historyBar.render();
                        });


                    //var item = window.historyBar.getItem(key);


                    });//end dbclick
                }
                
                $("#historyContent").append(div);
                
                type="solid";

               
            }//end else
        }//end render
    };//end public methods
}//end class
