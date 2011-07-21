function TimeForm(){
  
 
 
    var _unserialize = function(){

     
    };//end unserialized

    return {
        // Public methods
     
        display: function(dateArray) {

            $("#dialog-message").remove();
            var div =$('<div></div>');
            div.attr('id','dialog-message');
            div.attr('title','Date Selection');
            var html = window.workspace.getDivisions()["input_time"];
            div.append(html);
            $("#testdiv").append(div);
            var date_range_list = $("#input_time_range_list");
            date_range_list.html("");
            
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

                    $(this).closest('tr').remove();

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
            });
        }//end render

    };//end public methods

}//end class
