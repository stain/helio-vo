function ActionViewer(imageParam,typeParam,actionNameParam,labelParam,serviceNameParam) {

    var className = "ActionViewer";
    var actionName = actionNameParam;
    var serviceName = serviceNameParam;
    var type = typeParam;
    var content;
    var imagePath = imageParam;
    var label = "";
    var advancedSearch;
    var prevData;
    var step =0;
    var history = new Array();
    
    /**
     * Initialize the tooltips and reset buttons of columns 
     * Called after onSucess, onError
     */
    var _initAdvancedParams = function(xmlHttpRequest,textStatus,jqXHR){
        // trace method
        //    	if (typeof console!="undefined") {
        //    		console.info("_initAdvancedParams " + textStatus );
        //    	}
        
        $('.column-reset').each(function() {
            $(this).button();
        });

        // setup tooltips
        $(".colLabelTooltipMe").each(function() {
            var me = this;
            $(this).tooltip({
                position: "center right",
                tipClass: 'ctooltip_' + this.id.substring(6),
                delay: 0,
                predelay:0,
                relative: true
            });
        });
    };
    
    /**
     * Called after successful loading of columns
     * @param data HTML stub containing the loaded columns
     * @param textStatus a status message.
     */
    var __onSuccessGetAdvancedParams = function(data,textStatus) {
        //if (typeof console!="undefined") console.info("_onSuccessGetHECColumns");
        $('#advancedParams').append(data);
    };

    /**
     * Method called in case an error occurs when loading the advanced param table.
     * @param XMLHttpREquest the underlying request
     * @param textStatus status message
     * @param errorThrown error object
     */
    var __onErrorGetAdvancedParams = function(xmlHttpRequest,textStatus,errorThrown) {
        var vars = [];
        var hashes = this.data.split('&');
        for(var i = 0; i < hashes.length; i++)
        {
            var hash = hashes[i].split('=');
            vars.push(hash[0]);
            vars[hash[0]] = hash[1];
        }
        var catalogName = vars['catalog'];
        $('#advancedParams').append('<div class="' + serviceName + '_' + catalogName + '">' +
            "<p>Error occurred while loading catalog info for " + catalogName + ".</p>" +
            "Reason: " + textStatus + " </p>" +
            "<p>" + errorThrown + "</p>" +
            "</div>");
    };

    /**
     * Load the input fields for a given catalog from remote
     */
    var _loadAdvancedParams = function(serviceName, catalogName) {
        if (typeof console!="undefined")console.info("ActionViewer ::  _loadAdvancedParams for " + serviceName + "_" + catalogName);

        // call getAdvancedParams asynchronously
        jQuery.ajax(
        {
            type : 'GET',
            data : {
                "serviceName":serviceName,
                "catalog":catalogName
            },
            url : 'getAdvancedParams',
            success: __onSuccessGetAdvancedParams,
            error: __onErrorGetAdvancedParams,
            complete: _initAdvancedParams
        });
        return false;
    };

    var _removeAdvancedParams = function(serviceName, catalogName) {
        if (typeof console!="undefined")console.info("ActionViewer ::  _removeAdvancedParams for " + serviceName + "_" + catalogName);
        $("." + serviceName + "_" + catalogName).remove();
    };

    var _initGhostElements = function(){
        if (typeof console!="undefined")console.info("ActionViewer :: _initGhostElements");
            
        fnInitializeDatePicker();
        
        $("#minDate").val($.cookie("minDate"));
        $("#maxDate").val($.cookie("maxDate"));
        $("#minTime").val($.cookie("minTime"));
        $("#maxTime").val($.cookie("maxTime"));

        var catalogCheckboxes = $(".catalogueSelector input:checkbox");

        // disable search button as long as no column is selected.
        var onChangeSearchButton = function(event){
            $('.submit_button').button({
                disabled: !$(".catalogueSelector input:checked").val()
            });
        };
        onChangeSearchButton();  // init button state
        catalogCheckboxes.change(onChangeSearchButton); // register button handler
        
        $.collapsible(".queryHeader","group1");
        
        var onChangeCheckboxes = function(event){
            if (typeof console!="undefined")console.info("ActionViewer ::  catalogCheckboxes.change "+$(this).val());
            var catalogName = $(this).val();
            if($(this).is(':checked')){
                _loadAdvancedParams(serviceName, catalogName);
            }else{
                _removeAdvancedParams(serviceName, catalogName);
            }
        };
        
        // init preselected checkboxes
        if($("input:checked").length == 0){

            $('input[title="Instrument"]').attr("checked","checked");
        }
        $(".catalogueSelector input:checked").each(function() {
            var catalogName = $(this).val();
            var catSection = $("." + serviceName + "_" + catalogName);
        	
            if (catSection.length == 0) {
                onChangeCheckboxes.call(this);
            }
        });
        
        catalogCheckboxes.change(onChangeCheckboxes); // register checkbox handler

        $.collapsible(".advancedParameters","group2");
        
        // initialize the pager controll's delete button ('X')
        $("#currentDisplay").find("#delete").click(function(){
            if(history.length>0){
                history.splice(step, 1);
                step = history.length-1;
                window.historyBar.render();
            }else{
                window.historyBar.removeCurrent();
                window.workspace.setDisplay("splash");
            }
        });

        // remove column inputs if catalog selector is disabled
        // TODO: remove
        $(".catalogueSelector").change(function(){
            $('.columnInputs').html("");
            $('#whereField').val("");
        });
            
        $( ".custom_button").button();
        
        // setup tooltips
        $(".labelTooltipMe").each(function() {
            $(this).tooltip({
                position: "center right",
                tipClass: 'hecLabelTooltip',
                delay: 0,
                predelay:0,
                relative: true
            });
        });

        $(".column-reset").click(function(){

            $(".columnSelection").val("");
        });

        var options = {
            target:        '#responseDivision',   // target element(s) to be updated with server response
            beforeSerialize: fnBeforeQuery,
            beforeSubmit:  window.workspace.onLoading,  // pre-submit callback
            success:       fnOnComplete,  // post-submit callback
            error:         fnOnErrorAsynchQuery,
            // other available options:
            //url:       "asyncQuery",        // override for form's 'action' attribute
            //type:      'POST'        // 'get' or 'post', override for form's 'method' attribute
            //dataType:  null        // 'xml', 'script', or 'json' (expected server response type)
            //clearForm: true        // clear all form fields after successful submit
            //resetForm: true        // reset the form after successful submit

            // $.ajax options can be used here too, for example:
            timeout:   100000
        };

        // bind form using 'ajaxForm'
      
        $('#actionViewerForm').ajaxForm(options);

        $('.submit_button').button({
            disabled: !$(".catalogueSelector input:checked").val()
        });

        

         
        // setup column tooltips
        _initAdvancedParams();
        
        // format dpas selection box
        $("#instArea").selectBox('destroy');
        $("#instArea").selectBox().change( function() {
            $('.submit_button2').button({
                disabled: $(".selectBox-selected").length ==0
            });
        });

        
        $('.submit_button2').button({
            disabled: ($(".selectBox-selected").length ==0)
        });


        $("#currentDisplay").find("#label").val(label);
        $("#currentDisplay").find("#label").change(function() {
            window.historyBar.getCurrent().setLabel($(this).val());
            window.historyBar.render(1);
            if($(".destroyMe").length != 0)return;
            $(this).parent().append("<span class='destroyMe' style='color:white'><b>label set!</b></span>");
            $('.destroyMe').fadeOut(2000, function() {
                $(".destroyMe").remove();
            });

        });
    };
    var _initSolidElements = function(){
        if (typeof console!="undefined")console.info("ActionViewer:: _initSolidElements ");
        $("#currentDisplay").find("#counter").css("display","block");
        $("#currentDisplay").find("#counter").text((step+1)+"/"+history.length);
        

        $(".placeholder").remove();
        
        $("#currentDisplay").find("#forward").css("display","block");
        $("#currentDisplay").find("#forward").click(function(){
            window.workspace.getElement().nextStep();
        });

        $("#currentDisplay").find("#backward").css("display","block");
        $("#currentDisplay").find("#backward").click(function(){
            window.workspace.getElement().prevStep();
        });

       
        
    /*
        $("#resultSelectionSelectAll").click(function(){
            
            var tableId =$(this).attr('reference');
            
            $("#"+tableId).find("tbody").find("tr").each(function(){
                
                
                this.className.replace( /_selected/, "" );
                this.className+'_selected';
                
            });
        });*/
    };
    /*
     * Takes in the serialized parameters from the previous form, parses and redraws them into the new form.
     * @formData: serialized form string, Example: "minDateList=2003-01-01T07%3A49%3A00%2C2003-01-02T04%3A41%3A00%2C2003-01-02T12%3A58%3A00"
     *
     */
    var _unserialize = function(formData,advancedSearchParam){

        $('#currentDisplay').find('#advancedParams').html(advancedSearchParam);

        
        // $('#currentDisplay').find('.columnInputs').html(advancedSearchParam);
        
        //$('#currentDisplay').find('.columnInputs').css("display","block"); // remove
        //$("#currentDisplay").find("select").find("option").removeAttr("selected"); // remove
        var fields = formData.split("&");
        var minDateList =new Array();
        var maxDateList =new Array();
        for(field in fields){
            var tempField= fields[field];

            if(tempField.indexOf("minDateList=")!= -1){
                tempField =tempField.replace('minDateList=',"");
                tempField =tempField.replace('%3A',":");
                tempField =tempField.replace('%3A',":");
                tempField =tempField.replace('%2C',",");
                tempField =tempField.replace('+',"");
                //$(".minDateList").val(tempField);
                minDateList.push(tempField);
                
                

            }//end if
            else if(tempField.indexOf("maxDateList=")!= -1){
                tempField =tempField.replace('maxDateList=',"");
                tempField =tempField.replace('%3A',":");
                tempField =tempField.replace('%3A',":");
                tempField =tempField.replace('%2C',",");
                tempField =tempField.replace('+',"");
                //$(".maxDateList").val(tempField);
                maxDateList.push(tempField);
               
            }//end if
            else if(tempField.indexOf("minTime=")!= -1){
                tempField =tempField.replace('minTime=',"");
                tempField =tempField.replace('%3A',":");
                $("#currentDisplay").find("input[name='minTime']").val(tempField);
            }//end if
            else if(tempField.indexOf("maxTime=")!= -1){
                tempField =tempField.replace('maxTime=',"");
                tempField =tempField.replace('%3A',":");
                $("#currentDisplay").find("input[name='maxTime']").val(tempField);
            }//end if
            else if(tempField.indexOf("minDate=")!= -1){
                tempField =tempField.replace('minDate=',"");
                $("#currentDisplay").find("input[name='minDate']").val(tempField);
            }//end if
            else if(tempField.indexOf("maxDate=")!= -1){
                tempField =tempField.replace('maxDate=',"");
                $("#currentDisplay").find("input[name='maxDate']").val(tempField);
            }//end if
            else if(tempField.indexOf("extra=")!= -1){

                tempField =tempField.replace('extra=',"");
                
                
                $("#currentDisplay").find("select").find("option[value='"+tempField+"']").attr("selected","selected"); // remove
                
                $("#currentDisplay").find("input[value='"+tempField+"']").attr("checked","checked");

            }else if(tempField.indexOf("where=")!= -1){

                tempField =tempField.replace('where=',"");
                tempField =tempField.replace(/%5C/g,"\\");
                tempField =tempField.replace(/%2F/g,"/");
                tempField =tempField.replace(/%3B/g,";");
                tempField =tempField.replace(/%2C/g,",");
                
                tempField =tempField.split("%3B");
                
                for(input in tempField){
                    
                    var innerTempField = tempField[input].split(";");
                    var value = innerTempField[1];
                    innerTempField = innerTempField[0];
                    
                    
                    
                    $("#currentDisplay").find("input[name='"+innerTempField+"']").val(value);
                    
                //$("#currentDisplay").find("input[name='"+inputName+"."+labelName+"']").val(value);
                }//end input
            }//end if
        }//end fields
        
        
        if(maxDateList != null && maxDateList.length>0) for(var i = 0; i< maxDateList.length;i++){
            
            $(".hideDates").css("display","none");
            $(".dateTable").append(
                '<tr class="biggerInput dropInput">'+
                '<td><input name="minDateList" type="text" value="'+ minDateList[i]+'"/><div class="subbing cbutton">-</div><div class="adding cbutton">+</div></td>'+
                '<td><!--input type="checkbox" checked="checked"/--></td>'+
                '<td><input name="maxDateList" type="text" value="'+ maxDateList[i]+'"/><div class="subbing cbutton">-</div><div class="adding cbutton">+</div></td></tr>');
            $(".resultDroppable").css('background-image','url(../images/icons/toolbar/circle_time.png)');
        }//end for i


        $(".cbutton").button();
    };//end unserialized

    return {
        // Public methods
        getClassName: function() {
            if (typeof console!="undefined")console.info("ActionViewer :: getClassName");
            return className;
        },
       
        getServiceName: function() {
            if (typeof console!="undefined")console.info("ActionViewer :: getServiceName");
            return serviceName;
        },
        prepareStep: function(formData,advancedSearchParams) {
            if (typeof console!="undefined")console.info("ActionViewer :: prepareStep :: formData ="+ formData);
            prevData=formData;
            advancedSearch=advancedSearchParams;
        },

        addStep: function(result) {
            if (typeof console!="undefined")console.info("ActionViewer :: addStep -> notshown");
            var object = new Object();
            object['result']=result;
            object['formData']=prevData;
            object['advancedSearch']=advancedSearch;

            history.push(object);
            step = history.length -1;

        },
        nextStep: function() {
            if (typeof console!="undefined")console.info("ActionViewer :: nextStep");
            if(step < history.length -1){
                step++;
                
                this.renderContent();
            }
        },
        prevStep: function() {
            if (typeof console!="undefined")console.info("ActionViewer :: prevStep");
            if(step > 0){
                step--;
                
                this.renderContent();
            }
            
        },
        setLabel: function(labelParam) {
            if (typeof console!="undefined")console.info("ActionViewer :: setLabel -> " +labelParam);
            label=labelParam;
            
        },
        getLabel: function() {
            if (typeof console!="undefined")console.info("ActionViewer :: getLabel");
            return label;
        },
        

        setImagePath: function(path) {
            if (typeof console!="undefined")console.info("ActionViewer :: setImagePath -> " +path);
            imagePath = path;
        },
        getImagePath: function() {
            if (typeof console!="undefined")console.info("ActionViewer :: getImagePath");
            return imagePath;
        },
        setContent: function(contentParam) {
            if (typeof console!="undefined")console.info("ActionViewer :: setContent -> " +contentParam);
            content = contentParam;
        },
        getContent: function() {
            if (typeof console!="undefined")console.info("ActionViewer :: getContent");
            return content;
        },

        getType: function() {
            if (typeof console!="undefined")console.info("ActionViewer :: getType -> " + type);
            return type;
        },
        setType: function(typeParam) {
            if (typeof console!="undefined")console.info("ActionViewer :: setType -> " +typeParam);
            type =typeParam;
        },
        renderContent: function() {
            if (typeof console!="undefined")console.info("ActionViewer :: renderContent");
            window.workspace.setDisplay(actionName);
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

                var title ="Element contains no data";
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
                var title ="<div>Number of elements: "+history.length+"<br>Label: "+label+"<br>Service name: "+serviceName+"</div>";
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
                            $('#currentDisplay').fadeOut(500, function(){
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

                        $('#currentDisplay').fadeOut(500, function(){
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
