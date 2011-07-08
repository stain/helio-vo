


function sendQuery(minDate,maxDate,minTime,maxTime,serviceName,extra){
    /*
       * Initialize the tooltips and reset button of columns
     * Called after onSucess, onError
     */

    //$("#result_overview").css("display","table");
    
    //$("#result_area").append('<img width="300px" heigth="100px" style="margin:0px" src="/helio-portal/images/helio/DLL.gif" />');
    var rowpos = $('#result_area').position();
    if(rowpos!=null){
    

    
   $('html,body').scrollTop(rowpos.top);
   }

    var __onComplete = function(){
        
    };
   

     
    /**
     * Called after successful loading of HEC columns
     * @param data HTML stub containing the loaded columns
     * @param textStatus a status message.
     */
    var __onSuccess = function(data,textStatus) {
        

        
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
        $("#result_overview").css("display","table");
        $("#result_area").html("An error occured with the service selected, we cannont complete your query");
        $("#result_button").remove();
        
        
        
        
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



function saveHistoryBar(){
//alert("save hist");
    var __onComplete = function(){

    };

    var __onSuccess = function(data,textStatus) {
  //      alert(data);
    //    alert(textStatus);
    };

    /**
     * Method called in case an error occurs when loading the HEC table.
     * @param XMLHttpREquest the underlying request
     * @param textStatus status message
     * @param errorThrown error object
     */
    var __onError = function(xmlHttpRequest,textStatus,errorThrown) {
    //alert(xmlHttpRequest);
    //alert(textStatus);
    //alert(errorThrown);


    };

    jQuery.ajax(
    {
        type : 'GET',
         data : {
            "HUID":$.cookie("helioSession"),
            "html":$("#history").html()

        },
        url : 'asyncSaveHistoryBar',
        success: __onSuccess,
        error: __onError,
        complete: __onComplete
    });

    return;
}



function getHistoryBar(){

    var __onComplete = function(){

    };

    var __onSuccess = function(data,textStatus) {
        
        $("#history").html(data);
        window.historyBar.initSaved();
    };

    /**
     * Method called in case an error occurs when loading the HEC table.
     * @param XMLHttpREquest the underlying request
     * @param textStatus status message
     * @param errorThrown error object
     */
    var __onError = function(xmlHttpRequest,textStatus,errorThrown) {
    //alert(xmlHttpRequest);
    //alert(textStatus);
    //alert(errorThrown);


    };

    jQuery.ajax(
    {
        type : 'GET',
         data : {
            "HUID":$.cookie("helioSession")
            

        },
        url : 'asyncGetHistoryBar',
        success: __onSuccess,
        error: __onError,
        complete: __onComplete
    });

    return;
}
