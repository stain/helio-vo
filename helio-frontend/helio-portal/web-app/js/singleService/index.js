/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
function resetAll(){
    //$('#dateRangeHistory').html("");
      $('#serviceHistory').html("");
    $('#tableHistory').html("");
    $('#columnHistory').html("");
     $("#columnBlock :text").val('');
        $("#tableBlock :checked" ).removeAttr('checked');
        $("#serviceBlock :radio" ).removeAttr('checked');
        $("#tableCurrentSelection ul").html("");
        $("#tableCurrentSelection").css('display','none');
}
function refreshHistory(){
    //$('#dateRangeHistory')
    //console.log("refreshHistory Called");
    var rh_minDate = $('#minDate').val();
    var rh_maxDate = $('#maxDate').val();
    var rh_minTime = $('#minTime').val();
    var rh_maxTime = $('#maxTime').val();
    $('#dateRangeHistory').html("<strong>Selected start date</strong>: "+rh_minDate+" "+rh_minTime+" <strong>Selected end date</strong>:"+rh_maxDate+" "+rh_maxTime);
    var startRange=[];
    startRange.lenght=10;
    var endRange=[];
    endRange.lenght=10;
     $('#tableCurrentSelection').find(('input[name|="startDate"]')).each(function(i){
          startRange[i]=$(this).val();
          
         
     });
     $('#tableCurrentSelection').find(('input[name|="endDate"]')).each(function(i){
          endRange[i]=$(this).val();
          

     });
     if(startRange.length == 0){
        $("#dateRangeHistory").css('display','block');
        $("#dateRangesHistory").css('display','none');
     }else{
         $("#dateRangeHistory").css('display','none');
         $("#dateRangesHistory").css('display','block');

         
     }
     $("#dateRangesHistory ul").html("");
     for (var i = 0; i < startRange.length; i++){
         $("#dateRangesHistory ul").append("<li> start: "+startRange[i]+" end: "+endRange[i]+"</li>");
      }
     //console.log(endRange);
     //console.log(startRange);


    $('#serviceBlock :radio').each(function(){
        if($(this).is(':checked')){
             $('#serviceHistory').html("<strong>Selected service</strong>: "+$(this).val());
        
        }});
      var from = "";
     $("#tableBlock :checked").each(function(){
        if($(this).is(':checked')){
        from = from+$(this).val()+",";
        }
     });
     from = from.substring(0,from.length-1);
     if(from!="")$('#tableHistory').html("<strong>Selected Tables</strong>: "+from);
     else $('#tableHistory').html("");
     var rh_where= $('#wherePQL').val();
     if(rh_where != "") $('#columnHistory').html("<strong>Selected column values</strong>: "+$('#wherePQL').val());
     else $('#columnHistory').html("");



    
}

