/*
 *clears the datefields back to the original state when a droppable circle is dragged out of target
 *clears the highlighting of the droparea as well
 *@TODO: needs to be reworked into a droppable item methods in their corresponding viewer
 **/
function fnclearDateTexts2(){
    $(".hideDates").css("display","block");
    $(".biggerInput").remove();
    $(".TextAreas").css("display","none");
    $(".minDateList").val("");
    $(".maxDateList").val("");
    $(".resultDroppable" ).removeClass( "ui-state-highlight" );
    $(".resultDroppable2" ).removeClass( "ui-state-highlight" );

    $("#instArea").html($("#droppable-inner").data("content"));
    //$(".tooltip").css("display","none");
}


/*
 *method called when the ajax query for advanced parameters
 *@TODO: needs to me worked into the actionviewer class
 */
function fnOnCompleteGetColumns(){
    if (typeof console!="undefined")console.info("fnOnCompleteGetColumns");
    $(".column-reset").button();
    $(".column-reset").click(function(){
		
        $(".columnSelection").val("");
    });
    $(".columnSelection").keyup(function(){
        mysubmit();
		
    });
    $.collapsible(".advancedParameters","group2");

}




/*
 *method called when submiting a query to gather the data of the advanced parameters fields and convert it into a single line by filling out the whereField
 *works in conjuction with a listener set on the .columnSelection onChange
 *
 *@TODO: rework all queries into a similar method using the forms plugin for jquery
 */

function mysubmit(){
    if (typeof console!="undefined")console.info("mysubmit");
    // reset where field
    $("#whereField").val("");

    // loop over all extra parameters
    $(".columnSelection").each(function(i){
        if($(this).val() == ""){
            // nothing to do
        } else {
            var value = $(this).val();
            var id = $(this).attr('name');

            if($("#whereField").val()!=""){
                var prevVal = $("#whereField").val();
                $("#whereField").val(prevVal + ";" + id + "," + value);
            }else{
                $("#whereField").val(id + "," + value);
            }
        }
        return true;
    });
}

/*
 *test method to check functionality of datepicker
 *@TODO: rework into the actionviewer class
 */
function fnInitializeDatePicker(){
    /**
    var dates = $('#minDate, #maxDate').datepicker({
        
        showOn: "button",
        buttonImageOnly: true,
        buttonImage: "../images/icons/calendar.gif"
        
  
    });
     **/

    var dates = $('#minDate, #maxDate').datepicker({
        defaultDate: "+1w",
        
        yearRange: '1970:2011',
        dateFormat: 'yy-mm-dd',
        changeMonth: true,
        showOn: "button",
        buttonImageOnly: true,
        buttonImage: "../images/icons/calendar.gif",
        changeYear: true,
        numberOfMonths: 1,
        onSelect: function(selectedDate) {
           
            //this.id == "minDate" ? window.minDate = selectedDate : window.maxDate = selectedDate;
            this.id == "minDate" ? $.cookie("minDate",selectedDate) : $.cookie("maxDate",selectedDate);

            var option = this.id == "minDate" ? "minDate" : "maxDate";
            var instance = $(this).data("datepicker");
            var date = $.datepicker.parseDate(instance.settings.dateFormat || $.datepicker._defaults.dateFormat, selectedDate, instance.settings);
            dates.not(this).datepicker("option", option, date);

        }
    });
    $("#minDate").keyup(function(){
        $.cookie("minDate",$(this).val());
    });
    $("#maxDate").keyup(function(){    
        $.cookie("maxDate",$(this).val());
    });
    $("#minTime").keyup(function(){
        $.cookie("minTime",$(this).val());
    });
    $("#maxTime").keyup(function(){
        $.cookie("maxTime",$(this).val());
    });

    var validateDates = function(){
        try{
            var maxTime = $("#maxTime").val();
            var minTime = $("#minTime").val();
            var maxDate = $("#maxDate").val();
            var minDate = $("#minDate").val();
            var IsoDate = new RegExp("^([0-9]{4})-([0-9]{2})-([0-9]{2})$");
            var IsoTime = new RegExp("^([0-9]{2}):([0-9]{2})$");
            var matches = IsoDate.exec(maxDate);

            if(matches ==null){
                $("#maxDate").addClass("inputError");
            }else{
                $("#maxDate").removeClass("inputError");
                //var maxDateObject = new Date(matches[1], (matches[2] - 1), matches[3]);
            
            }
            matches = IsoDate.exec(minDate);
            if(matches ==null){
                $("#minDate").addClass("inputError");
            }
            else{
                $("#minDate").removeClass("inputError");
            }
            matches = IsoTime.exec(maxTime);
            if(matches ==null){
                $("#maxTime").addClass("inputError");
            }else{
                $("#maxTime").removeClass("inputError");
            }
            matches = IsoTime.exec(minTime);
            if(matches ==null){
                $("#minTime").addClass("inputError");
            }
            else{
                $("#minTime").removeClass("inputError");
            }


            /*
            if(matches[1]!=null&&matches[2]&&matches[3]){

            }
            $("#dateError").remove();
             */
        }
        catch(err){
            $(".dateTable").append("<span style='color:red' id='dateError'>Error occurred. Please revise your input</span>");
        }
    };
    $("#maxTime,#minTime,#minDate,#maxDate").keydown(validateDates);
    $("#maxTime,#minTime,#minDate,#maxDate").keyup(validateDates);
}


