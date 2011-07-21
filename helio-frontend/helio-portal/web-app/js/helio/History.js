function History() {


    var array=[];
    var limit = 13;
    var offset =0;
    var filter = "all";
    var current = 0;

    return {
        instrument_input_form: function(selector){

            var holder= $('<div></div>');
            holder.html(selector.attr('inst_data'));
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
                        selector.attr('inst_data',$("#extra_list_form").html());
                        selector.data('inst_data',$("#extra_list_form").html());
                        saveHistoryBar();

                        $("#dialog-message").dialog( "close" );
                        $("#dialog-message").remove();

                    }
                }
            });
        },
        event_input_form: function(selector){
            var holder= $('<div></div>');
            holder.html(selector.attr('event_data'));

            $("#dialog-message").remove();
            var div =$('<div></div>');
            div.attr('id','dialog-message');
            div.attr('title','Event Parameter');

            var html = window.workspace.getDivisions()["input_event"];

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


                        selector.attr('event_data',$("#extra_list_form").html());
                        selector.data('event_data',$("#extra_list_form").html());

                        
                        saveHistoryBar();

                        $("#dialog-message").dialog( "close" );
                        $("#dialog-message").remove();

                    }
                }
            });
        },
        time_input_form: function(selector,edit){
            var holder= $('<div></div>');
            holder.html(selector.data('time_data'));


            $("#dialog-message").remove();
            var div =$('<div></div>');
            div.attr('id','dialog-message');
            div.attr('title','Date Edition');
            var html = window.workspace.getDivisions()["input_time"];
            div.append(html);
            $("#testdiv").append(div);
            var date_range_list = $("#input_time_range_list");

            date_range_list.html("");
            //var num =date_range_list.data("ranges");
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
                
                
                td = $('<td><div class="input_time_range_remove">Delete</div></td>');
                tr.append(td);
                date_range_list.append(tr);
                _formatDateRange(num);
                $(".input_time_range_remove").unbind();
                $(".input_time_range_remove").button();
                $(".input_time_range_remove").click(function(){

                   if($("#input_time_range_list tr").length ==2){
                        $(this).closest('tr').remove();
                        $(".input_time_range_remove").button({'disabled':true});
                    }else if($("#input_time_range_list tr").length >2){
                        $(this).closest('tr').remove();
                    }

                });
            }


            var iterator =0;

            holder.find('tr').each(function(){
                
                iterator++;
                holder.data("ranges",iterator);
                _createDateRange(iterator);
                $(this).find("input").each(function(){
                    
                    if($(this).attr("name")=="maxDate")$("#maxDate"+iterator).val($(this).val());
                    if($(this).attr("name")=="minDate")$("#minDate"+iterator).val($(this).val());
                    if($(this).attr("name")=="minTime")$("#minTime"+iterator).val($(this).val());
                    if($(this).attr("name")=="maxTime")$("#maxTime"+iterator).val($(this).val());
                });




            });
            if(iterator == 0){
                holder.data("ranges",1);
                _createDateRange(1);
                 $(".input_time_range_remove").button({'disabled':true});
            }
            if(iterator == 1){
                $(".input_time_range_remove").button({'disabled':true});
            }
            



            $("#input_time_range_button").click(function(){
                var num =holder.data("ranges");
                holder.data("ranges",num+1);
                _createDateRange(num+1);
                $(".input_time_range_remove").button({'disabled':false});
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
                close: function(){
                    
                    $("#dialog-message").remove();
                },
                buttons: {
                    Ok: function() {
                        var div = $("<div></div>");

                        var table =$("<table></table>");
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
                        div.append(table);
                        selector.attr('time_data',div.html());
                        selector.data('time_data',div.html());
                        if(edit == false){
                            var div = $("<div  title='"+"noTitle"+"' class='floaters'></div>");
                            var table2 =$('<table border="0" cellpadding="0" cellspacing="0"></table>');
                            var tr2 =$("<tr></tr>");
                            var td2 =$("<td></td>");
                            console.debug(selector);
                            td2.append(selector);
                            td2.append($("<div  style='margin-left:10px;margin-top:10px;;float:right' class='closeme ui-state-default ui-corner-all'><span class='ui-icon ui-icon-close'></span></div>"));
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
                        }
                        saveHistoryBar();






                        $("#dialog-message").dialog( "close" );
                        $("#dialog-message").remove();




                    },
                    Cancel: function() {
                        
                        $("#dialog-message").dialog( "close" );
                        $("#dialog-message").remove();

                    }
                }
            })
        },

        result_input_form: function(selector){
            var holder= $('<div></div>');
            holder.html(selector.attr('result_data'));

            $("#dialog-message").remove();
            var div =$('<div></div>');
            div.attr('id','dialog-message');
            div.attr('title','Result Edition');
            var html = window.workspace.getDivisions()["input_result"];
            div.append(html);
            $("#testdiv").append(div);

            $("#input_result_area ul").append("<li>Result Source:</li>");




            $(".custom_button").button();

            $('#dialog-message').dialog({
                modal: true,
                height:330,
                width:700,
                buttons: {


                    Ok: function() {

                        //selector.attr('time_data',table.html());
                        //selector.data('time_data',table.html());
                        //saveHistoryBar();
                        $("#dialog-message").dialog( "close" );
                        $("#dialog-message").remove();

                    },
                    Display: function(){

                        window.workspace.createItem(selector.attr('helio_task'));

                        $("#instruments_drop").attr('src','../images/helio/circle_inst.png');
                        $("#event_drop").attr('src','../images/helio/circle_event.png');
                        $("#time_drop").attr('src','../images/helio/circle_time.png');
                        $('#time_area').html(selector.attr('time_data'));
                        $('#extra_list').html(selector.attr('event_data'));
                        getSavedResult(selector.attr('helio_resultId'));
                        if(selector.attr('inst_data')!= null)$('#extra_list').html(selector.attr('inst_data'));
                        $("#dialog-message").dialog( "close" );
                        $("#dialog-message").remove();

                    }
                }
            })
        },
        clear: function(){
            if (typeof console!="undefined")console.info("History :: clear");
            array = [];
            this.render();
        },
        init: function(){
            if (typeof console!="undefined")console.info("History :: init");

            $( "#history" ).droppable({
                accept: ".drop_able",
                activeClass: "ui-state-hover",
                hoverClass: "ui-state-active",
                drop: function( event, ui ) {



                    var testver =ui.draggable.attr('src');
                    var title ="";
                    var div = $("<div  title='"+"noTitle"+"' class='floaters'></div>");
                    var table =$('<table border="0" cellpadding="0" cellspacing="0"></table>');
                    var tr =$("<tr></tr>");
                    var td =$("<td></td>");
                    var img =   $( "<img class='history_draggable' alt='"+"image missing"+"'/>" ).attr( "src",ui.draggable.attr('src') );
                    if(testver.indexOf('time') != -1){
                        img.data('time_data',$("#time_area").html());
                        img.attr('time_data',$("#time_area").html());
                        img.attr('helio_type','time');
                    }
                    if(testver.indexOf('event')!= -1){
                        img.data('event_data',$("#extra_list").html());
                        img.attr('event_data',$("#extra_list").html());
                        img.attr('helio_type','event');
                    }
                    if(testver.indexOf('inst')!= -1){
                        img.data('inst_data',$("#extra_list").html());
                        img.attr('inst_data',$("#extra_list").html());
                        img.attr('helio_type','inst');
                    }
                    if(testver.indexOf('result')!= -1){
                        img.attr('helio_task',$("#task_name").val());
                        img.attr('helio_resultId',$("#resultId").val());
                        img.attr('event_data',$("#extra_list").html());
                        img.attr('inst_data',$("#extra_list").html());
                        img.attr('time_data',$("#time_area").html());
                        img.attr('helio_type','result');
                    }
                    td.append(img);
                    td.append($("<div  style='margin-left:10px;margin-top:10px;;float:right' class='closeme ui-state-default ui-corner-all'><span class='ui-icon ui-icon-close'></span></div>"));

                    img.draggable({
                        revert: "invalid",
                        helper:"clone",
                        zIndex: 1700
                    });

                    img.click(function(){
                        if($(this).attr('helio_type')== 'time'){
                            window.historyBar.time_input_form($(this),true);
                        }
                        if($(this).attr('helio_type')== 'event'){
                            window.historyBar.event_input_form($(this));
                        }
                        if($(this).attr('helio_type')== 'inst'){
                            window.historyBar.instrument_input_form($(this));
                        }
                        if($(this).attr('helio_type')== 'result'){
                            window.historyBar.result_input_form($(this));
                        }
                    });



                    tr.append(td);


                    table.append(tr);
                    //tr =$('<tr class="inner_label"><td>'+label+'</td><tr>')
                    //table.append(tr);

                    div.append(table);


                    $("#historyContent").append(div);


                    $(".closeme").unbind();
                    $(".closeme").click(function(){



                        $(this).parent().parent().parent().parent().parent().remove();
                        saveHistoryBar();

                    });
                    
                    $("#dialog-message").remove();
                    div =$('<div></div>');
                    div.attr('id','dialog-message');
                    div.attr('title','Save Element');
                    var html = $("<div style='padding:15px'><b>Please give your saved element a label:</b> <input id='label_input' type='text'/></div>")
                    div.append(html);
                    $("#testdiv").append(div);
                    $('#dialog-message').dialog({
                        modal: true,
                        height:200,
                        width:400,
                        buttons: {


                            Ok: function() {

                                tr =$('<tr class="inner_label"><td>'+$("#label_input").val()+'</td><tr>')
                                table.append(tr);

                                saveHistoryBar();
                                $("#dialog-message").dialog( "close" );
                                $("#dialog-message").remove();

                            },
                            Cancel: function(){
                                $(".floaters").last().remove();
                                saveHistoryBar();
                                $("#dialog-message").dialog( "close" );
                                $("#dialog-message").remove();



                            }
                        }
                    });
                }
            });

        },
        initSaved: function() {



            $(".history_draggable").each(function(){
                
                var img = $(this);
                $(".closeme").unbind();
                $(".closeme").click(function(){



                    $(this).parent().parent().parent().parent().parent().remove();
                    saveHistoryBar();

                });
                img.draggable({
                    revert: "invalid",
                    helper:"clone",
                    zIndex: 1700
                });
                if(img.attr('time_data') != -1){
                    img.data('time_data',img.attr('time_data'));

                }
                if(img.attr('event_data') != -1){
                    img.data('event_data',img.attr('event_data'));

                }
                if(img.attr('inst_data') != -1){
                    img.data('inst_data',img.attr('inst_data'));

                }
                img.click(function(){
                    if($(this).attr('helio_type')== 'time'){
                        window.historyBar.time_input_form($(this),true);
                    }
                    if($(this).attr('helio_type')== 'event'){
                        window.historyBar.event_input_form($(this));
                    }
                    if($(this).attr('helio_type')== 'inst'){
                        window.historyBar.instrument_input_form($(this));
                    }
                    if($(this).attr('helio_type')== 'result'){
                        window.historyBar.result_input_form($(this));
                    }
                });
            });
        },
        addItem: function(item) {
            if (typeof console!="undefined")console.info("History :: addItem ->"+ item.getClassName());
            var prevItem =array.pop();
            if(prevItem != null &&prevItem.getType()!="ghost"){
                array.push(prevItem);

            }
            array.push(item);
            current=array.length-1;
            array.length >=limit ? offset = array.length-limit: offset =0;

        },
        setFilter: function(filterParam) {
            if (typeof console!="undefined")console.info("History :: setFilter ->"+ filterParam);
            filter = filterParam;
        },
        getItem: function(index) {
            if (typeof console!="undefined")console.info("History :: getItem ->"+ index);
            return array[index];

        },
        removeItem : function(index) {
            if (typeof console!="undefined")console.info("History :: removeItem ->"+ index);


            array.splice(index, 1);
            //if(array.length >0)current--;
            current =-1;


            this.render();

        },
        cleanGhost: function(){
            if (typeof console!="undefined")console.info("History :: cleanGhost ->");
            var element = array.pop();
            if(element.getType()=="ghost"){

                return;
            };
            array.push(element);

        },
        solidify: function(html){
            if (typeof console!="undefined")console.info("History :: solidify ->"+ html);

            //get current
            var element = array.pop();

            if(element.getType()=="ghost"){
                element.setType("query");
                element.setHtml(html);
            //$(element).data("query",$("#currentDisplay").html());


            //var serialized = $("#currentDisplay").find("form").serialize();

            //$(element).data("serialized",serialized);
            //$("#currentDisplay").remove();

            }
            array.push(element);

            this.render();


        },
        render: function(param){
            if (typeof console!="undefined")console.info("History :: render ->" + current +" param "+ param);

            if(param !=1){
                if(array.length >0 && current >=0){

                    window.workspace.setElement(array[current]);
                }else{
                    window.workspace.setDisplay("splash");
                }
            }
            $('#historyContent').html('');
            var key = 0;

            var arrayToRender = [];
            var arrayToIndex = [];
            if(filter=='all'){
                arrayToRender=array;
                for(var i = 0;i < array.length;i++) {
                    arrayToIndex.push(i);
                }
            }
            else if(filter=='results'){
                for(i = 0;i < array.length;i++) {


                    if(array[i].getType() == 'nativeResult'){
                        arrayToRender.push(array[i]);
                        arrayToIndex.push(i);
                    }

                }

            }
            else if(filter=='actions'){
                for(i = 0;i < array.length;i++) {


                    if(array[i].getType() == 'query'){
                        arrayToRender.push(array[i]);
                        arrayToIndex.push(i);
                    }

                }

            }
            else if(filter=='selections'){
                for(i = 0;i < array.length;i++) {


                    if(array[i].getType() == 'resultSelection'){
                        arrayToRender.push(array[i]);
                        arrayToIndex.push(i);
                    }

                }

            }



            for(key = offset;key < arrayToRender.length;key++) {


                if(key <limit+offset)arrayToRender[key].render(arrayToIndex[key],current);

            }

            fnInitDroppable();
            $(".floadters[title]").tooltip({
                position: "center right",
                delay: 0,
                predelay:500
            });

        },

        shiftRight: function() {
            if (typeof console!="undefined")console.info("History :: shiftRight");
            offset--;
            if(offset < 0)offset =0;
            this.render();
        },
        shiftLeft: function() {
            if (typeof console!="undefined")console.info("History :: shiftLeft");
            offset++;
            if(offset > array.length-1)offset =array.length-1;
            if(offset < 0)offset =0;
            this.render();
        },
        setFocus: function(key){
            current = key;
            this.render();
        }

    };
}
