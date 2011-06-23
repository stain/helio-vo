

function sendQuery(minDate,maxDate,minTime,maxTime,serviceName,extra){
    /*
       * Initialize the tooltips and reset button of columns
     * Called after onSucess, onError
     */
    var __onComplete = function(){
        
    };
   

     
    /**
     * Called after successful loading of HEC columns
     * @param data HTML stub containing the loaded columns
     * @param textStatus a status message.
     */
    var __onSuccess = function(data,textStatus) {

        $("#responseDivision").html(data);
        $("#displayableResult").html("");
          $("#ics_instrument").css("display","block");
        $("#ils_trajectories").css("display","block");
        $("#voTables").prepend($("#ics_instrument").clone());
        $("#voTables").prepend($("#ils_trajectories").clone());
        
        $("#ics_instrument").css("display","none");
        $("#ils_trajectories").css("display","none");

        $(":checkbox").unbind();
        $(":checkbox").removeAttr("checked");

        $(":checkbox").change(function(){

            var checkboxName = $(this).attr("name");
            var checkboxColumn = $(this).attr("column");



            if($(this).is(':checked')){

                $("#resultTable0").dataTable().fnFilter(checkboxName,checkboxColumn);
            }else{
                //console.debug("not checked");
                $("#resultTable0").dataTable().fnFilter("",checkboxColumn);
            }



        });

        $('#displayableResult').append($('#tables'));
        $('#displayableResult').css("display","block");
        
        $('.resultTable').each(function(){
            fnFormatTable(this.id);
        });

        $("#dialog-message").dialog( "close" );
                                    $("#dialog-message").remove();
        
    };

    /**
     * Method called in case an error occurs when loading the HEC table.
     * @param XMLHttpREquest the underlying request
     * @param textStatus status message
     * @param errorThrown error object
     */
    var __onError = function(xmlHttpRequest,textStatus,errorThrown) {
        
    };

    jQuery.ajax(
    {
        type : 'GET',
        data : {
            "minDate":minDate.join(","),
            "maxDate":maxDate.join(","),
            "minTime":minTime.join(","),
            "maxTime":maxTime.join(","),
            "serviceName":serviceName,
            "extra":extra.join(",")

        },
        url : 'asyncQuery',
        success: __onSuccess,
        error: __onError,
        complete: __onComplete
    });

    return;
}