/*
 *Method not in use
 *adds an extra row to the datatables for keeping track of what was selected previously
 *
 *@TODO: rework into the votable manager concept
 */
function fnAppendColumnSelected(){
    if (typeof console!="undefined")console.info("fnAppendColumnSelected");
    var nCloneTd = $( '<td></td>' );
    nCloneTd.text('0');
    //nCloneTd.css('display','none');

    var nCloneTh = $( '<th></th>' );
    nCloneTh.text('0');
    //nCloneTh.css('display','none');
    $('.resultTable tbody tr').each( function () {
        
        $(this).append( nCloneTd.clone() );
    } );

    $('.resultTable thead tr').each( function () {
        $(this).append( nCloneTh.clone() );
    } );
}

/*
 *initializes the droppable areas for the selected results.
 *@TODO: should be part of actionviewer
 */
function fnInitDroppable(){
    if (typeof console!="undefined")console.info("fnInitDroppable");
    
    $( ".resultDroppable2" ).droppable({
        activeClass: "ui-state-hover",
        hoverClass: "ui-state-active",
        greedy:true,
        accept: ".resultDraggable",
        out: function(event,ui) {
            ui.draggable.data('returnMe',true);
            ui.draggable.data('dropBox',this);
            $(this).droppable("enable");


        },
        over: function( event, ui ) {


            var item=window.historyBar.getItem(ui.draggable.attr("id"));
            var content =item.getContent();

            var flag = false;
            for ( i in content){
                var temp =content[i]["obsinst_key "];
                if(temp != null)flag =true;
            }
            
            

            if(ui.draggable.attr('src') != "../images/icons/toolbar/circle_inst.png" ){
                if(ui.draggable.attr('src') != "../images/icons/toolbar/circle_both.png")
                    $(this).droppable("disable") ;
                
            }
            if(!flag)$(this).droppable("disable");
        },
        drop: function( event, ui ) {
            $(".dropInput2").remove();
            var already_dragged = $(this).data('dropped_items');
            if(already_dragged == ""){
                $(this).data('dropped_items',ui.draggable);
            } else {
                if(already_dragged != ui.draggable)
                    $( already_dragged).animate({
                        "left": $(already_dragged).data("Left"),
                        "top": $( already_dragged).data("Top")
                    }, "slow",function(){
                });
                $(this).data('dropped_items',ui.draggable);

            }

            $( this ).addClass( "ui-state-highlight" );
            var item=window.historyBar.getItem(ui.draggable.attr("id"));
            var content =item.getContent();



            $("#instArea").empty();
            var instList = [];
            for ( i in content){
                //obsinst_key
                var temp =content[i]["obsinst_key "];
                if(temp != null)instList.push(temp);
                if(temp != null)$('#instArea').append('<option selected value="'+temp+'">'+temp+'</option>');


            }
            $(".resultDroppable2").css('background-image','url(../images/icons/toolbar/circle_inst.png)');
            $("#instArea").selectBox('destroy');
            $("#instArea").selectBox().change( function() {
                $('.submit_button2').button({
                    disabled: $(".selectBox-selected").length ==0
                });
            });


            $('.submit_button2').button({
                disabled: ($(".selectBox-selected").length ==0)
            });
            var revertButton = $('<input style="margin-top:10px" class="custom_button dropInput2" value="Revert Drop" type="button"/>');
            revertButton.click(function(){
                $(".dropInput2").remove();
                $("#instArea").html($("#droppable-inner").data("content"));
                $("#instArea").selectBox('destroy');
                $("#instArea").selectBox().change( function() {
                    $('.submit_button2').button({
                        disabled: $(".selectBox-selected").length ==0
                    });
                });
                $('.submit_button2').button({
                    disabled: ($(".selectBox-selected").length ==0)
                });

                $(".resultDroppable2").css('background-image','url(../images/helio/circle_inst_grey.png)');
            });
            $("#instArea").parent().parent().append(revertButton);
            $(".custom_button").button();

        }
    }).data('dropped_items',"");


    $( ".resultDroppable" ).droppable({
        activeClass: "ui-state-hover",
        hoverClass: "ui-state-active",
        greedy:true,
        accept: ".resultDraggable",

        over: function( event, ui ) {

            var item=window.historyBar.getItem(ui.draggable.attr("id"));
            var content =item.getContent();

            var flag = false;
            for ( i in content){
                var start = content[i]["time_start "];
                
                if(start != null)flag =true;
            }
            if(ui.draggable.attr('src') != "../images/icons/toolbar/circle_time.png" ){
                if(ui.draggable.attr('src') != "../images/icons/toolbar/circle_both.png")
                    $(this).droppable("disable") ;

            }

            if(!flag)$(this).droppable("disable");
            $(".tooltip").css("display","none");
        },

        out: function(event,ui) {
            // ui.draggable.data('returnMe',true);
            // ui.draggable.data('dropBox',this);
            

            $(".tooltip").css("display","none");

        },

        drop: function( event, ui ) {
            $(".dropInput").remove();
            $(".hideDates").css("display","none");
            $( this ).addClass( "ui-state-highlight" );
            var item=window.historyBar.getItem(ui.draggable.attr("id"));
            var content =item.getContent();
            //$(".minDateList").val("");
            //$(".maxDateList").val("");
            
            //var maxTemp = [];
            //var minTemp = [];
            for ( i in content){
                //  var temp =content[i]["time_start "];
                //  if(temp != null)minTemp.push(temp);
                //  temp =content[i]["time_end "];
                //  if(temp != null)maxTemp.push(temp);
            

                var carry =$("<div id='carry'></div>");
                var time_start;
                var time_end;
                for(j in content[i]){

                    if(j == "time_start "){
                        time_start = content[i][j];
                        carry.data("time_start",i+","+j);
                    }
                    if(j == "time_end "){
                        time_end = content[i][j];
                        carry.data("time_end",i+","+j);
                    }

                }//j
                if(time_start != null && time_end != null) {
                    

                    $(".dateTable").append(
                    '<tr class="biggerInput dropInput">'+
                        '<td><input name="minDateList" type="text" index="'+carry.data("time_start")+'" value="'+ time_start+'"/><div class="adding cbutton">+</div><div class="subbing cbutton">-</div></td>'+
                        '<td><!--input type="checkbox" checked="checked"/--></td>'+
                        '<td><input name="maxDateList" type="text" index="'+carry.data("time_end")+'" value="'+ time_end+'"/><div class="adding cbutton">+</div><div class="subbing cbutton">-</div></td></tr>');
                    $(".resultDroppable").css('background-image','url(../images/icons/toolbar/circle_time.png)');
                    
                }else if(time_start != null) {
                
                    $(".dateTable").append(
                    '<tr class="biggerInput dropInput">'+
                        '<td><input name="minDateList" type="text" index="'+carry.data("time_start")+'" value="'+ time_start+'"/><div class="subbing cbutton">-</div><div class="adding cbutton">+</div></td>'+
                        '<td><!--input type="checkbox" checked="checked"/--></td>'+
                        '<td><input name="maxDateList" type="text" index="'+carry.data("time_start")+'" value="'+ time_start+'"/><div class="subbing cbutton">-</div><div class="adding cbutton">+</div></td></tr>');
                    $(".resultDroppable").css('background-image','url(../images/icons/toolbar/circle_time.png)');
                }
            }//i

            var revertButton = $('<input style="margin-top:10px" class="custom_button dropInput" value="Revert Drop" type="button"/>');
            revertButton.click(function(){
                $(".dropInput").remove();
                $(".hideDates").css('display','inherit');

                $(".resultDroppable").css('background-image','url(../images/icons/toolbar/circle_time.png)');
                $(".resultDroppable").css('background-image','url(../images/helio/circle_time_grey.png)');
            });
            $(".dateTable").append(revertButton);
            $(".custom_button").button();
            $(".cbutton").button();


            //$(".minDateList").val(minTemp);
            //$(".maxDateList").val(maxTemp);
            $(".subbing").click(function(){
                var time_start = $(this).parent().children("input").val();
                var newTime = dateCalculator(time_start,"-");
                $(this).parent().find("input").val(newTime);
            });
            $(".adding").click(function(){

                var time_start = $(this).parent().children("input").val();
                var newTime = dateCalculator(time_start,"+");
                $(this).parent().find("input").val(newTime);
            });
                
          
            $(".tooltip").css("display","none");
        }//drop
    }).data('dropped_items',"");


}




