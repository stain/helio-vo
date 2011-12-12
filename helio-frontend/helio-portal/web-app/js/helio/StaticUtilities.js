/**
 * Helper function to enable scrolling slider in the datacart
 *
 * @ui helper selector
 * @event onChangeEvent
 */
function handleSliderChange(event, ui) {
    var maxScroll = $("#historyScrollWidth").attr("scrollWidth") -
    $("#historyScrollWidth").width();
    $("##historyScrollWidth").animate({
        scrollLeft: ui.value *
        (maxScroll / 100)
    }, 1000);
}

/**
 * Helper function to enable scrolling slider in the datacart
 * @ui helper selector
 * @event sliderEvent
 */
function handleSliderSlide(event, ui)
{
    var maxScroll = $("#historyScrollWidth").attr("scrollWidth") -
    $("#historyScrollWidth").width();
    $("#historyScrollWidth").attr({
        scrollLeft: ui.value * (maxScroll / 100)
    });
}

/**
 * Helper function to validate correct date input in date selectors, returns false if wrong date pair
 * called by -->
 * TODO: Check in conjunction with http://app.devzing.com/HELIO/bugzilla/show_bug.cgi?id=7.  
 * @itr index corresponding to the date range pair (maxdate, mindate)
 */
function date_form_validate(itr){
    try{
        var maxDate = $("#maxDate"+itr).val();
        if(maxDate.indexOf("T") == -1){//validates time part on its own and autocompletes if missing
            maxDate = maxDate + "T00:00:00";
            $("#maxDate"+itr).val(maxDate);
        }
        var minDate = $("#minDate"+itr).val();
        if(minDate.indexOf("T") == -1){

            minDate = minDate + "T00:00:00";
            $("#minDate"+itr).val(minDate);

        }
        var IsoDate = new RegExp("^(19|20)\\d\\d[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])T([0-9]{2}):([0-9]{2}):([0-9]{2})$");

        var matches = IsoDate.exec(minDate);
        if(matches === null){

            return false;
        }
        matches = IsoDate.exec(maxDate);
        if(matches === null){
            return false;
        }

        return true;

    }
    catch(err){
        return false;
    }
};

/*
 *Function that creates the dialog for DES
 * TODO: needs to be modeled in a data structure (e.g. JSON)
 */
