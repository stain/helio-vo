function fnclearDateTexts(){
  $(".hideDates").css("display","block");
  $(".TextAreas").css("display","none");
  $(".minDateList").val("");
  $(".maxDateList").val("");
  $(".resultDroppable" ).removeClass( "ui-state-highlight" );
  $(".resultDroppable2" ).removeClass( "ui-state-highlight" );
  
$("#instArea").html($("#instArea").data("content"));
};

function fnOnCompleteGetColumns(){
    $(".columnSelection").keyup(function(){
        mysubmit();

    });
}


function mysubmit(){
    $("#whereField").val("");

    $(".columnSelection").each(function(i){
        if($(this).val() == ""){
            
        }else{

            var columnText = $(this).parent().text();
            var value = $(this).val();
            var id = $(this).attr('name');
            
            
            
            if($("#whereField").val()!=""){
                var prevVal = $("#whereField").val();
                $("#whereField").val(prevVal+";"+id+"."+columnText.trim()+","+value);
            }else{
                $("#whereField").val(id+"."+columnText.trim()+","+value);
            }


            

        }
        return true;
    });
//$('#pqlQuery').append($("#minDate").text());
}


function fnInitializeDatePicker(){
    return;
    var dates = $('#minDate, #maxDate').datepicker({
        defaultDate: "+1w",
        dateFormat: 'yy-mm-dd',
        yearRange: '1970:2011',
        changeMonth: true,
        showOn: "button",
        buttonImageOnly: true,
        buttonImage: "../images/icons/calendar.gif",
        changeYear: true,
        numberOfMonths: 1,
        onSelect: function(selectedDate) {
            
            var option = this.id == "minDate" ? "minDate" : "maxDate";
            var instance = $(this).data("datepicker");
            var date = $.datepicker.parseDate(instance.settings.dateFormat || $.datepicker._defaults.dateFormat, selectedDate, instance.settings);
            dates.not(this).datepicker("option", option, date);
        }
    });
}

//not in use
function fnInitializeDataTable(){
    $('.resultTable').dataTable({
        "bJQueryUI": true,
        "bAutoWidth": true,
        "bLengthChange": false,
        "sPaginationType": "full_numbers",
        "sScrollX": "100%",
        "sScrollXInner": "100%",
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
            /* Deal with a click on each row

            $(nRow).click( function() {
                
                var pos =oTable.fnGetPosition(this);

                fnAddSelectedRow(pos,aData);

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
            });
            */
            return nRow;
        }
    });
    
}