/*
 *Test method to check data being selected properly
 *
 *@TODO: check if this method is still in use and clean if its not
 */
function fnGetSelected( oTableLocal )
{

    if (typeof console!="undefined")console.error("fnGetSelected");
    var aSelected = new Array();
    var aaData = oTableLocal.fnSettings().aaDataMaster;
    for ( var i=0 ; i<aaData.length ; i++ )
    {
        if ( aaData[i][5] == 1 )
        {
            aSelected.push( i );
        }
    }

    return aSelected;
}


/*
 *callback method that is used to keep track of whats been selected in a dataTable
 *contents are kept in an invisible division called #testdiv and the elements added are .resCont
 *they have attributes that keept track of the row selected and header corresponding to that row
 */
function fnAddSelectedRow(pos,aData,oTable){
    if (typeof console!="undefined")console.info("fnAddSelectedRow");
        
    
    var totalResult =[];
    var headers =oTable.fnSettings().aoColumns;
    for (i in headers){
        totalResult.push( headers[i].sTitle);
            
            
    }
    
    var flag =true;
    var tableId = oTable.attr("id");
    pos= pos+tableId;

    $('.resCont').each(function(i, val) {
        var currentPos =  $(this).text();
        
        if(currentPos==pos){
            
            $(this).remove();
            flag =false;
            return;
        }
    });
    if(flag){

        var div = $('<div></div>');
        div.addClass('resCont');
        div.text(pos);
        div.attr("title",aData);
        div.attr("title2",totalResult);
        $('#testdiv').append(div);
        //$("#testdiv div[title]").tooltip();
    }
    if($('.resCont').length !=0){
        $('#testdiv').css('display','none');
    }else{
        $('#testdiv').css('display','none');
    }
    $("#resultSelectionCounter").find('span').text($('.resCont').length);
}