function createmission(selector){

    var holder= $('<div></div>');

    $("#dialog-message").remove();
    var div =$('<div></div>');
    div.attr('id','dialog-message');
    div.attr('title','Argument Selection');

    var html = window.workspace.getDivisions()["input_datamining"];
    div.append(html);

    $("#testdiv").append(div);

    var _input_disable = function(depth){
        switch(depth){
            case 0:
                $("#input_instrument").attr('disabled', true);
                $("#input_instrument").html("");

                $("#input_measurement").val("");
            case 1:
                $("#input_function").attr('disabled', true);
                $("#input_function").html("");
                $("#input_operator").attr('disabled', true);
            case 2:
                $("#input_argument").attr('disabled', true);
                $("#input_argument").html("");
                $("#input_condition").attr('disabled', true);
                $("#input_condition").val("");
                $("#input_average_time").attr('disabled', true);
                $("#input_average_time").val("");
                $("#input_time_window").attr('disabled', true);
                $("#input_time_window").val("");
                $("#input_expression").attr('disabled', true);
                $("#input_expression").html("");
        }

    };
    $("#input_mission").change(function(){
        _input_disable(0);
        switch($(this).val()){

            case "ACE":
                $("<option></option>").appendTo("#input_instrument");
                $("<option value='ace:swe:all'>SWEPAM</option>").appendTo("#input_instrument");
                $("<option value='ace:imf:all'>MAG</option>").appendTo("#input_instrument");
                $("#input_instrument").removeAttr('disabled');
                break;
            case "WIND":
                $("<option></option>").appendTo("#input_instrument");
                $("<option value='wnd:swe:kp'>SWE</option>").appendTo("#input_instrument");
                $("<option value='wnd:mfi:kp'>MFI</option>").appendTo("#input_instrument");
                $("#input_instrument").removeAttr('disabled');
                break;
            case "ULYSSES":
                $("<option></option>").appendTo("#input_instrument");
                $("<option value='ulys:bai:mom'>SWOOPS</option>").appendTo("#input_instrument");
                $("<option value='b:ulys:mag'>FGM/VHM</option>").appendTo("#input_instrument");

                $("#input_instrument").removeAttr('disabled');
                break;
            case "STA":
                $("<option></option>").appendTo("#input_instrument");
                $("<option value='sta:l2:pla'>PLASTIC</option>").appendTo("#input_instrument");
                $("<option value='sta:mag:mag'>MAG</option>").appendTo("#input_instrument");
                $("#input_instrument").removeAttr('disabled');
                break;
            case "STB":
                $("<option></option>").appendTo("#input_instrument");
                $("<option value='stb:l2:pla'>SWEPAM</option>").appendTo("#input_instrument");
                $("<option value='stb:mag:mag'>MAG</option>").appendTo("#input_instrument");
                $("#input_instrument").removeAttr('disabled');
                break;
            default:
        }
    });
    $("#input_instrument").change(function(){
        _input_disable(1);

        switch($(this).val()){
            case "ace:swe:all":
                $("<option></option>").appendTo("#input_function");
                $("<option value='DERIV'>Parameter Derivative</option>").appendTo("#input_function");

                $("<option value='VAR'>Parameter Variance in Sliding Window</option>").appendTo("#input_function");
                $("<option value='VALUE'>Parameter Value</option>").appendTo("#input_function");
                $("#input_function").removeAttr('disabled');
                $("#input_measurement").val("thermal plasma");
                break;
            case "ace:imf:all":
                $("<option></option>").appendTo("#input_function");
                $("<option value='DERIV'>Parameter Derivative</option>").appendTo("#input_function");
                $("<option value='SIGN'>Parameter Sign Change</option>").appendTo("#input_function");
                $("<option value='VAR'>Parameter Variance in Sliding Window</option>").appendTo("#input_function");
                $("<option value='VALUE'>Parameter Value</option>").appendTo("#input_function");
                $("#input_function").removeAttr('disabled');
                $("#input_measurement").val("magnetic field");

                break;


            case "wnd:swe:kp":
                $("<option></option>").appendTo("#input_function");
                $("<option value='DERIV'>Parameter Derivative</option>").appendTo("#input_function");

                $("<option value='VAR'>Parameter Variance in Sliding Window</option>").appendTo("#input_function");
                $("<option value='VALUE'>Parameter Value</option>").appendTo("#input_function");
                $("#input_function").removeAttr('disabled');
                $("#input_measurement").val("thermal plasma");
                break;
            case "wnd:mfi:kp":
                $("<option></option>").appendTo("#input_function");
                $("<option value='DERIV'>Parameter Derivative</option>").appendTo("#input_function");
                $("<option value='SIGN'>Parameter Sign Change</option>").appendTo("#input_function");
                $("<option value='VAR'>Parameter Variance in Sliding Window</option>").appendTo("#input_function");
                $("<option value='VALUE'>Parameter Value</option>").appendTo("#input_function");
                $("#input_function").removeAttr('disabled');
                $("#input_measurement").val("magnetic field");
                break;

            case 'ulys:bai:mom':
                $("<option></option>").appendTo("#input_function");
                $("<option value='DERIV'>Parameter Derivative</option>").appendTo("#input_function");

                $("<option value='VAR'>Parameter Variance in Sliding Window</option>").appendTo("#input_function");
                $("<option value='VALUE'>Parameter Value</option>").appendTo("#input_function");
                $("#input_function").removeAttr('disabled');
                $("#input_measurement").val("thermal plasma");
                break;
            case'b:ulys:mag':
                $("<option></option>").appendTo("#input_function");
                $("<option value='DERIV'>Parameter Derivative</option>").appendTo("#input_function");
                $("<option value='SIGN'>Parameter Sign Change</option>").appendTo("#input_function");
                $("<option value='VAR'>Parameter Variance in Sliding Window</option>").appendTo("#input_function");
                $("<option value='VALUE'>Parameter Value</option>").appendTo("#input_function");
                $("#input_function").removeAttr('disabled');
                $("#input_measurement").val("magnetic field");
                break;
            case'sta:l2:pla':
                $("<option></option>").appendTo("#input_function");
                $("<option value='DERIV'>Parameter Derivative</option>").appendTo("#input_function");

                $("<option value='VAR'>Parameter Variance in Sliding Window</option>").appendTo("#input_function");
                $("<option value='VALUE'>Parameter Value</option>").appendTo("#input_function");
                $("#input_function").removeAttr('disabled');
                $("#input_measurement").val("thermal plasma");
                break;
            case'sta:mag:mag':
                $("<option></option>").appendTo("#input_function");
                $("<option value='DERIV'>Parameter Derivative</option>").appendTo("#input_function");
                $("<option value='SIGN'>Parameter Sign Change</option>").appendTo("#input_function");
                $("<option value='VAR'>Parameter Variance in Sliding Window</option>").appendTo("#input_function");
                $("<option value='VALUE'>Parameter Value</option>").appendTo("#input_function");
                $("#input_function").removeAttr('disabled');
                $("#input_measurement").val("magnetic field");
                break;
            case'stb:l2:pla':
                $("<option></option>").appendTo("#input_function");
                $("<option value='DERIV'>Parameter Derivative</option>").appendTo("#input_function");

                $("<option value='VAR'>Parameter Variance in Sliding Window</option>").appendTo("#input_function");
                $("<option value='VALUE'>Parameter Value</option>").appendTo("#input_function");
                $("#input_function").removeAttr('disabled');
                $("#input_measurement").val("thermal plasma");
                break;
            case'stb:mag:mag':
                $("<option></option>").appendTo("#input_function");
                $("<option value='DERIV'>Parameter Derivative</option>").appendTo("#input_function");
                $("<option value='SIGN'>Parameter Sign Change</option>").appendTo("#input_function");
                $("<option value='VAR'>Parameter Variance in Sliding Window</option>").appendTo("#input_function");
                $("<option value='VALUE'>Parameter Value</option>").appendTo("#input_function");
                $("#input_function").removeAttr('disabled');
                $("#input_measurement").val("magnetic field");
                break;

            default:
                break;
        }
    });
    
    $("#input_function").change(function(){
        _input_disable(2);
        $("#input_operator").removeAttr("disabled");
        switch($(this).val()){

            case "DERIV":
                $("<option></option>").appendTo("#input_argument");
                $("<option value='V'>velocity_magnitude</option>").appendTo("#input_argument");
                $("<option value='N'>ion_density</option>").appendTo("#input_argument");
                $("<option value='B'>magnetic_field_magnitude</option>").appendTo("#input_argument");
                $("<option value='BX'>magnetic_field_x_component</option>").appendTo("#input_argument");
                $("<option value='BY'>magnetic_field_y_component</option>").appendTo("#input_argument");
                $("<option value='BZ'>magnetic_field_z_component</option>").appendTo("#input_argument");
                $("#input_argument").removeAttr('disabled');
                $("#input_condition").removeAttr('disabled');
                $("#input_average_time").removeAttr('disabled');


                break;
            case "SIGN":
                $("<option></option>").appendTo("#input_argument");

                $("<option value='BX'>magnetic_field_x_component</option>").appendTo("#input_argument");
                $("<option value='BY'>magnetic_field_y_component</option>").appendTo("#input_argument");
                $("<option value='BZ'>magnetic_field_z_component</option>").appendTo("#input_argument");
                $("#input_argument").removeAttr('disabled');
                $("#input_average_time").removeAttr('disabled');
                break;
            case "VAR":
                $("<option></option>").appendTo("#input_argument");
                $("<option value='V'>velocity_magnitude(km/s)</option>").appendTo("#input_argument");
                $("<option value='N'>ion_density</option>").appendTo("#input_argument");

                $("<option value='BX'>magnetic_field_x_component</option>").appendTo("#input_argument");
                $("<option value='BY'>magnetic_field_y_component</option>").appendTo("#input_argument");
                $("<option value='BZ'>magnetic_field_z_component</option>").appendTo("#input_argument");
                $("#input_argument").removeAttr('disabled');
                $("#input_condition").removeAttr('disabled');
                $("#input_time_window").removeAttr('disabled');
                $("#input_average_time").removeAttr('disabled');

                break;
            case "VALUE":
                $("<option></option>").appendTo("#input_argument");
                $("<option value='V'>velocity_magnitude</option>").appendTo("#input_argument");
                $("<option value='N'>ion_density</option>").appendTo("#input_argument");
                $("<option value='B'>magnetic_field_magnitude</option>").appendTo("#input_argument");
                $("<option value='BX'>magnetic_field_x_component</option>").appendTo("#input_argument");
                $("<option value='BY'>magnetic_field_y_component</option>").appendTo("#input_argument");
                $("<option value='BZ'>magnetic_field_z_component</option>").appendTo("#input_argument");
                $("#input_argument").removeAttr('disabled');
                $("#input_condition").removeAttr('disabled');
                $("#input_average_time").removeAttr('disabled');

                break;

            default:
                break;
        }
    });

    $(":input").change(function(){
        var des_function = $("#input_function").val();
        var mission = $("#input_mission").val();
        var argument = $("#input_argument").val();
        var condition = $("#input_condition").val();
        var operator = $("#input_operator").val();

        if(operator == ">"){
            condition = "/"+condition;
        }else if(operator == "<"){
            condition = condition+"/";
        }
        switch(des_function){
            case "DERIV":
                $("#input_expression").html( des_function+","+mission+":"+argument+":"+condition+":"+$("#input_average_time").val());
                break;
            case "SIGN":
                $("#input_expression").html( des_function+","+mission+":"+argument+":"+$("#input_average_time").val());
                break;
            case "VAR":
                $("#input_expression").html( des_function+","+mission+":"+argument+":"+condition+":"+$("#input_average_time").val()+":"+$("#input_time_window").val());

                break;
            case "VALUE":
                $("#input_expression").html( des_function+","+mission+":"+argument+":"+condition+":"+$("#input_average_time").val());
                break;
            default:
                $("#input_expression").html("");
                break;
        }

    });
    _input_disable(0);
    if($("#block_area input[name='function']").val() != null){

        var funct =$("#block_area input[name='function']").val();
        $("#input_mission").removeAttr('disabled');
        $("#input_instrument").removeAttr('disabled');
        $("#input_function").removeAttr('disabled');
        $("#input_operator").removeAttr('disabled');

        $("#input_argument").removeAttr('disabled');

        switch (funct){
            case "SIGN":

                $("#input_argument").removeAttr('disabled');
                $("#input_average_time").removeAttr('disabled');
                break;

            case "VAR":

                $("#input_argument").removeAttr('disabled');
                $("#input_condition").removeAttr('disabled');
                $("#input_time_window").removeAttr('disabled');
                $("#input_average_time").removeAttr('disabled');
                break;
            default:
                $("#input_argument").removeAttr('disabled');
                $("#input_condition").removeAttr('disabled');
                $("#input_average_time").removeAttr('disabled');

        }

        $("#input_mission").html($("#block_area input[name='mission']").val());
        $("#input_mission").val($("#block_area input[name='mission']").attr('selection'));

        $("#input_instrument").html($("#block_area input[name='instrument']").val());
        $("#input_instrument").val($("#block_area input[name='instrument']").attr('selection'));

        $("#input_measurement").html($("#block_area input[name='measurement']").val());
        $("#input_measurement").val($("#block_area input[name='measurement']").attr('selection'));
        $("#input_function").html($("#block_area input[name='function']").val());
        $("#input_function").val($("#block_area input[name='function']").attr('selection'));
        $("#input_argument").html($("#block_area input[name='argument']").val());
        $("#input_argument").val($("#block_area input[name='argument']").attr('selection'));

        $("#input_condition").html($("#block_area input[name='condition']").val());
        $("#input_condition").val($("#block_area input[name='condition']").attr('selection'));
        $("#input_operator").html($("#block_area input[name='operator']").val());
        $("#input_operator").val($("#block_area input[name='operator']").attr('selection'));

        $("#input_average_time").html($("#block_area input[name='averagetime']").val());
        $("#input_average_time").val($("#block_area input[name='averagetime']").attr('selection'));
        $("#input_time_window").html($("#block_area input[name='timewindow']").val());
        $("#input_time_window").val($("#block_area input[name='timewindow']").attr('selection'));
        $("#input_expression").html($("#block_area input[name='expression']").val());
        $("#input_expression").val($("#block_area input[name='expression']").attr('selection'));

    }

    formatButton($(".custom_button"));
    $('#dialog-message').dialog({
        modal: true,
        height:630,
        width:810,
        close: function(){

            $("#dialog-message").remove();
        },
        buttons: {
            // AddBlock: function(){

            //div.append("<div style='width:100%;border-bottom:1px solid black'><center> <label>Logical Operator:</lablel><select><option>AND</option><option>OR</option></select></center>");
            //div.append(html);

            //},
            Help: function(){
                $('#help_overlay h3').text("Argument Selection Form");
                $('#help_overlay p').text("Follow the simple steps described in the form, Some boxes might be unselectable if you attempt to skips steps. Don't be afraid to experiment, you can always come back if the query fails to return data.");
                $('#help_overlay').attr('title','Click to unblock').click($.unblockUI);
                $.blockUI({
                    message: $('#help_overlay')
                });
                $('.blockOverlay').attr('title','Click to unblock').click($.unblockUI);
            },
            Cancel: function() {
                $("#dialog-message").dialog( "close" );
            },
            Ok: function() {
                $("#block_area").html('');

                var des_function = $("#input_function");
                var mission = $("#input_mission");
                var argument = $("#input_argument");
                var instrument = $("#input_instrument");
                var condition = $("#input_condition");
                var operator = $("#input_operator");
                var expression = $("#input_expression");
                var averagetime = $("#input_average_time");
                var timewindow = $("#input_time_window");
                var measurement = $("#input_measurement");

                if(expression.html() == ""){
                    alert("Form cannot be empty.");
                    return;
                }
                $("#block_area").append($("#input_expression").html());
                $("#block_area").append("<input type='hidden' name='extra' value='"+$("#input_mission").val()+"'>");
                $("#block_area").append("<input type='hidden' name='where' value='"+$("#input_expression").html()+"'>");
                $("#block_area").append("<input type='hidden' name='function' selection='"+des_function.val()+"'' value='"+des_function.html()+"'>");
                $("#block_area").append("<input type='hidden' name='mission' selection='"+mission.val()+"'' value='"+mission.html()+"'>");
                $("#block_area").append("<input type='hidden' name='instrument' selection='"+instrument.val()+"'' value='"+instrument.html()+"'>");
                $("#block_area").append("<input type='hidden' name='argument' selection='"+argument.val()+"'' value='"+argument.html()+"'>");
                $("#block_area").append("<input type='hidden' name='condition' selection='"+condition.val()+"'' value='"+condition.html()+"'>");
                $("#block_area").append("<input type='hidden' name='operator' selection='"+operator.val()+"'' value='"+operator.html()+"'>");
                $("#block_area").append("<input type='hidden' name='averagetime' selection='"+averagetime.val()+"'' value='"+averagetime.html()+"'>");
                $("#block_area").append("<input type='hidden' name='timewindow' selection='"+timewindow.val()+"'' value='"+timewindow.html()+"'>");
                $("#block_area").append("<input type='hidden' name='measurement' selection='"+measurement.val()+"'' value='"+measurement.html()+"'>");
                $("#block_area").append("<input type='hidden' name='expression' selection='"+expression.val()+"'' value='"+expression.html()+"'>");


                $("#block_drop").attr('src','../images/helio/circle_block.png');
                $("#block_drop").addClass('drop_able');

                $("#dialog-message").dialog( "close" );
                $("#dialog-message").remove();
                window.workspace.evaluator();
            }
        }
    });
}