function mysubmit(){
    $("#whereField").val("");

    $(".columnSelection").each(function(i){
        if($(this).val() == ""){
            $(this).attr('name','');
        }else{
            
            var columnText = $(this).parent().text();
            var value = $(this).val();
            var id = $(this).parent().parent().parent().attr('id');
            //alert(columnText);
            //alert(value);
            //alert(id);
            if($("#whereField").val()!=""){
                var prevVal = $("#whereField").val();
                $("#whereField").val(prevVal+";"+id+"."+columnText.trim()+","+value);
            }else{
                $("#whereField").val(id+"."+columnText.trim()+","+value);
            }
            
            
            $(this).attr('name','');

        }
        return true;
    });
//$('#pqlQuery').append($("#minDate").text());
}
$(function() {
    resetAll();
    refreshHistory();
    $("#fromPQL").val("");
    $("#wherePQL").val("");
    var dates = $('#minDate, #maxDate').datepicker({
        defaultDate: "+1w",
        changeMonth: true,
        dateFormat: 'yy-mm-dd',
        yearRange: '1970:2011',
        changeYear: true,
        numberOfMonths: 1,
        onSelect: function(selectedDate) {
            
            refreshHistory();
            
            var option = this.id == "minDate" ? "minDate" : "maxDate";
            var instance = $(this).data("datepicker");
            var date = $.datepicker.parseDate(instance.settings.dateFormat || $.datepicker._defaults.dateFormat, selectedDate, instance.settings);
            dates.not(this).datepicker("option", option, date);
        }
    });




    $(function() {
                $(".selectableTableBlock").attr('checked',false);
                $(".columnReset").css('display','none');
                
                $("#columnBlock").find("input:text").attr('value','');
                $(".tableReset").css('display','none');
                $("#serviceBlock").find("input:radio").attr('checked',false);
        
        $( ".reset" ).click(function() {
            

        if($(this).attr('name') == 'resetTable'){
                $(".selectableTableBlock").attr('checked',false);
                $(".columnReset").css('display','none');
                
                $("#columnBlock").find("input:text").attr('value','');

            }else if($(this).attr('name') == 'resetColumn'){

                $("#columnBlock").find("input:text").attr('value','');


            }else if($(this).attr('name') == 'resetService'){
                $(".selectableTableBlock").attr('checked',false);
                $(".columnReset").css('display','none');
                
                $("#columnBlock").find("input:text").attr('value','');
                $(".tableReset").css('display','none');
                $("#serviceBlock").find("input:radio").attr('checked',false);

            }

            
          
            
        });
    });




    $("#minTime").AnyTime_picker(
    {
        format: "%H:%i",
        labelTitle: "Hour",
        labelHour: "Hour",
        labelMinute: "Minute"
    } );


 $("#maxTime").change(function(e){
    refreshHistory();
 });
    $("#maxTime").AnyTime_picker(
    {
        format: "%H:%i",
        labelTitle: "Hour",
        labelHour: "Hour",
        labelMinute: "Minute"
    } );

    $('.selectableTableBlock').click(function() {
        
        $('#columnBlock').css('display','block');

          if($(this).not(':checked'))  {
              $('#columnBlock').find("#"+$(this).attr('value')).css('display','none');
                //$('#selectedListTable').find("<li>").text($(this).attr('value')).remove();
              //$('#selectedListTable').find("#"+$(this).attr('value')).remove();
             
        }
        if($(this).is(':checked'))  {
            //$(this).parent("#tableBlock").find('selectedListTable').appendTo();
            
            //$(this).attr('value').appendTo('#selectedListTable');
            //$('#selectedListTable').append("<li id='"+$(this).attr('value')+"'>"+$(this).attr('value')+"</li>");
            $('#columnBlock').find("#"+$(this).attr('value')).css('display','block');

        }
          $("#columnBlock :text").val('');
        
        $('#wherePQL').val('');
        refreshHistory();
    });
       



$(".columnSelection").keyup(function(){
    
    $("#wherePQL").val("");
    $(".columnSelection").each(function(i){
        if($(this).val() == ""){
            
        }else{
            
            var columnText = $(this).parent().text();
            var value = $(this).val();
            var id = $(this).parent().parent().parent().attr('id');
            //alert(columnText);
            //alert(value);
            //alert(id);
            if($("#wherePQL").val()!=""){
                var prevVal = $("#wherePQL").val();
                $("#wherePQL").val(prevVal+";"+id+"."+columnText.trim()+","+value);
            }else{
                $("#wherePQL").val(id+"."+columnText.trim()+","+value);
            }


            
        }})
    refreshHistory();
        
});

  

    $(':radio').click(function() {


        $(".tableReset").css('display','none');
        if($(this).attr('id')=="hecRadio")  {
        
            $("#hecTableDiv").css('display','block');
            
        }
        if($(this).attr('id')=="icsRadio")  {

            $("#icsTableDiv").css('display','block');
        }
        if($(this).attr('id')=="ilsRadio")  {

            $("#ilsTableDiv").css('display','block');
        }
        if($(this).attr('id')=="dpasRadio")  {

            $("#dpasTableDiv").css('display','block');
        }
        $("#columnBlock :text").val('');
        $("#tableBlock :checked" ).removeAttr('checked');
        $('#wherePQL').val('');
refreshHistory();

//$( "#tabs" ).tabs({selected: 2});

       
        //var imgString =$( "<img alt='" + "hola" + "' class='floaters' style='float:left; padding:10px;' />" ).attr( "src", imageworks).appendTo("#history");

     
        //var imgString =$( "waraaaaap" ).appendTo("#tableBlock");
        //$("#tableBlock").html('<p>open audition');
        //$("#columnBlock").css('display','block');
        // $(this).parents("#serviceBlock").find(":radio").attr('checked', false);

       
    });



});