function fnOnErrorAsynchQuery(xmlHttpRequest,textStatus,errorThrown){
    window.historyBar.render();

    var stackTrace = $("#errorResponse").html();
    if(textStatus=="timeout"){
        stackTrace = "service took too long to answer";
    }
    var div =$('<div></div>');
    div.attr('id','dialog-message');
    div.attr('title','Error');
    var message = "An unexpected error occured in the HELIO Front End. We apologize for the inconvenience. Please check your internet connection and try again.";
        
    div.append('<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 50px 0;"></span>'+message+'</p><br><p><b>Error context</b>: <span style="color:red;" >'+stackTrace+'</span></p>');
    $("#testdiv").append(div);


    $('#dialog-message').dialog({

        modal: true,
        buttons: {
            Ok: function() {
                $( this ).dialog( "close" );
            }
        }
    });




}
/*
 *Called when the ajax asynchQuery is finished
 *gets the currently displayed element and adds a step to it. Element are always ResultViewer
 *@TODO: should be moved to ResultViewer
 *
 */
function fnOnComplete(data,textStatus){
    if (typeof console!="undefined")console.info("fnOnComplete");
    
    if($("#errorResponse").length != 0){
        window.historyBar.render();
        
        
        var div =$('<div></div>');
        div.attr('id','dialog-message');
        div.attr('title','Error');
        var message = "An unexpected error occured in the HELIO Front End. We apologize for the inconvenience. Please check your internet connection and try again.";
        var stackTrace = $("#errorResponse").html();
        div.append('<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 50px 0;"></span>'+message+'</p><br><p><b>Error context</b>: <span style="color:red" >'+stackTrace+'</span></p>');
        $("#testdiv").append(div);


        $('#dialog-message').dialog({

            modal: true,
            buttons: {
                Ok: function() {
                    $( this ).dialog( "close" );
                }
            }
        });

        $("#errorResponse").remove();

        return;
    }




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

        $(":checkbox").change(function(){
            var checkboxColumn = $(this).attr("column");
            $("#resultTable0").dataTable().fnFilter("",checkboxColumn);
            var filter_array = {};
            $(":checked").each(function(){
                var checkboxName = $(this).attr("name");
                checkboxColumn = $(this).attr("column");
                var filter_column_value = filter_array[checkboxColumn] ==null ? "":filter_array[checkboxColumn];
                filter_array[checkboxColumn] = filter_column_value ==""?checkboxName: filter_column_value+"|"+checkboxName;

            });

            for (var key in filter_array) {

                $("#resultTable0").dataTable().fnFilter(filter_array[key],key,true);
            }


        //$("#resultTable0").dataTable().fnFilter(checkboxName,checkboxColumn);



        });

        $('#displayableResult').append($('#tables'));
        $('#displayableResult').css("display","block");

        $('.resultTable').each(function(){
            fnFormatTable(this.id);
        });
        $(".custom_button").button();
        $("#response_save_selection").click(function(){



            var serviceName = $("#service_name").val();

            if(serviceName == 'upload'){

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


                    var time_start_string =time_start_array[itr];
                    var fields = time_start_string.split('T');
                    var minDate =fields[0];
                    fields =fields[1].split(':');
                    var minTime = fields[0] +":"+fields[1];
                    var time_end_string =time_end_array[itr];
                    fields = time_end_string.split('T');
                    var maxDate =fields[0];
                    fields =fields[1].split(':');
                    var maxTime = fields[0] +":"+fields[1];
                    var tr = $('<tr></tr>');
                    tr.append("<td><b>Range "+itr+":</b></td>"+
                        "<td>"+minDate+"</td>"+
                        "<td>"+minTime+"</td>"+
                        "<td>--</td><td>"+maxDate+"</td>"+
                        "<td>"+maxTime+"</td>");

                    tr.append("<td><input type='hidden' name='maxDate' value='"+maxDate+"'/></td>")
                    tr.append("<td><input type='hidden' name='minDate' value='"+minDate+"'/></td>")
                    tr.append("<td><input type='hidden' name='maxTime' value='"+maxTime+"'/></td>")
                    tr.append("<td><input type='hidden' name='minTime' value='"+minTime+"'/></td>")
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
                div.attr('title','Instrument Edition');

                var html = window.workspace.getDivisions()["input_instruments"];
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



                $("#extra_list_form").html(holder.html());
                $("#extra_list_form").addClass('candybox');

                $("#extra_list_form input").each(function(){
                    $("#input_table td[internal='"+$(this).attr("id")+"']").parent().addClass('row_selected');
                });

                $("#input_filter").keyup(function(){
                    $("#input_table").dataTable().fnFilter($(this).val());
                });

                $('#input_table tr').click( function() {
                    var row =$(this).find('td').attr("internal").trim();
                    if ( $(this).hasClass('row_selected') ){
                        $(this).removeClass('row_selected');


                        $("#"+row).parent().remove();
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


                            var img =   $( "<img class='history_draggable' alt='"+"image missing"+"'/>" ).attr( "src",'../images/helio/circle_inst.png' );
                            var div = $("<div  title='"+"noTitle"+"' class='floaters'></div>");
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
        $("#dialog-message").dialog( "close" );
        $("#dialog-message").remove();
  
    

}
function fnFormatTableNoSelection(tableName){
    if (typeof console!="undefined")console.info("fnFormatTable");

    //Run some code here

    $("#"+tableName).dataTable({
        "bJQueryUI": true,
        "bAutoWidth": true,
        "bRetrieve":true,
        "bDestroy":true,
        "bLengthChange": true,
        "sPaginationType": "full_numbers",
        "sScrollX": "100%",
        "iDisplayLength": 25,
        "aLengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
        //"sScrollXInner": "100%",
        "bScrollCollapse": true
        


    });

}

/*
 *Formats every datatable in the system and adds listeners to the rows to be clicked
 *
 *@param tableName: takes in the id of the datatable to be parsed, data table should have headers set and body set with matching number of elements
 *
 */

function fnFormatTable(tableName){
    if (typeof console!="undefined")console.info("fnFormatTable");
  
    //Run some code here

    $("#"+tableName).dataTable({
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
        "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
            
            var dataIndex =aData.length-1;
            $(nRow).unbind();
            if($(nRow).hasClass("odd") && $(nRow).hasClass("even_selected")||$(nRow).hasClass("odd") && $(nRow).hasClass("odd_selected")){
                $(nRow).removeClass("odd");
            }
            if($(nRow).hasClass("even") && $(nRow).hasClass("even_selected")||$(nRow).hasClass("even") && $(nRow).hasClass("odd_selected")){
                $(nRow).removeClass("even");
            }
            /* Deal with a click on each row */
            $(nRow).click( function() {


                var oTable =$(this).closest("table").dataTable();
                var pos =oTable.fnGetPosition(this);
                

                fnAddSelectedRow(pos,aData,oTable);

                if ( aData[dataIndex] == 1 )
                {
                    aData[dataIndex] = 0;
                }
                else
                {
                    aData[dataIndex] = 1;
                }


                this.className = (aData[dataIndex] == 1) ?
                    this.className+'_selected' :
                    this.className.replace( /_selected/, "" );
            } );



            return nRow;
        }


    });

}

/*
 *Initializes the save result button, creates the new selected result element and adds it to the history bar with its relevant content
 *@TODO: should be moved into resultviewer since its an action that can only be done in there.
 */
function fnInitSave(){
    if (typeof console!="undefined")console.info("fnInitSave");
    $("#resultSelectionSave").click(function(){
        if (typeof console!="undefined")console.info("Save selection clicked");

        if($(".resCont").length==0){
            var div =$('<div></div>');
            div.attr('id','dialog-message');
            div.attr('title','Information');
            var message ="You need to select at least one row from your results at the bottom";
            div.append('<p><span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;"></span>'+message+'</p>');
            $("#testdiv").append(div);


            $('#dialog-message').dialog({

                modal: true,
                buttons: {
                    Ok: function() {
                        $( this ).dialog( "close" );
                    }
                }
            });
                
            return;

        }
        $(".resultTable").each(function(){

            if($(this).attr("id")!= ""){
                var id =$(this).attr("id");
                id =id.replace("_wrapper","");
                $("#"+id).dataTable().fnDestroy();
            }
        });
        
        $(".resultTable").find('tbody').find("tr").each(function(){            
            $(this).removeClass("gradeB");
            if($(this).attr("class") ==""){
                $(this).remove();
            }
        });
        //removes empty tables from the result viewer
        $(".resultTable tbody").each(function(){
            if(!$(this).find("tr").length){
                $(this).parent().remove();
            }
        });
        var tablesHtml =$("#voTables").html();
        var indexes = new Array();
        var count =0;
        var totalResult = [];
        $(".resCont").each(function(){
            indexes.push($(this).text());
            
            count++;
            $(this).remove();
            var rowData = $(this).attr("title").split(",");

            var colNames = $(this).attr("title2").split(",");

            var partialResult =[];
            for(i in colNames){

                partialResult[colNames[i]]=rowData[i];
            }
            totalResult.push(partialResult);
        });


        var element = new ResultViewer("../images/icons/toolbar/circle_empty.png","resultSelection",tablesHtml,totalResult,indexes,window.historyBar.getCurrent().getServiceName());
        window.historyBar.addItem(element);
        window.historyBar.render();
        /*
        $(".even_selected").each(function(){
            $(this).removeClass("even_selected");
            $(this).addClass("even");
        });
        $(".odd_selected").each(function(){
            $(this).removeClass("odd_selected");
            $(this).addClass("odd");
        });
         */
        
        $(".resCont").remove();
        

        $('.columnInputs').html("");
        

        

        

        
     
    });//end click
}
/* Creates a popup for the help section
 * @params url,windowname,w,h,x,y pretty self explanatory just set the initial size and position of the new window
 *
 */
function myPopup(url,windowname,w,h,x,y){
    if (typeof console!="undefined")console.info("myPopup");
    var mywin = window.open(url,windowname,"resizable=no,toolbar=no,scrollbars=yes,menubar=no,status=no,directories=no,width="+w+",height="+h+",left="+x+",top="+y+"");
    mywin.focus();
}
/*
 * clears the current selection of the catalogue selectors in the actions
 * @TODO: if selectors are staying theway they are, move to ResultViewer
 */
function fnOnChangeHistoryFilterSelect(event){
    if (typeof console!="undefined")console.info("fnOnChangeHistoryFilterSelect");

    window.historyBar.setFilter($(event).find("option:selected").val());
    window.historyBar.render();
}
/**
 * method called before the asynchQuery is done to take all the parameters in advanced parameters and combine them into the box of the where field
 * @TODO: move to resultVierwer
 */
function fnBeforeQuery(){
    if (typeof console!="undefined")console.info("fnBeforeQuery");





    //@TODO: validation
    var mindate = $('#minDate').val();
    var maxdate = $('#maxDate').val();
    mysubmit();
}





//javascript start
$(document).ready(function()
{

    
    if(false){
        if($.cookie("helioSession")==null){
        
            $.cookie("helioSession",$("#HUID").val(),{
                expires: 30
            });
        }else{
            $("#dialog-message").remove();
            var div =$('<div></div>');
            div.attr('id','dialog-message');
            div.attr('title','Session Change');
            var html = $("<div>Do you want to start a new session?</div>")
            div.append(html);
            $("#testdiv").append(div);
            $('#dialog-message').dialog({
                modal: true,
                height:200,
                width:200,
                buttons: {


                    Yes: function() {
                        $.cookie("helioSession",$("#HUID").val(),{
                            expires: 30
                        });
                        $("#historyContent").html("");
                        $("#dialog-message").dialog( "close" );
                        $("#dialog-message").remove();

                    },
                    No: function(){

                        

                     
                        $("#dialog-message").dialog( "close" );
                        $("#dialog-message").remove();

                    }
                }
            })
        }


    }

    var workspace = new Workspace();
    window.workspace = workspace;
    window.workspace.init();

    var history = new History();
    window.historyBar = history;
    window.historyBar.init();
    $( "#tabs" ).tabs();
    getHistoryBar();
    //new TimeForm().display();
    $( ".custom_button").button();
    $( ".menu_item" ).click(function() {

        var task_name = $(this).attr("id");
        window.workspace.createItem(task_name);
        
        
        


  
    });
    


    /*
     *
     *
    
    window.historyBar.init();
    window.workspace = workspace;
    window.workspace.init();

    //TODO:hack of dates
    
    //Test code area
    if($.cookie("minDate")==null)$.cookie("minDate","2003-01-01");
    if($.cookie("maxDate")==null)$.cookie("maxDate","2003-01-03");
    if($.cookie("minTime")==null)$.cookie("minTime","00:00");
    if($.cookie("maxTime")==null)$.cookie("maxTime","00:00");

    //window.maxDate="2003-01-03";
    //window.minDate="2003-01-01";
    $("#section-navigation img[title]").tooltip({
        position: "bottom right",
        delay: 0,
        predelay:0
    });
   
    window.onbeforeunload = function () {
        return "Leaving this site will clear all your browsing history";
    };
     **/
});


function dateCalculator(dateString,operation){
    
                

    var fields = dateString.split("T");
    var first = fields[0].split("-");
    var second = fields[1].split(":");
    var d = new Date(first[0], first[1]-1, first[2], second[0], second[1], second[2], 0);
    
    operation == "+" ? d.setMinutes(d.getMinutes()+30):d.setMinutes(d.getMinutes()-30);
    


    var month = (d.getMonth()+1)<10? "0"+(d.getMonth()+1):(d.getMonth()+1);
    var day = d.getDate()<10?"0"+d.getDate():d.getDate();
    var hour =  d.getHours()<10?"0"+d.getHours():d.getHours();
    var minutes = d.getMinutes()<10?"0"+d.getMinutes():d.getMinutes();
    var seconds= d.getSeconds()<10?"0"+d.getSeconds():d.getSeconds();

    var dateOutput = d.getFullYear()+"-"+month+"-"+day+"T"+hour+":"+minutes+":"+seconds;
    
    return dateOutput;

}

function validatemydate(itr){
    try{
        var maxTime = $("#maxTime"+itr).val();
        var minTime = $("#minTime"+itr).val();
        var maxDate = $("#maxDate"+itr).val();
        var minDate = $("#minDate"+itr).val();
        var IsoDate = new RegExp("^([0-9]{4})-([0-9]{2})-([0-9]{2})$");
        var IsoTime = new RegExp("^([0-9]{2}):([0-9]{2})$");
        var matches = IsoDate.exec(maxDate);

        if(matches ==null){
            $("#maxDate"+itr).addClass("inputError");
        }else{
            $("#maxDate"+itr).removeClass("inputError");
            //var maxDateObject = new Date(matches[1], (matches[2] - 1), matches[3]);

        }
        matches = IsoDate.exec(minDate);
        if(matches ==null){
            $("#minDate"+itr).addClass("inputError");
        }
        else{
            $("#minDate"+itr).removeClass("inputError");
        }
        matches = IsoTime.exec(maxTime);
        if(matches ==null){
            $("#maxTime"+itr).addClass("inputError");
        }else{
            $("#maxTime"+itr).removeClass("inputError");
        }
        matches = IsoTime.exec(minTime);
        if(matches ==null){
            $("#minTime"+itr).addClass("inputError");
        }
        else{
            $("#minTime"+itr).removeClass("inputError");
        }


        /*
            if(matches[1]!=null&&matches[2]&&matches[3]){

            }
            $("#dateError").remove();
         */
    }
    catch(err){
            
    }
};

