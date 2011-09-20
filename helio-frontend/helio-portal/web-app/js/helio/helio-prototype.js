


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
            formatButton($(".custom_button"))

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
            formatButton($(".custom_button"))
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

            if($("#service_name").val()=='ICS'){

                var div2 =$('<div></div>');
                var html = window.workspace.getDivisions()["input_instruments"];
                div2.append(html);
                    
                $("#testdiv").append(div2);
                     
                if($("#input_table td[internal='"+aData[2]+"']").length ==1){
                    $(nRow).attr("class","item_found "+$(nRow).attr('class'));

                            
                }else{
                    $(nRow).attr("class","item_missing "+$(nRow).attr('class'));
                            
                }
                div2.remove();

            }
            
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





//javascript start
$(document).ready(function()
{


    //Person sss = new Person("dsa","dsa");

    if($.cookie("minDate") == null)$.cookie("minDate","2003-01-01T00:00:00",{
        expires: 30
    });
    if($.cookie("maxDate") == null)$.cookie("maxDate","2003-03-01T00:00:00",{
        expires: 30
    });
    //$.cookie("maxDate",selectedDate);
    
    if($.cookie("helioSession")==null){
        
        $.cookie("helioSession",$("#HUID").val(),{
            expires: 30
        });
    }

    

    var workspace = new Workspace();
    window.workspace = workspace;
    window.workspace.init();

    var history = new History();
    window.historyBar = history;
    window.historyBar.init();
    $( "#tabs" ).tabs();
    $( ".reset_session" ).click(function(){


        
    
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
                    saveHistoryBar();
                    $("#dialog-message").dialog( "close" );
                    $("#dialog-message").remove();

                },
                No: function(){




                    $("#dialog-message").dialog( "close" );
                    $("#dialog-message").remove();

                }
            }
        });
    });

    getHistoryBar();
    //new TimeForm().display();
    formatButton($(".custom_button"))
    $( ".menu_item" ).click(function() {

        var task_name = $(this).attr("id");
        
        window.workspace.createItem(task_name);
        
  
    });
    
$("#content-slider").slider({
    animate: true,
    change: handleSliderChange,
    slide: handleSliderSlide
  });

});
function handleSliderChange(e, ui)
{
  var maxScroll = $("#historyScrollWidth").attr("scrollWidth") -
                  $("#historyScrollWidth").width();
  $("##historyScrollWidth").animate({scrollLeft: ui.value *
     (maxScroll / 100) }, 1000);
}

function handleSliderSlide(e, ui)
{
  var maxScroll = $("#historyScrollWidth").attr("scrollWidth") -
                  $("#historyScrollWidth").width();
  $("#historyScrollWidth").attr({scrollLeft: ui.value * (maxScroll / 100) });
}
function date_form_validate(itr){

    try{
        var maxDate = $("#maxDate"+itr).val();
        if(maxDate.indexOf("T") == -1){
            maxDate = maxDate + "T00:00:00"
            $("#maxDate"+itr).val(maxDate);
        }
        var minDate = $("#minDate"+itr).val();
        if(minDate.indexOf("T") == -1){
            
            minDate = minDate + "T00:00:00";
            $("#minDate"+itr).val(minDate);
            
        }
        var IsoDate = new RegExp("^(19|20)\\d\\d[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])T([0-9]{2}):([0-9]{2}):([0-9]{2})$");
        
        

       

        
        var matches = IsoDate.exec(minDate);
        
        if(matches ==null){
            
            return false;
        }
        matches = IsoDate.exec(maxDate);
        if(matches ==null){
            
            return false;
        }
       
        return true;

    }
    catch(err){
        
        return false;
    }
};

function createmission(selector){

    var holder= $('<div></div>');
            
    $("#dialog-message").remove();
    var div =$('<div></div>');
    div.attr('id','dialog-message');
    div.attr('title','Argument Selection');

    var html = window.workspace.getDivisions()["input_datamining"];
    div.append(html);

    $("#testdiv").append(div);
            

    formatButton($(".custom_button"))
    $('#dialog-message').dialog({
        modal: true,
        height:630,
        width:800,
        close: function(){

            $("#dialog-message").remove();
        },
        buttons: {
            AddBlock: function(){

               div.append("<div style='width:100%;border-bottom:1px solid black'><center> <label>Logical Operator:</lablel><select><option>AND</option><option>OR</option></select></center>");
               div.append(html);
     
            },
            Help: function(){
                       
            },
            Cancel: function() {
                $("#dialog-message").dialog( "close" );
            },
            Ok: function() {
                       $("#block_area").html('');
            var img = $(".block_img").attr('src');

            $(".input_form_table").each(function(){
                $("#block_area").append("<img src='"+ img +"' />");
                $("#block_area").append(" AND ");
            });


                //console.debug($("#block_area").lastChild());


                $("#dialog-message").dialog( "close" );
                $("#dialog-message").remove();

            }
        }
    });
}




//Helper Functions
function pr(name){
    console.debug($("#"+name).val());
    return $("#"+name)
}

function formatButton(selector){

    selector.each(function(){
        if(!$(this).hasClass('ui-button')){
            $(this).button();
        }
    });


   
}