/**
 * Helper function to debug objects easier, needs firebug enabled, returns the object in its jquery wrapper
 * @name id of object
 */
function pr(name){
    console.debug($("#"+name).val());
    return $("#"+name);
}

/**
 * Helper function to make sure buttons are enabled only once.
 * @selector jquery object to be transformed to button
 */
function formatButton(selector){
    selector.each(function(){
        if(!$(this).hasClass('ui-button')){
            $(this).button();
        }
    });
}

/**
 * callback method that is used to keep track of whats been selected in a dataTable
 * contents are kept in an invisible division called #testdiv and the elements added are .resCont
 * they have attributes that keept track of the row selected and header corresponding to that row
 */
function fnAddSelectedRow(pos,aData,oTable){
    //if (typeof console!="undefined")console.info("fnAddSelectedRow");

    var totalResult =[];
    var headers =oTable.fnSettings().aoColumns;
    for (i in headers){
        totalResult.push( headers[i].sTitle);
    }

    var flag = true;
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
        // TODO: remove this, its obsolete, is it?
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

/**
 * Formats every datatable in the system and adds listeners to the rows to be clicked
 * @tableName: takes in the id of the datatable to be parsed, data table should have headers set and body set with matching number of elements
 */
function fnFormatTable(tableName){
//    if (typeof console!="undefined")console.info("fnFormatTable");

    var dataTable =$("#"+tableName).dataTable({
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
                if(aData[aData.length-1] =="T"){
                    $(nRow).attr("class","item_found "+$(nRow).attr('class'));
                }else{
                    $(nRow).attr("class","item_missing "+$(nRow).attr('class'));
                }
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
                if ( aData[dataIndex] == 1 ) {
                    aData[dataIndex] = 0;
                }
                else {
                    aData[dataIndex] = 1;
                }

                this.className = (aData[dataIndex] == 1) ?
                this.className+'_selected' :
                this.className.replace( /_selected/, "" );
            } );

            return nRow;
        }
    });
    return dataTable;
}

/**
 * Creates a popup for the help section
 * @params url,windowname,w,h,x,y pretty self explanatory just set the initial size and position of the new window
 */
function showHelp(url,windowname,w,h,x,y){
    if (typeof console!="undefined")console.info("showHelp");
    var mywin = window.open(url,windowname,"resizable=no,toolbar=no,scrollbars=yes,menubar=no,status=no,directories=no,width="+w+",height="+h+",left="+x+",top="+y+"");
    mywin.focus();
}