function fnAppendColumnSelected(){
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


function fnInitializeSingleElements(){

    $("#instArea").data("content",$("#instArea").html());


    $( ".resultDroppable2" ).droppable({
			activeClass: "ui-state-hover",
			hoverClass: "ui-state-active",
                        greedy:true,
                        accept: ".resultDraggable",
                        out: function(event,ui) {
                           ui.draggable.data('returnMe',true);
                           ui.draggable.data('dropBox',this);
                        },
			drop: function( event, ui ) {
                             var already_dragged = $(this).data('dropped_items');
                             if(already_dragged == ""){
                                 $(this).data('dropped_items',ui.draggable);
                             } else {
                                 if(already_dragged != ui.draggable)
                               $( already_dragged).animate({ "left": $(already_dragged).data("Left"),"top": $( already_dragged).data("Top")}, "slow",function(){
                            });
                            $(this).data('dropped_items',ui.draggable);

                             }
                            
                            $( this ).addClass( "ui-state-highlight" );
                            var item=window.varx.getItem(ui.draggable.attr("id"));
                            var content =item.getContent();
                            
                            
                            
                            $("#instArea").empty();
                            var instList = [];
                            for ( i in content){
                                //obsinst_key
                                var temp =content[i]["obsinst_key "];
                                if(temp != null)instList.push(temp);
                                if(temp != null)$('#instArea').append('<option selected value="'+temp+'">'+temp+'</option>');
                            
                                
                            }
                            
                            


                            
                            



			}
		}).data('dropped_items',"");


        $( ".resultDroppable" ).droppable({
			activeClass: "ui-state-hover",
			hoverClass: "ui-state-active",
                        greedy:true,
                        accept: ".resultDraggable",

                        over: function( event, ui ) {
                            
                        },
                        out: function(event,ui) {
                           ui.draggable.data('returnMe',true);
                           ui.draggable.data('dropBox',this);
                          
                            


                        },

			drop: function( event, ui ) {
                            
                             var already_dragged = $(this).data('dropped_items');
                             
                             if(already_dragged == ""){
                                 
                                 $(this).data('dropped_items',ui.draggable);
                             } else {
                                 
                                 if(already_dragged != ui.draggable)
                               $( already_dragged).animate({ "left": $(already_dragged).data("Left"),"top": $( already_dragged).data("Top")}, "slow",function(){
                                
                            });
                            $(this).data('dropped_items',ui.draggable);
                            
                             }
                             
                                 
                             
                             

                            
                            $(".hideDates").css("display","none");
				$( this ).addClass( "ui-state-highlight" );
                            var item=window.varx.getItem(ui.draggable.attr("id"));
                            var content =item.getContent();
                            $(".minDateList").val("");
                            $(".maxDateList").val("");
                            $(".TextAreas").css("display","block");
                            var maxTemp = [];
                            var minTemp = [];
                            for ( i in content){
                                var temp =content[i]["time_start "];
                                if(temp != null)minTemp.push(temp);
                                temp =content[i]["time_end "];
                                if(temp != null)maxTemp.push(temp);
                            }
                            
                            
                            $(".minDateList").val(minTemp);
                            $(".maxDateList").val(maxTemp);
                            


			}
		}).data('dropped_items',"");
   

    $('#clearButton').live('click',function() {
        fnClearHistory(this)
        });
$('#sabe').live('click',function() {
    mysubmit();});

    $("#section-navigation img[title]").tooltip({
        position: "center right",
        delay: 0
    });
  /*
  $("#tooltipme").tooltip({
        position: "center right",
        delay: 0
   });*/


    var history = $( "#history" );
    
}

function fnInitializeDraggableElements(){
    $( ".draggable" ).draggable({
        opacity:0.7,
        helper:"clone"
    });
    $( ".draggable" ).dblclick(function() {

        var text =  $(this).find("img").attr("src");
        var fields =text.split('/');
        text = fields[fields.length-1];
        var red = 1;
        fnclearDateTexts();
        var result = null;
        switch (text) {
            case 'event.png':

                $(".displayable").css("display","none");
                $("#displayableCatalogue").css("display","block");
                break;
            case 'ics.png':

                $(".displayable").css("display","none");
                $("#displayableICS").css("display","block");
                break;
            case 'ils.png':

                $(".displayable").css("display","none");
                $("#displayableILS").css("display","block");
                break;
            case 'dpas.png':

                $(".displayable").css("display","none");
                $("#displayableDPAS").css("display","block");
                break;
            case 'timerange50.png':

                $(".displayable").css("display","none");
                $("#displayableTime").css("display","block");
                break;
            case 'filter.png':

                $(".displayable").css("display","none");
                $("#displayableFilter").css("display","block");
                break;
            case 'userspace.png':

                $(".displayable").css("display","none");
                $("#displayableUserspace").css("display","block");
                break;
            case 'date50.png':

                $(".displayable").css("display","none");
                $("#displayableDate").css("display","block");
                break;
            default:
                $(".displayable").css("display","none");
                alert("function currently disabled (" +text+")");
                return;


        }


        var imageworks = $(this).find("img").attr("src");
        //imageworks =imageworks.replace(".png","50op.png");
        
         



            var element = new HelioElement(imageworks,"ghost");
            window.varx.addItem(element);
            window.varx.render();
            $(".resCont").remove();
    // imgString =imgString.replace("50.png",".png")
    //             $(imgString).appendTo("#history");
    // var img = $( "<img alt='" + "missing" + "' class='floaters' style='float:left; padding:10px;width:40px;height:40px;' />" ).attr( "src", ui.draggable.find("img").attr("src") ).appendTo( "#history" );

    //$(this).html("<form>First name: <input type='text' "+"name='firstname' /><br />Last name: <input type='text' name='lastname' /></form>  ");




    });




    $( "#droppable-inner" ).droppable({
        accept: ".draggable",

        activeClass: "ui-state-hover",
        hoverClass: "ui-state-active",
        drop: function( event, ui ) {
            var text =  ui.draggable.find("img").attr("src");
            var fields =text.split('/');
            text = fields[fields.length-1];
            fnclearDateTexts();
            
            var red = 1;
            var result = null;
            switch (text) {
                case 'event.png':

                    $(".displayable").css("display","none");
                    $("#displayableCatalogue").css("display","block");
                    break;
                case 'ics.png':

                    $(".displayable").css("display","none");
                    $("#displayableICS").css("display","block");
                    break;
                case 'ils.png':

                    $(".displayable").css("display","none");
                    $("#displayableILS").css("display","block");
                    break;
                case 'dpas.png':

                    $(".displayable").css("display","none");
                    $("#displayableDPAS").css("display","block");
                    break;
                case 'timerange50.png':

                    $(".displayable").css("display","none");
                    $("#displayableTime").css("display","block");
                    break;
                case 'filter.png':

                    $(".displayable").css("display","none");
                    $("#displayableFilter").css("display","block");
                    break;
                case 'userspace.png':

                    $(".displayable").css("display","none");
                    $("#displayableUserspace").css("display","block");
                    break;
                case 'date50.png':

                    $(".displayable").css("display","none");
                    $("#displayableDate").css("display","block");
                    break;
                default:
                    $(".displayable").css("display","none");
                    alert("function currently disabled (" +text+")");
                    return;


            }


            var imageworks = ui.draggable.find("img").attr("src");
            
            
            
            var element = new HelioElement(imageworks,"ghost");
            window.varx.addItem(element);
            window.varx.render();
            $(".resCont").remove();
            
            //alert(x.y); // shows '42'

        // imgString =imgString.replace("50.png",".png")
        //             $(imgString).appendTo("#history");
        // var img = $( "<img alt='" + "missing" + "' class='floaters' style='float:left; padding:10px;width:40px;height:40px;' />" ).attr( "src", ui.draggable.find("img").attr("src") ).appendTo( "#history" );

        //$(this).html("<form>First name: <input type='text' "+"name='firstname' /><br />Last name: <input type='text' name='lastname' /></form>  ");
        }
    });
}

//not in use
function fnGetSelected( oTableLocal )
{
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

function fnAddSelectedRow(pos,aData,oTable){

    
    
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
        div.addClass('resCont')
        div.text(pos);
        div.attr("title",aData);
        div.attr("title2",totalResult);
        $('#testdiv').append(div);
        //$("#testdiv div[title]").tooltip();
    }
    if($('.resCont').length !=0){
        $('#testdiv').css('display','block');
    }else{
        $('#testdiv').css('display','none');
    }
}

function fnOnComplete(){
    
    //fnAppendColumnSelected();
    //fnInitializeDataTable();

    $('#displayableResult').html("");
    $(".displayable").css("display","none");
    fnclearDateTexts();
    
    $('.resultTable').each(function(){
        
        fnFormatTable(this.id);
        
    });
    $('#displayableResult').append($('#tables'));
    
    
    $('#displayableResult').css("display","block");

     var tooltipContent =  $("#previousQuery").text();
     window.varx.solidify(tooltipContent);
     var totalSize = $("#totalSize").val();
     var element = new HelioElement("../images/icons/toolbar/result.png","solid","Amount of entries: "+totalSize);
     window.varx.addItem(element);
     window.varx.render();
    
//fnFormatTable("#example");
$("#responseDivision").html("");


}

function fnClearHistory(){
    window.varx.clear();
    $(".resCont").remove();
    $('.displayable').css("display","none");
    $('.testdiv').html("");
    $('.columnInputs').html("");
    $('#whereField').val("");
    $("#instArea").html($("#instArea").data("content"));
    
    
    

}

function fnFormatTable(tableName){
   
    $("#"+tableName).dataTable({
        "bJQueryUI": true,
        "bAutoWidth": true,
        "bLengthChange": true,
        "sPaginationType": "full_numbers",
        "sScrollX": "100%",
        "iDisplayLength": 25,
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
function fnOnLoading(){
    
    $('.displayable').css("display","none");
    $('#displayableOnLoading').css("display","block");
    window.varx.render();

    
}


$(document).ready(function()
{
    var history = new History();

    window.varx = history;
    //window.history = new History();
    $(".catalogueSelector").change(function(){
        $('.columnInputs').html("");
        $('#whereField').val("");
    });
    fnInitializeDatePicker();
    fnInitializeSingleElements();
    fnInitializeDraggableElements();

$("#saveButton").click(function(){
    var count =0;
    var totalResult = [];
    $(".resCont").each(function(){
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
        
        
        totalResult.count = "Saved elements: " + count;
    
     var element = new HelioElement("../images/icons/toolbar/selectedR.png","result",totalResult);
     window.varx.addItem(element);
     window.varx.render();
     $(".even_selected").each(function(){
         $(this).removeClass("even_selected");
         $(this).addClass("even");
     });
     $(".odd_selected").each(function(){
         $(this).removeClass("odd_selected");
         $(this).addClass("odd");
     });
     $('#testdiv').css("display",'none');
     $(".resCont").remove();
    $('.displayable').css("display","none");
    
    $('.columnInputs').html("");
    $('#whereField').val("");

});

// Create one instance of Person


// Create another instance of Person


//var shelf = new Shelf();

//alert(user.getName()); // My Name Changed
//window.location.replace("http://localhost:8080/ThrirdTry/prototype/explorer");
    
    //$("#selectorTest").closest('div').append("holaa");






    //window.onbeforeunload = function () {
        //alert("are you sure you want to leave my glorious page");
        //location.replace("http://localhost:8080/ThrirdTry/prototype/explorer");
        

   //return "This session is expired and the history altered.";

     //   }

});